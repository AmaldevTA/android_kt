package com.example.dagger_update.deps

import android.app.Application
import android.content.Context
import com.example.dagger_update.deps.qualifier.ApplicationContext
import com.example.dagger_update.model.DataBase
import com.example.dagger_update.model.Network
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    @ApplicationContext
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): DataBase {
        return DataBase(context)
    }

    @Singleton
    @Provides
    fun provideAppNetwork(@ApplicationContext context: Context): Network {
        return Network(context)
    }

}