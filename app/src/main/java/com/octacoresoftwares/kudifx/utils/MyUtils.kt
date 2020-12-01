package com.octacoresoftwares.kudifx.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.data.Entry
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.local.Latest
import com.octacoresoftwares.kudifx.local.Rates
import com.octacoresoftwares.kudifx.ui.spinner.Country
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

fun returnRate(matcher: String, rates: Rates, context: Context): Double {
    val list = context.resources.getStringArray(R.array.destination_entries)
    return when (matcher) {
        list[0] -> rates.BTC
        list[1] -> rates.CAD
        list[2] -> rates.EUR
        list[3] -> rates.GBP
        list[4] -> rates.INR
        list[5] -> rates.JPY
        list[6] -> rates.NGN
        list[7] -> rates.PLN
        else -> rates.USD
    }
}

fun returnDays(allLatest: List<Latest>, threshold: Int): List<String> {
    val days = mutableListOf<String>()
    var position = 0
    allLatest.forEach {
        if (position < threshold) {
            days.add(it.day)
            position++
        }
    }
    return days
}

fun returnRates(matcher: String, rates: List<Rates>, context: Context): List<Double> {
    val list = context.resources.getStringArray(R.array.destination_entries)
    val gottenRate = mutableListOf<Double>()
    when (matcher) {
        list[0] -> {
            rates.forEach {
                gottenRate.add(it.BTC)
            }
        }
        list[1] -> {
            rates.forEach {
                gottenRate.add(it.CAD)
            }
        }
        list[2] -> {
            rates.forEach {
                gottenRate.add(it.EUR)
            }
        }
        list[3] -> {
            rates.forEach {
                gottenRate.add(it.GBP)
            }
        }
        list[4] -> {
            rates.forEach {
                gottenRate.add(it.INR)
            }
        }
        list[5] -> {
            rates.forEach {
                gottenRate.add(it.JPY)
            }
        }
        list[6] -> {
            rates.forEach {
                gottenRate.add(it.NGN)
            }
        }
        list[7] -> {
            rates.forEach {
                gottenRate.add(it.PLN)
            }
        }
        list[8] -> {
            rates.forEach {
                gottenRate.add(it.USD)
            }
        }
    }
    return gottenRate
}

fun fillEntryList(rates: List<Double>?, threshold: Int): List<Entry> {
    val entries = mutableListOf<Entry>()
    var position = 0
    rates?.forEach {
        if (position < threshold) {
            entries.add(Entry(position.toFloat(), it.toFloat()))
            position++
        }
    }
    return entries
}

@SuppressLint("Recycle")
fun generateCountries(names: Array<String>, flagsArray: Int, context: Context): List<Country> {
    var position = 0
    val countries = mutableListOf<Country>()
    val flags = context.resources.obtainTypedArray(flagsArray)
    names.forEach { name ->
        countries.add(Country(shortName = name, flagUrl = flags.getResourceId(position, 0)))
        position++
    }
    return countries
}

fun generateRate(data: com.octacoresoftwares.kudifx.remote.Rates) = Rates(
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

fun getDateTime(s: Long): HashMap<String, Any> {
    val sdf = SimpleDateFormat("dd MMM", Locale.ENGLISH)
    val netDate = Date(s)
    val calendar = Calendar.getInstance()
    calendar.time = netDate

    val year = calendar.get(Calendar.YEAR)
    var day = ""
    try {
        day = sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
    return hashMapOf(
        "day" to day,
        "year" to year
    )
}

fun getTime(s: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    val netDate = Date(s.times(1000))
    return sdf.format(netDate)
}

@RequiresApi(Build.VERSION_CODES.N)
fun checkNetwork(context: Context, callback: NetworkUtilsCallback) {
    try {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                callback.isConnected(value = STATUS.AVAILABLE)
            }

            override fun onLost(network: Network) {
                callback.isConnected(value = STATUS.LOST)
            }

            override fun onUnavailable() {
                callback.isConnected(value = STATUS.UNAVAILABLE)
            }
        })
    } catch (e: Exception) {
        Log.e("NetworkUtils", "Exception occurred in checkNetwork method", e)
        callback.isConnected(value = STATUS.ERROR)
    }
}

enum class STATUS(val value: Int) {
    AVAILABLE(0),
    LOST(-1),
    UNAVAILABLE(-2),
    ERROR(-10)
}
interface NetworkUtilsCallback {
    fun isConnected(value: STATUS)
}