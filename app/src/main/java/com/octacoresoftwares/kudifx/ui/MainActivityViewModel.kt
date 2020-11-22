package com.octacoresoftwares.kudifx.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.octacoresoftwares.kudifx.local.Latest
import com.octacoresoftwares.kudifx.local.Rates
import com.octacoresoftwares.kudifx.repo.Repository
import com.octacoresoftwares.kudifx.repo.Results
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repo: Repository): ViewModel() {

    fun getLatestRate(key: String) {
        viewModelScope.launch {
            when(val result = repo.saveLatestRate(key)) {
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