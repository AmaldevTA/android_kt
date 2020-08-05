package com.aml.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aml.room.database.dao.UserDao
import com.aml.room.database.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}