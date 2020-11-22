package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import com.octacoresoftwares.kudifx.local.model.Latest
import com.octacoresoftwares.kudifx.local.model.Rates

interface DatabaseRepo {
    suspend fun insertLatestRate(latest: Latest)
    suspend fun getAllRates(): Results<LiveData<List<Rates>>>
    suspend fun getAllLatestRate(): Results<LiveData<List<Latest>>>
}