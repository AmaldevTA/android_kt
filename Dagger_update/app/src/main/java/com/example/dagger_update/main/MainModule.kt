package com.example.dagger_update.main

import android.content.Context
import com.example.dagger_update.deps.qualifier.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    @ActivityContext
    fun provideContext(mainActivity: MainActivity): Context = mainActivity
}