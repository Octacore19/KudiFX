package com.octacoresoftwares.kudifx.ui

import android.util.Log
import androidx.lifecycle.*
import com.octacoresoftwares.kudifx.local.model.Latest
import com.octacoresoftwares.kudifx.local.model.LatestRate
import com.octacoresoftwares.kudifx.local.model.Rates
import com.octacoresoftwares.kudifx.remote.model.LatestRates
import com.octacoresoftwares.kudifx.remote.model.SymbolsResponse
import com.octacoresoftwares.kudifx.repo.NetworkRepo
import com.octacoresoftwares.kudifx.repo.Repository
import com.octacoresoftwares.kudifx.repo.Results
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repo: Repository): ViewModel() {

    fun getLatestRate(query: Map<String, String>) {
        viewModelScope.launch {
            when(val result = repo.saveLatestRate(query)) {
                is Results.Success<Int> -> {
                    getAllRates()
                    getAllLatestRate()
                    Log.i("MainActivityViewModel", "Saved successfully: $result")
                }
                is Results.Error -> {
                    Log.wtf("MainActivityViewModel", result.exception)
                }
            }
        }
    }

    private fun getAllLatestRate() = viewModelScope.launch {
        when (val result = repo.getAllLatestRate()) {
            is  Results.Success<LiveData<List<Latest>>> -> {
                Log.i("MainActivityViewModel", " \nGetAllLatestRate Method\n" +
                        "Latest: ${result.data.value?.first()} \n" +
                        "Rate: ${result.data.value?.first()?.rates}")
            }
            is Results.Error -> {
                Log.wtf("MainActivityViewModel", result.exception)
            }
        }
    }

    private fun getAllRates() = viewModelScope.launch {
        when (val result = repo.getAllRates()) {
            is  Results.Success<LiveData<List<Rates>>> -> {
                Log.i("MainActivityViewModel", " \nGetAllRates Method\n" +
                        "Rates: ${result.data.value}")
            }
            is Results.Error -> {
                Log.wtf("MainActivityViewModel", result.exception)
            }
        }
    }
}

class MainActivityViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repo) as T
    }
}