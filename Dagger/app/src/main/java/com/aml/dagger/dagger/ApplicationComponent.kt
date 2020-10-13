package com.aml.dagger.dagger

import android.content.Context
import com.aml.dagger.MyApplication
import com.aml.dagger.dagger.module.ActivityBuildersModule
import com.aml.dagger.dagger.module.LocalModule
import com.aml.dagger.dagger.module.NetworkModule
import com.aml.dagger.dagger.module.ViewModelFactoryModule
import com.aml.dagger.profile.v1.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        LocalModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<MyApplication> {

    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}