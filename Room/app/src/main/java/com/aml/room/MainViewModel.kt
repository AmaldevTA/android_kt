package com.aml.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
    private val db = AppDatabase.getDatabase(app)

    suspend fun addDummy() = withContext(Dispatchers.IO) {
        val list = mutableListOf<User>()
        list.add(User(5, "John", 54, 75.2))
        list.add(User(6, "Jesus", 25, 54.3))
        list.add(User(7, "Mathew", 37, 65.2))
        list.add(User(8, "Gabriel", 44, 44.5))
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