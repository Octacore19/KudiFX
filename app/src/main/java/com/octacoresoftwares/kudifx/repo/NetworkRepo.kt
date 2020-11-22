package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import com.octacoresoftwares.kudifx.local.model.Latest
import com.octacoresoftwares.kudifx.remote.model.LatestRates
import com.octacoresoftwares.kudifx.remote.model.SymbolsResponse

interface NetworkRepo {
    suspend fun getLatestRate(query: Map<String, String>): Results<LiveData<LatestRates>>
}