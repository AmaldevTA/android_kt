package com.aml.hilt.profile.repo

import android.content.SharedPreferences
import com.aml.hilt.database.AppDatabase
import javax.inject.Inject

class ProfileLocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
    private val preferences: SharedPreferences
) {

    fun printReferences() {
        println("<<<>>>  $appDatabase")
        println("<<<>>>  $preferences")
    }

}