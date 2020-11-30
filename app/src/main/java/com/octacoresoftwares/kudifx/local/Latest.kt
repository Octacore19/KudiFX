package com.octacoresoftwares.kudifx.local

import androidx.room.*

data class LatestRate(
    @Embedded val latest: Latest,
    @Relation(
        parentColumn = "timestamp",
        entityColumn = "rateTimestamp"
    ) val rates: Rates
)

@Entity(tableName = "latest")
data class Latest(
    @PrimaryKey val timestamp: Long,
    val base: String,
    val date: String,
    val day: String,
    val year: String,
    val createdAt: Long = System.currentTimeMillis()
) {
    @Ignore lateinit var rates: Rates
}

@Entity(tableName = "rates")
data class Rates(
    @PrimaryKey var rateTimestamp: Long = 0L,
    var createdAt: Long = 0L,
    val BTC: Double,
    val CAD: Double,
    val EUR: Double,
    val GBP: Double,
    val INR: Double,
    val JPY: Double,
    val NGN: Double,
    val PLN: Double,
    val USD: Double
)