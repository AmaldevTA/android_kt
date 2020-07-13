package com.aml.dagger.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {

    @Provides
    fun context(): Context {
        return context
    }
}