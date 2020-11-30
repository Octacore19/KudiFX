package com.octacoresoftwares.kudifx.ui

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.button.MaterialButtonToggleGroup
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.local.Latest
import com.octacoresoftwares.kudifx.local.Rates
import com.octacoresoftwares.kudifx.repo.Repository
import com.octacoresoftwares.kudifx.repo.RepositoryImpl
import com.octacoresoftwares.kudifx.repo.Results
import com.octacoresoftwares.kudifx.ui.spinner.CountryAdapter
import com.octacoresoftwares.kudifx.utils.generateCountries
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private const val TAG = "MainActivityPresenter"

class MainActivityPresenter(
    private val model: MainActivityViewModel,
    private val repo: Repository,
    private val owner: LifecycleOwner,
    private val context: Context
) {

    private val originCountries by lazy {
        val names = context.resources.getStringArray(R.array.origin_entries)
        generateCountries(names, R.array.origin_currency_icons, context)
    }
    private val destinationCountries by lazy {
        val names = context.resources.getStringArray(R.array.destination_entries)
        generateCountries(names, R.array.destination_currency_icons, context)
    }
    val originAdapter = CountryAdapter(context, R.layout.currency_spinner, originCountries)
    val destinationAdapter = CountryAdapter(context, R.layout.currency_spinner, destinationCountries)
    val spinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (parent?.id) {
                R.id.current_selection -> {
                    model.originCountrySelected = originCountries[position]
                }
                R.id.destination_selection -> {
                    model.destinationCountrySelected = destinationCountries[position]
                }
            }
        }
        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
    val buttonGroupListener = MaterialButtonToggleGroup.OnButtonCheckedListener { _, checkedId, isChecked ->
        if (isChecked) {
            when (checkedId) {
                R.id.thirty_days -> model.numberOfHistoryDays = 30
                R.id.ninety_days -> model.numberOfHistoryDays = 90
            }
        }
    }

    fun getAllLatestRate() = model.viewModelScope.launch {
        while (isActive) {
            when (val result = repo.getAllLatestRate()) {
                is Results.Success<LiveData<List<Latest>>> -> {
                    result.data.observe(owner) {
                        model.allRatesAvailable = it
                    }
                }
                is Results.Error -> {
                    Log.wtf(TAG, result.exception)
                }
            }
            delay(60.times(1000))
        }
    }
    fun getMostRecentRate() = model.viewModelScope.launch {
        while (isActive) {
            when (val result = repo.getMostRecentRate()) {
                is Results.Success<LiveData<Latest>> -> {
                    result.data.observe(owner) {
                        model.latestRate = it
                    }
                }
                is Results.Error -> {
                    Log.wtf(TAG, result.exception)
                }
            }
            delay(60.times(1000))
        }
    }
    fun getAllRates() = model.viewModelScope.launch {
        while (isActive) {
            when (val result = repo.getAllRates()) {
                is Results.Success<LiveData<List<Rates>>> -> {
                    result.data.observe(owner) {
                        model.allRates = it
                    }
                }
                is Results.Error -> {
                    Log.wtf(TAG, result.exception)
                }
            }
            delay(60.times(1000))
        }
    }
}