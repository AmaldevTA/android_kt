package com.example.dagger_update.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dagger_update.model.DataBase
import com.example.dagger_update.model.Network
import javax.inject.Inject

class SettingsViewModel constructor(
    private val dataBase: DataBase,
    private val network: Network
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val dataBase: DataBase,
        private val network: Network
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SettingsViewModel(dataBase, network) as T
        }
    }
}