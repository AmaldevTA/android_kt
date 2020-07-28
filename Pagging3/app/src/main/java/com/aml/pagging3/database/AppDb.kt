package com.aml.pagging3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aml.pagging3.database.dao.ProductDao
import com.aml.pagging3.model.Product

@Database(entities = [Product::class], exportSchema = false, version = 1)
abstract  class AppDb : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private var instance: AppDb? = null
        @Synchronized
        fun get(context: Context): AppDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java, "AppDatabase"
                ).build()
            }
            return instance!!
        }
    }
}