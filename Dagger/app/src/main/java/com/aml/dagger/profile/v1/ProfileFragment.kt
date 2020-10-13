package com.aml.dagger.profile.v1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aml.dagger.dagger.ApplicationComponent
import com.aml.dagger.dagger.DaggerApplicationComponent
import com.aml.dagger.databinding.ProfileFragmentBinding
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var presenter: ProfilePresenter

    private lateinit var binding: ProfileFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val applicationGraph: ApplicationComponent =
            DaggerApplicationComponent.factory().create(context)
        applicationGraph.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)

        presenter.testViewModel()

        return binding.root
    }


}