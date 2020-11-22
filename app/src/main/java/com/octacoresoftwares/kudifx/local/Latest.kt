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
    val date: String
) {
    @Ignore lateinit var rates: Rates
}

@Entity(tableName = "rates")
data class Rates(
    @PrimaryKey var rateTimestamp: Long = 0L,
    val AED: Double,
    val BTC: Double,
    val CAD: Double,
    val EUR: Double,
    val GBP: Double,
    val INR: Double,
    val JPY: Double,
    val KES: Double,
    val NGN: Double,
    val PLN: Double,
    val QAR: Double,
    val RSD: Double,
    val USD: Double,
    val XAF: Double
)