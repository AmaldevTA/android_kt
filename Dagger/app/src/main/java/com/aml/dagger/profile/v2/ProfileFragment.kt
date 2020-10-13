package com.aml.dagger.profile.v2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.aml.dagger.databinding.ProfileFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

//    @Inject
//    lateinit var viewModelFactory: ProfileViewModel1.Factory
//    private val viewModel: ProfileViewModel1 by viewModels(
//        factoryProducer = { viewModelFactory }
//    )

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ProfileViewModel2 by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)

        viewModel.testViewModel()

        return binding.root
    }


}