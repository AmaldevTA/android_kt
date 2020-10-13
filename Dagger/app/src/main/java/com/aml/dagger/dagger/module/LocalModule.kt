package com.aml.dagger.dagger.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.aml.dagger.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object LocalModule {

    @Singleton
    @Provides
    fun getAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .build()
    }

    @Singleton
    @Provides
    fun getAppPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preference", Context.MODE_PRIVATE)
    }
}