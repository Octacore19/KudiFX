package com.octacoresoftwares.kudifx.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.octacoresoftwares.kudifx.services.ChildWorkerFactory
import com.octacoresoftwares.kudifx.services.LatestRatesWorker
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.android.HasAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider

class WorkManagerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val foundEntry = workerFactories.entries
            .find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        return foundEntry?.value?.get()?.create(appContext, workerParameters)
    }
}

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(LatestRatesWorker::class)
    fun bindLatestRatesWorker(factory: LatestRatesWorker.Factory): ChildWorkerFactory
}

@Module(includes = [AssistedInject_LatestAssistedInjectModule::class])
@AssistedModule
interface LatestAssistedInjectModule