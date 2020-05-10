package com.tst.dataandviewbinding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tst.dataandviewbinding.databinding.MainLayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView( binding.root)

        binding.message.text = "Hello World!"

        val viewModel : MainViewModel by viewModels()
        binding.viewModel = viewModel
        viewModel.name.set("UserName")

    }
}
