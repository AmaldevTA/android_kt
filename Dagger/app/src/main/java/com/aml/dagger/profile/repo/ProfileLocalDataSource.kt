package com.aml.dagger.profile.repo

import android.content.SharedPreferences
import com.aml.dagger.database.AppDatabase
import javax.inject.Inject

class ProfileLocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
    private val preferences: SharedPreferences){
}