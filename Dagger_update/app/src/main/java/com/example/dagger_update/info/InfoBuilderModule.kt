package com.example.dagger_update.info

import com.example.dagger_update.deps.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class InfoBuilderModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [InfoModule::class])
    abstract fun bindInfoActivity(): InfoActivity
}