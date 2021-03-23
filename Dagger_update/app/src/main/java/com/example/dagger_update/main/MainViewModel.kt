package com.example.dagger_update.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dagger_update.deps.qualifier.ApplicationContext
import com.example.dagger_update.model.DataBase
import com.example.dagger_update.model.Network
import javax.inject.Inject

class MainViewModel constructor(
    private val context: Context,
    private val dataBase: DataBase,
    private val network: Network
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        @ApplicationContext private val context: Context,
        private val dataBase: DataBase,
        private val network: Network
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(context, dataBase, network) as T
        }
    }

    fun printContextDetails() {
        println("<<<>>> $context")
    }

}