package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import com.octacoresoftwares.kudifx.local.model.Latest
import com.octacoresoftwares.kudifx.local.model.Rates
import com.octacoresoftwares.kudifx.remote.model.LatestRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val local: DatabaseRepo,
    private val remote: NetworkRepo
) {

    suspend fun getAllRates() = local.getAllRates()

    suspend fun getAllLatestRate() = local.getAllLatestRate()

    suspend fun saveLatestRate(query: Map<String, String>): Results<Int> =
        withContext(Dispatchers.IO){
            when(val result = remote.getLatestRate(query)) {
                is Results.Success<LiveData<LatestRates>> -> {

                    result.data.value?.let { data ->
                        val rates = Rates(
                            BTC = data.rates.BTC,
                            CAD = data.rates.CAD,
                            EUR = data.rates.EUR,
                            GBP = data.rates.GBP,
                            INR = data.rates.INR,
                            JPY = data.rates.JPY,
                            NGN = data.rates.NGN,
                            PLN = data.rates.PLN,
                            RSD = data.rates.RSD,
                            USD = data.rates.USD
                        )

                        val latest = Latest(timestamp = data.timestamp, date = data.date, base = data.base)
                        latest.rates = rates

                        local.insertLatestRate(latest)
                    }
                    Results.Success(0)
                }
                is Results.Error -> Results.Error(result.exception)
            }
        }
}