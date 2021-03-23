package com.example.dagger_update.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dagger_update.R
import com.example.dagger_update.info.adapter.InfoAdapter
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class InfoActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var adapter: InfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        findViewById<Button>(R.id.printContext).setOnClickListener {
            adapter.printContextDetails()
        }
    }

    override fun androidInjector() = fragmentDispatchingAndroidInjector
}