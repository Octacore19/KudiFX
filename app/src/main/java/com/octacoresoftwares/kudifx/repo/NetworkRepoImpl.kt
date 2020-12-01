package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.octacoresoftwares.kudifx.remote.NetworkApi
import com.octacoresoftwares.kudifx.remote.LatestRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * [NetworkRepoImpl] - concrete implementation of [NetworkRepo]
 * @constructor - [NetworkApi]
 */
class NetworkRepoImpl @Inject constructor(private val api: NetworkApi): NetworkRepo {

    /**
     * [getLatestRate]
     * Implementation of method that gets Latest rate
     * from over the network API.
     * [key] is the API key required by the endpoint
     * @return [Results<LiveData<LatestRates>]
     */
    override suspend fun getLatestRate(key: String): Results<LiveData<LatestRates>> =
        withContext(Dispatchers.IO) {
            try {
                Results.Success(MutableLiveData(api.getLatestRate(key)))
            } catch (e: Exception) {
                Results.Error(e)
            }
        }
}