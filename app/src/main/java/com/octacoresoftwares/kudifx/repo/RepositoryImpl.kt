package com.octacoresoftwares.kudifx.repo

import androidx.lifecycle.LiveData
import com.octacoresoftwares.kudifx.local.Latest
import com.octacoresoftwares.kudifx.local.Rates
import com.octacoresoftwares.kudifx.remote.LatestRates
import com.octacoresoftwares.kudifx.utils.generateRate
import com.octacoresoftwares.kudifx.utils.getDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class RepositoryImpl @Inject constructor(
    private val local: DatabaseRepo,
    private val remote: NetworkRepo
): Repository {

    override suspend fun getAllRates() = local.getAllRates()

    override suspend fun getAllLatestRate() = local.getAllLatestRate()

    override suspend fun getMostRecentRate() = local.getMostRecentRate()

    override suspend fun saveLatestRate(key: String): Results<Int> =
        withContext(Dispatchers.IO) {
            when (val result = remote.getLatestRate(key)) {
                is Results.Success<LiveData<LatestRates>> -> {

                    result.data.value?.let { data ->
                        val dateMap = getDateTime(data.timestamp.times(1000))
                        val latest = Latest(timestamp = data.timestamp, date = data.date, base = data.base,
                                day = dateMap["day"].toString(), year = dateMap["year"].toString())
                        val rates = generateRate(data = data.rates)
                        latest.rates = rates
                        local.insertLatestRate(latest)
                    }

                    Results.Success(0)
                }
                is Results.Error -> Results.Error(result.exception)
            }
        }
}