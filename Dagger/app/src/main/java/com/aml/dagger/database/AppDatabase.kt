package com.aml.dagger.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aml.dagger.database.dao.ProfileDao
import com.aml.dagger.database.entity.Profile

@Database(
    entities = [Profile::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun profileDao(): ProfileDao
}