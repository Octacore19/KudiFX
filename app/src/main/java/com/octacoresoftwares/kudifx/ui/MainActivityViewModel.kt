package com.octacoresoftwares.kudifx.ui

import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.Entry
import com.octacoresoftwares.kudifx.BR
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.local.Latest
import com.octacoresoftwares.kudifx.local.Rates
import com.octacoresoftwares.kudifx.ui.spinner.Country
import com.octacoresoftwares.kudifx.utils.*
import javax.inject.Inject

private const val TAG = "MainActivity VM"

class MainActivityViewModel @Inject constructor(private val context: Context) : ObservableViewModel() {
    val textRes = R.string.mid_market
    val defaultDestination = 7
    val defaultOrigin = 0

    @get: Bindable
    var rates: List<Double>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.rates)
        }

    @get: Bindable
    var latestRate: Latest? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.latestRate)
        }

    @get: Bindable
    var allRates: List<Rates>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.allRates)
        }

    @get: Bindable
    var allRatesAvailable: List<Latest>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.allRatesAvailable)
        }

    @get: Bindable
    var timestamp = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.timestamp)
        }

    @get: Bindable
    var answer = ""
        set(value) {
            field = value
            destinationRateHint = if (field.isNotEmpty()) {
                field
            } else {
                1.times(rate).toBigDecimal().toPlainString()
            }
            notifyPropertyChanged(BR.answer)
        }

    @get: Bindable
    var originRateText = ""
        set(value) {
            field = value
            destinationRateText = ""
            answer = if (field.isNotEmpty()) {
                val head = field.toDouble()
                head.times(rate).toBigDecimal().toPlainString()
            } else {
                ""
            }
            notifyPropertyChanged(BR.originRateText)
        }

    @get: Bindable
    var destinationRateText = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.destinationRateText)
        }

    @get: Bindable
    var originCountrySelected: Country? = null
        set(value) {
            field = value
            field?.let { country ->
                latestRate?.let { latest ->
                    val rate = returnRate(country.shortName, latest.rates, context)
                    originRateHint = rate.toInt().toString()
                    originLabel = country.shortName
                }
            }
            notifyPropertyChanged(BR.originCountrySelected)
        }

    @get:Bindable
    var destinationCountrySelected: Country? = null
        set(value) {
            field = value
            field?.let { country ->
                latestRate?.let { latest ->
                    rate = returnRate(country.shortName, latest.rates, context)
                    allRates?.let {
                        returnRates(country.shortName, it, context)
                    }?.let {
                        rates = it
                    }
                    allRatesAvailable?.let {
                        returnDays(it, numberOfHistoryDays)
                    }?.let {
                        labels = it
                    }
                    entries = fillEntryList(rates, numberOfHistoryDays)
                    destinationRateHint = rate.toBigDecimal().toPlainString()
                    destinationLabel = country.shortName
                    timestamp = getTime(latest.timestamp)
                }
            }
            notifyPropertyChanged(BR.destinationCountrySelected)
        }

    @get: Bindable
    var originRateHint = ""
        private set(value) {
            field = value
            notifyPropertyChanged(BR.originRateHint)
        }

    @get: Bindable
    var destinationRateHint = ""
        private set(value) {
            field = value
            notifyPropertyChanged(BR.destinationRateHint)
        }

    @get: Bindable
    var originLabel = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.originLabel)
        }

    @get: Bindable
    var destinationLabel = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.destinationLabel)
        }

    @get: Bindable
    var numberOfHistoryDays = 30
        set(value) {
            field = value
            notifyPropertyChanged(BR.numberOfHistoryDays)
        }

    @get: Bindable
    var entries: List<Entry>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.entries)
        }

    @get: Bindable
    var labels: List<String>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.labels)
        }

    private var rate = 0.0
        private set(value) {
            field = value
            if (originRateText.isNotEmpty()) {
                val head = originRateText.toDouble()
                destinationRateText = head.times(field).toBigDecimal().toPlainString()
            }
        }

    fun convertAnswer() {
        if (answer.isNotEmpty()) {
            destinationRateText = answer.toBigDecimal().toPlainString()
        }
    }
}

