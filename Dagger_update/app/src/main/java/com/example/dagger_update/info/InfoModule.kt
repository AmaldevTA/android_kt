package com.example.dagger_update.info

import android.content.Context
import com.example.dagger_update.deps.qualifier.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class InfoModule {
    @Provides
    @ActivityContext
    fun provideContext(activity: InfoActivity): Context = activity
}