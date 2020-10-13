package com.aml.dagger.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.aml.dagger.view_model_factory.MyViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory
}