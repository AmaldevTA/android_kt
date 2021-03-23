package com.example.dagger_update.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dagger_update.R
import com.example.dagger_update.settings.adapter.SettingsAdapter
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SettingsFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: SettingsViewModel.Factory

    private val viewModel: SettingsViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    @Inject
    lateinit var adapter: SettingsAdapter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)

        view.findViewById<Button>(R.id.adapterContext).setOnClickListener {
            adapter.printContextDetails()
        }

        return view
    }

    override fun androidInjector() = childFragmentInjector

}