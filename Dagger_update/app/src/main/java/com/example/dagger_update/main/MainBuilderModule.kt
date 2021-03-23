package com.example.dagger_update.main

import com.example.dagger_update.deps.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainBuilderModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class, MainFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity
}