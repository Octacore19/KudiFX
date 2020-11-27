package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import com.octacoresoftwares.kudifx.local.Latest
import com.octacoresoftwares.kudifx.local.Rates
import com.octacoresoftwares.kudifx.remote.LatestRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val local: DatabaseRepo,
    private val remote: NetworkRepo
) {

    suspend fun getAllRates() = local.getAllRates()

    suspend fun getAllLatestRate() = local.getAllLatestRate()

    suspend fun getMostRecentRate() = local.getMostRecentRate()

    suspend fun saveLatestRate(key: String): Results<Int> =
        withContext(Dispatchers.IO) {
            when (val result = remote.getLatestRate(key)) {
                is Results.Success<LiveData<LatestRates>> -> {

                    result.data.value?.let { data ->
                        val latest =
                            Latest(timestamp = data.timestamp, date = data.date, base = data.base)
                        val rates = generateRate(data = data.rates)
                        latest.rates = rates
                        local.insertLatestRate(latest)
                    }
                    Results.Success(0)
                }
                is Results.Error -> Results.Error(result.exception)
            }
        }

    private fun generateRate(data: com.octacoresoftwares.kudifx.remote.Rates) = Rates(
        BTC = data.BTC,
        CAD = data.CAD,
        EUR = data.EUR,
        GBP = data.GBP,
        INR = data.INR,
        JPY = data.JPY,
        NGN = data.NGN,
        PLN = data.PLN,
        USD = data.USD,
    )
}