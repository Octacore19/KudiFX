package com.octacoresoftwares.kudifx.di

import androidx.lifecycle.ViewModel
import com.octacoresoftwares.kudifx.local.RoomModule
import com.octacoresoftwares.kudifx.remote.RetrofitModule
import com.octacoresoftwares.kudifx.repo.*
import com.octacoresoftwares.kudifx.ui.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class, RoomModule::class])
abstract class MainModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindLoginViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun bindRepository(repo: RepositoryImpl): Repository

    @Binds
    abstract fun bindNetworkRepo(repo: NetworkRepoImpl): NetworkRepo

    @Binds
    abstract fun bindDatabaseRepo(repo: DatabaseRepoImpl): DatabaseRepo
}