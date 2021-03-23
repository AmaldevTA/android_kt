package com.example.dagger_update

import android.app.Application
import com.example.dagger_update.deps.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApp : Application(), HasAndroidInjector {

    companion object {
        @JvmStatic
        private val TAG = MyApp::class.java.simpleName
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .factory()
            .create(
                this
            )
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}