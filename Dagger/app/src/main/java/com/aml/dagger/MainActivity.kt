package com.aml.dagger

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.aml.dagger.databinding.ActivityMainBinding
import com.aml.dagger.profile.v1.ProfileFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            replace(binding.container.id, ProfileFragment::class.java, bundleOf())
        }
    }
}