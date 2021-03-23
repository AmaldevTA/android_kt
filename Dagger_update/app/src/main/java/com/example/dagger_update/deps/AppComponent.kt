package com.example.dagger_update.deps

import android.app.Application
import com.example.dagger_update.MyApp
import com.example.dagger_update.info.InfoBuilderModule
import com.example.dagger_update.main.MainBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        MainBuilderModule::class,
        InfoBuilderModule::class,
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

    fun inject(application: MyApp)
}