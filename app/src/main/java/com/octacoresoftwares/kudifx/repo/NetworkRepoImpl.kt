package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.octacoresoftwares.kudifx.remote.NetworkApi
import com.octacoresoftwares.kudifx.remote.LatestRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepoImpl constructor(
    private val service: NetworkApi
): NetworkRepo {

    override suspend fun getLatestRate(key: String): Results<LiveData<LatestRates>> =
        withContext(Dispatchers.IO) {
            try {
                Results.Success(MutableLiveData(service.getLatestRate(key)))
            } catch (e: Exception) {
                Results.Error(e)
            }
        }
}