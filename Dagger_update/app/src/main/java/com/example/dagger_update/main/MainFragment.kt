package com.example.dagger_update.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.dagger_update.R
import com.example.dagger_update.info.InfoActivity
import com.example.dagger_update.main.adapter.MainFragmentAdapter
import com.example.dagger_update.settings.SettingsFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MainFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    private val viewModel: MainViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    @Inject
    lateinit var adapter: MainFragmentAdapter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        view.findViewById<Button>(R.id.viewModelContext).setOnClickListener {
            viewModel.printContextDetails()
        }

        view.findViewById<Button>(R.id.adapterContext).setOnClickListener {
            adapter.printContextDetails()
        }

        view.findViewById<Button>(R.id.load).setOnClickListener {
            childFragmentManager.commit {
                replace(R.id.container, SettingsFragment())
                addToBackStack(null)
            }
        }

        view.findViewById<Button>(R.id.nextActivity).setOnClickListener {
            startActivity(Intent(requireContext(), InfoActivity::class.java))
        }

        return view
    }

    override fun androidInjector() = childFragmentInjector

}