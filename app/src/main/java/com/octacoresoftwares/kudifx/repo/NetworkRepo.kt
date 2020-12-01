package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import com.octacoresoftwares.kudifx.remote.LatestRates

/**
 * [NetworkRepo]
 * Interface that controls the fetching of data
 * from the Network API
 */
interface NetworkRepo {
    /**
     * [getLatestRate]
     * Method that gets the latest Rate from
     * over the Network API.
     * [key] is the API key required by the endpoint
     * @return [Results<LiveData<LatestRates>]
     */
    suspend fun getLatestRate(key: String): Results<LiveData<LatestRates>>
}