package com.aml.hilt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aml.hilt.database.dao.ProfileDao
import com.aml.hilt.database.entity.Profile

@Database(
    entities = [Profile::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun profileDao(): ProfileDao
}