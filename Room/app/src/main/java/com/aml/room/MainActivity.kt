package com.aml.room

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val count = findViewById<TextView>(R.id.count)
        findViewById<Button>(R.id.add).setOnClickListener {
            addDummy()
        }

        viewModel.getAllLive().observe(this, Observer {
           // count.text = "Row count ${it.size}"
        })

        lifecycleScope.launch{
            viewModel.getAllFlow().collect {
                //count.text = "Row count ${it.size}"
            }
        }

        viewModel.getAllFlowable(Consumer {
            //count.text = "Row count ${it.size}"
        })


        lifecycleScope.launch{
           val res = viewModel.getUserByName("Jo")
            count.text = "Row count ${res.size}"
        }
    }

    private fun addDummy(){
        lifecycleScope.launch{
            viewModel.addDummy()
        }
    }
}