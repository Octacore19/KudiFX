package com.octacoresoftwares.kudifx.local

import androidx.room.*

@Dao
abstract class RatesDao: LatestDao {

    suspend fun insertLatestRate(latest: Latest): Long {
        _insertLatestRate(latest, latest.rates)
        return _insertLatest(latest)
    }

    @Query("SELECT * FROM rates")
    abstract suspend fun getAllRates(): List<Rates>

    suspend fun getMostRecentRate(): Latest {
        val latestRate = _getMostRecentRate()
        latestRate.latest.rates = latestRate.rates
        return latestRate.latest
    }

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
        rates.rateTimestamp = latest.timestamp
        rates.createdAt = latest.createdAt
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

    @Transaction
    @Query("SELECT * FROM latest WHERE createdAt = (SELECT MAX(createdAt) FROM latest)")
    suspend fun _getMostRecentRate(): LatestRate
}