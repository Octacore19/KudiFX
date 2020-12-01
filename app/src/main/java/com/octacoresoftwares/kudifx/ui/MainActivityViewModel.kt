package com.octacoresoftwares.kudifx.ui

import android.content.Context
import androidx.databinding.Bindable
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
    var progress = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.progress)
        }

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
            field?.let { latest ->
                originCountrySelected?.let {
                    progress = field == null
                    val rate = returnRate(it.shortName, latest.rates, context)
                    originRateHint = rate.toInt().toString()
                    originLabel = it.shortName
                }
                destinationCountrySelected?.let {
                    progress = field == null
                    rate = returnRate(it.shortName, latest.rates, context)
                    destinationLabel = it.shortName
                    destinationRateHint = if (originRateText.isNotEmpty()) {
                        val head = originRateText.toDouble()
                        head.times(rate).toBigDecimal().toPlainString()
                    } else {
                        rate.toBigDecimal().toPlainString()
                    }
                }
                timestamp = getTime(latest.timestamp)
            }
            notifyPropertyChanged(BR.latestRate)
        }

    @get: Bindable
    var allRates: List<Rates>? = null
        set(value) {
            field = value
            progress = field == null
            field?.let { rates ->
                destinationCountrySelected?.let {
                    this.rates = returnRates(it.shortName, rates, context)
                    entries = fillEntryList(this.rates, numberOfHistoryDays)
                }
            }
            notifyPropertyChanged(BR.allRates)
        }

    @get: Bindable
    var allRatesAvailable: List<Latest>? = null
        set(value) {
            field = value
            progress = field == null
            field?.let { allRates ->
                destinationCountrySelected?.let {
                    labels = returnDays(allRates, numberOfHistoryDays)
                }
            }
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
            notifyPropertyChanged(BR.answer)
        }

    @get: Bindable
    var originRateText = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.originRateText)

            destinationRateText = ""
            answer = if (field.isNotEmpty()) {
                val head = field.toDouble()
                head.times(rate).toBigDecimal().toPlainString()
            } else {
                ""
            }

            destinationRateHint = if (answer.isNotEmpty()) {
                answer
            } else {
                1.times(rate).toBigDecimal().toPlainString()
            }
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
            progress = true
            field = value
            notifyPropertyChanged(BR.originCountrySelected)
        }

    @get:Bindable
    var destinationCountrySelected: Country? = null
        set(value) {
            progress = true
            field = value
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
            val tempDestinationCountry = destinationCountrySelected
            destinationCountrySelected = tempDestinationCountry
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
        /*private set(value) {
            field = value
            if (originRateText.isNotEmpty()) {
                val head = originRateText.toDouble()
                destinationRateHint = head.times(field).toBigDecimal().toPlainString()
            }
        }*/
}

