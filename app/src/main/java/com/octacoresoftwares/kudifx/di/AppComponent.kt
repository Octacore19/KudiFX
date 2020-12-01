package com.octacoresoftwares.kudifx.di

import android.content.Context
import com.octacoresoftwares.kudifx.App
import com.octacoresoftwares.kudifx.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [
    AndroidInjectionModule::class,
    LatestAssistedInjectModule::class,
    ViewModelBuilderModule::class,
    WorkerBindingModule::class,
    MainModule::class])
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun workFactory(): WorkManagerFactory
    fun inject(activity: MainActivity)
}