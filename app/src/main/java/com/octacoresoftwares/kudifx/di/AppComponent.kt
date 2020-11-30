package com.octacoresoftwares.kudifx.di

import android.content.Context
import com.octacoresoftwares.kudifx.remote.RetrofitModule
import com.octacoresoftwares.kudifx.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ViewModelBuilderModule::class, MainModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}