package com.example.dagger_update.main.adapter

import android.content.Context
import com.example.dagger_update.deps.qualifier.ActivityContext
import javax.inject.Inject

class MainFragmentAdapter @Inject constructor(
    @ActivityContext val context: Context
) {

    fun printContextDetails() {
        println("<<<>>> $context")
    }
}