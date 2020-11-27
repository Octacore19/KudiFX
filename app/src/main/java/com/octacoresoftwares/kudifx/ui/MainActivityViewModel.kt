package com.octacoresoftwares.kudifx.ui

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.local.Latest
import com.octacoresoftwares.kudifx.local.Rates
import com.octacoresoftwares.kudifx.repo.Repository
import com.octacoresoftwares.kudifx.repo.Results
import kotlinx.coroutines.launch

private const val TAG = "MainActivity VM"

class MainActivityViewModel(private val repo: Repository): ObservableViewModel() {

    fun getAllLatestRate() = viewModelScope.launch {
        when (val result = repo.getAllLatestRate()) {
            is  Results.Success<LiveData<List<Latest>>> -> {
                Log.i(TAG, " \nGetAllLatestRate Method\n" +
                        "Latest: ${result.data.value?.first()} \n" +
                        "Rate: ${result.data.value?.first()?.rates}")
            }
            is Results.Error -> {
                Log.wtf("MainActivityViewModel", result.exception)
            }
        }
    }

    fun getMostRecentRate() = viewModelScope.launch {
        when (val result = repo.getMostRecentRate()) {
            is Results.Success<LiveData<Latest>> -> {
                Log.i(TAG, " \nGetMostRecentRate Method\n" +
                        "Latest: ${result.data.value} \n" +
                        "Rate: ${result.data.value?.rates}")
            }
        }
    }

    fun getAllRates() = viewModelScope.launch {
        when (val result = repo.getAllRates()) {
            is  Results.Success<LiveData<List<Rates>>> -> {
                Log.i(TAG, " \nGetAllRates Method\n" +
                        "Rates: ${result.data.value}")
            }
            is Results.Error -> {
                Log.wtf(TAG, result.exception)
            }
        }
    }

    val listener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (parent?.id) {
                R.id.current_selection -> {
                    Log.i(TAG, "Item $position selected in current")
                }
                R.id.destination_selection -> {
                    Log.i(TAG, "Item $position selected in destination")
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) { }
    }
}

class MainActivityViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repo) as T
    }
}