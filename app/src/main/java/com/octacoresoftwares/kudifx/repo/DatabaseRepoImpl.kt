package com.octacoresoftwares.kudifx.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.octacoresoftwares.kudifx.local.RatesDao
import com.octacoresoftwares.kudifx.local.Latest
import com.octacoresoftwares.kudifx.local.Rates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseRepoImpl @Inject constructor(
    private val dao: RatesDao
): DatabaseRepo {

    override suspend fun insertLatestRate(latest: Latest) {
        withContext(Dispatchers.IO) {
            try {
                dao.insertLatestRate(latest)
            } catch (e: Exception) {
                Log.wtf("NetworkRepoImpl", e)
            }
        }
    }

    override suspend fun getAllRates(): Results<LiveData<List<Rates>>> =
        withContext(Dispatchers.IO) {
            try {
                Results.Success(MutableLiveData(dao.getAllRates()))
            } catch (e: Exception) {
                Results.Error(e)
            }
        }

    override suspend fun getAllLatestRate(): Results<LiveData<List<Latest>>> =
        withContext(Dispatchers.IO) {
            try {
                Results.Success(MutableLiveData(dao.getAllLatestRates()))
            } catch (e: Exception) {
                Results.Error(e)
            }
        }

    override suspend fun getMostRecentRate(): Results<LiveData<Latest>> =
        withContext(Dispatchers.IO) {
            try {
                Results.Success(MutableLiveData(dao.getMostRecentRate()))
            } catch (e: Exception) {
                Results.Error(e)
            }
        }
}