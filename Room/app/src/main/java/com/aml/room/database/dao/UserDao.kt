package com.aml.room.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.aml.room.database.entity.User
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllLive(): LiveData<List<User>>

    @Query("SELECT * FROM User")
    fun getAllFlow(): Flow<List<User>>

    @Query("SELECT * FROM User")
    fun getAllPaging(): PagingSource<Int, User>

    @Query("SELECT * FROM User")
    fun getAllFlowable(): Flowable<List<User>>

    /*-------------------------------------------------------------------------------------*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bankList: List<User>)

    @Query("DELETE FROM User")
    suspend fun deleteAll()

    @Update
    suspend fun updateAll(bankList: List<User>)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user WHERE age > :minAge")
    fun loadAllUsersOlderThan(minAge: Int): List<User>

    @RawQuery
    fun getUserByDynamicQuery(query: SimpleSQLiteQuery): List<User>
}