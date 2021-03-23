package com.example.dagger_update.settings.adapter

import android.content.Context
import com.example.dagger_update.deps.qualifier.ActivityContext
import javax.inject.Inject

class SettingsAdapter @Inject constructor(
    @ActivityContext val context: Context
) {

    fun printContextDetails() {
        println("<<<>>> $context")
    }
}