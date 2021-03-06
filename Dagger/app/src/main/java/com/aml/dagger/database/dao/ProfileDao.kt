package com.aml.dagger.database.dao

import androidx.room.*
import com.aml.dagger.database.entity.Profile

@Dao
interface ProfileDao {

    @Query("SELECT * FROM Profile")
    fun getAll(): List<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bankList: List<Profile>)

    @Query("DELETE FROM Profile")
    fun deleteAll()

    @Update
    fun updateAll(bankList: List<Profile>)
}