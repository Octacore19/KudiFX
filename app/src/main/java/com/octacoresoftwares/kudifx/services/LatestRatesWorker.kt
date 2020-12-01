package com.octacoresoftwares.kudifx.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.local.AppDatabase
import com.octacoresoftwares.kudifx.remote.RetrofitModule
import com.octacoresoftwares.kudifx.repo.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class LatestRatesWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repo: Repository
): CoroutineWorker(context, workerParams) {

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val key = context.resources.getString(R.string.access_key)
                repo.saveLatestRate(key)
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}