package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.octacoresoftwares.kudifx.remote.NetworkApi
import com.octacoresoftwares.kudifx.remote.model.LatestRates
import com.octacoresoftwares.kudifx.remote.model.SymbolsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepoImpl constructor(
    private val service: NetworkApi
): NetworkRepo {

    override suspend fun getLatestRate(query: Map<String, String>): Results<LiveData<LatestRates>> =
        withContext(Dispatchers.IO) {
            try {
                Results.Success(MutableLiveData(service.getLatestRate(query)))
            } catch (e: Exception) {
                Results.Error(e)
            }
        }
}