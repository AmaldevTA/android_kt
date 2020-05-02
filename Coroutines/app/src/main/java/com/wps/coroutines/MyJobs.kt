package com.wps.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyJobs : ViewModel() {

    fun launchDataLoad() {
        viewModelScope.launch(Dispatchers.IO) {
            doBackgroundWork()
            withContext(Dispatchers.Main) {
                doMainThreadWork()
            }
        }
    }

    private suspend fun doBackgroundWork(){

    }

    private suspend fun doMainThreadWork(){

    }
}
