package com.octacoresoftwares.kudifx.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.local.AppDatabase
import com.octacoresoftwares.kudifx.remote.Service
import com.octacoresoftwares.kudifx.repo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LatestRatesWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val remote = NetworkRepoImpl(Service.createService()) as NetworkRepo
        val local = DatabaseRepoImpl(AppDatabase.getDatabase(context).ratesDao()) as DatabaseRepo
        val repo = Repository(local, remote)

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