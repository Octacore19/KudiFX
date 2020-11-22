package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import com.octacoresoftwares.kudifx.remote.LatestRates

interface NetworkRepo {
    suspend fun getLatestRate(key: String): Results<LiveData<LatestRates>>
}