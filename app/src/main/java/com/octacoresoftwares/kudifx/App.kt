package com.octacoresoftwares.kudifx

import android.app.Application
import androidx.work.*
import com.octacoresoftwares.kudifx.di.AppComponent
import com.octacoresoftwares.kudifx.di.DaggerAppComponent
import com.octacoresoftwares.kudifx.di.WorkManagerFactory
import com.octacoresoftwares.kudifx.services.LatestRatesWorker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App: Application(), HasAndroidInjector, Configuration.Provider {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
        super.onCreate()

        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("kudifx", ExistingPeriodicWorkPolicy.REPLACE,
                PeriodicWorkRequestBuilder<LatestRatesWorker>(30, TimeUnit.MINUTES)
                    .setConstraints(constraint)
                    .build()
            )
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun getWorkManagerConfiguration(): Configuration {
        val factory = appComponent.workFactory()
        return Configuration.Builder().setWorkerFactory(factory).build()
    }
}