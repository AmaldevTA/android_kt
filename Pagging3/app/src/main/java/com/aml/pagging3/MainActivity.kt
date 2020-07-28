package com.aml.pagging3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aml.pagging3.adapter.ProductAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagingAdapter = ProductAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.productList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = pagingAdapter

        lifecycleScope.launch {
            viewModel.advancedProductPagingFlow.collect { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }
}