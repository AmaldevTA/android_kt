package com.aml.dagger.dagger

import com.aml.dagger.dagger.module.LocalModule
import com.aml.dagger.dagger.module.NetworkModule
import com.aml.dagger.profile.ProfileFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, LocalModule::class])
interface ApplicationComponent {
    fun inject(fragment: ProfileFragment)
}