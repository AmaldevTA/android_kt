package com.aml.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aml.room.database.dao.TempUserDao
import com.aml.room.database.dao.UserDao
import com.aml.room.database.entity.TempUser
import com.aml.room.database.entity.User
import com.aml.room.database.migration.Migration1To2
import com.aml.room.database.migration.Migration2To3

@Database(
    entities = [User::class, TempUser::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun tempUSerDao(): TempUserDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database",
                )
                    .addMigrations(Migration1To2())
                    .addMigrations(Migration2To3())
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}