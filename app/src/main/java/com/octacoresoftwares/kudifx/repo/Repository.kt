package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import com.octacoresoftwares.kudifx.local.Latest
import com.octacoresoftwares.kudifx.local.Rates

interface Repository {
    suspend fun getAllRates(): Results<LiveData<List<Rates>>>
    suspend fun getAllLatestRate(): Results<LiveData<List<Latest>>>
    suspend fun getMostRecentRate(): Results<LiveData<Latest>>
    suspend fun saveLatestRate(key: String): Results<Int>
}