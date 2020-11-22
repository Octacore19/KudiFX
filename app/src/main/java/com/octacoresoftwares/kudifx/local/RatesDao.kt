package com.octacoresoftwares.kudifx.local

import androidx.room.*
import com.octacoresoftwares.kudifx.local.model.Latest
import com.octacoresoftwares.kudifx.local.model.LatestRate
import com.octacoresoftwares.kudifx.local.model.Rates

@Dao
abstract class RatesDao: LatestDao {

    suspend fun insertLatestRate(latest: Latest): Long {
        val res = _insertLatestRate(latest, latest.rates)
        if (res < 0L) {
            return -1L
        }
        return _insertLatest(latest)
    }

    @Query("SELECT * FROM rates")
    abstract suspend fun getAllRates(): List<Rates>

    suspend fun getAllLatestRates(): List<Latest> {
        val latestRate = _getAllLatestRates()
        val latest = mutableListOf<Latest>()
        latestRate.forEach {
            it.latest.rates = it.rates
            latest.add(it.latest)
        }
        return latest
    }

    private suspend fun _insertLatestRate(latest: Latest, rates: Rates): Long {
        rates.latestRateTimestamp = latest.timestamp
        return _insertRates(rates)
    }
}

@Dao
interface LatestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insertRates(rates: Rates): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insertLatest(latest: Latest): Long

    @Transaction
    @Query("SELECT * FROM latest")
    suspend fun _getAllLatestRates(): List<LatestRate>
}