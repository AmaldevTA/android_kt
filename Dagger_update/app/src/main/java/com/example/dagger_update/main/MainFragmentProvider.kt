package com.example.dagger_update.main

import com.example.dagger_update.deps.scope.PerFragment
import com.example.dagger_update.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {

    @PerFragment
    @ContributesAndroidInjector(modules = [])
    abstract fun bindMainFragment(): MainFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [])
    abstract fun bindSettingsFragment(): SettingsFragment

}