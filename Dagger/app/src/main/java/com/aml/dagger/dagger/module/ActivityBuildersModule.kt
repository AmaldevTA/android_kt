package com.aml.dagger.dagger.module

import com.aml.dagger.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class,
            ViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}