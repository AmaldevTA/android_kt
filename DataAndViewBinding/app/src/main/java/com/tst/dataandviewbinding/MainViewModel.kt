package com.tst.dataandviewbinding

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val message: String = "Hello World!!"
    val count: Int = 1
    val name = ObservableField<String>()

    fun onClickAction1(view: View) {}

    fun onClickAction2(str: String){}

    fun onClickAction3(view: View, str: String){}
}