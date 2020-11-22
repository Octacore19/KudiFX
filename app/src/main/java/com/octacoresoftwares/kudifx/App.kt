package com.octacoresoftwares.kudifx

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.octacoresoftwares.kudifx.services.LatestRatesWorker
import java.util.concurrent.TimeUnit

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val request = PeriodicWorkRequest
            .Builder(LatestRatesWorker::class.java, 30, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("kudifx", ExistingPeriodicWorkPolicy.REPLACE, request)
    }
}