package com.aml.room

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import com.aml.room.database.AppDatabase
import com.aml.room.database.entity.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {
    val db = Room.databaseBuilder(app, AppDatabase::class.java, "app_database")
        .build()

    suspend fun addDummy() = withContext(Dispatchers.IO) {
        val list = mutableListOf<User>()
        list.add(User(1, "John", 54))
        list.add(User(2, "Jesus", 25))
        list.add(User(3, "Mathew", 37))
        list.add(User(4, "Gabriel", 44))
        db.userDao().insertAll(list)
    }


    fun getAllLive(): LiveData<List<User>> = db.userDao().getAllLive()

    fun getAllFlow(): Flow<List<User>> = db.userDao().getAllFlow()

    fun getAllFlowable(onNext: Consumer<List<User>>) {
        db.userDao().getAllFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext)
    }

    suspend fun getUserByName(search: String) : List<User> = withContext(Dispatchers.IO) {
        val query = SimpleSQLiteQuery("SELECT * FROM user WHERE name LIKE '%$search%'")
        db.userDao().getUserByDynamicQuery(query)
    }
}