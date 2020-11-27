package com.octacoresoftwares.kudifx.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LatestRates(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Rates
): Parcelable

@Parcelize
data class Rates(
    val BTC: Double,
    val CAD: Double,
    val EUR: Double,
    val GBP: Double,
    val INR: Double,
    val JPY: Double,
    val NGN: Double,
    val PLN: Double,
    val USD: Double
): Parcelable