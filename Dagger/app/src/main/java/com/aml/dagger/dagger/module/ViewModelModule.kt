package com.aml.dagger.dagger.module

import androidx.lifecycle.ViewModel
import com.aml.dagger.profile.v2.ProfileViewModel2
import com.aml.dagger.view_model_factory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel2::class)
    internal abstract fun bindProfileViewModel2(viewModel: ProfileViewModel2): ViewModel
}