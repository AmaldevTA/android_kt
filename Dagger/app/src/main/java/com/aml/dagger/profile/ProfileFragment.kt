package com.aml.dagger.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import com.aml.dagger.R
import com.aml.dagger.dagger.ApplicationComponent
import com.aml.dagger.dagger.DaggerApplicationComponent
import com.aml.dagger.dagger.module.ContextModule
import com.aml.dagger.databinding.ProfileFragmentBinding
import javax.inject.Inject

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @Inject
    lateinit var presenter: ProfilePresenter

    private lateinit var binding: ProfileFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val applicationGraph: ApplicationComponent =
            DaggerApplicationComponent.builder()
                .contextModule(ContextModule(context))
                .build()
        applicationGraph.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, R.layout.profile_fragment, container, false)
        return binding.root
    }


}