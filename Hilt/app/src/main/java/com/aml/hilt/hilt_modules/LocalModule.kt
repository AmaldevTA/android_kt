package com.aml.hilt.hilt_modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.aml.hilt.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun getAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .build()
    }

    @Singleton
    @Provides
    fun getAppPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preference", Context.MODE_PRIVATE)
    }
}