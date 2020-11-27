package com.octacoresoftwares.kudifx.ui

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.databinding.ActivityMainBinding
import com.octacoresoftwares.kudifx.local.AppDatabase
import com.octacoresoftwares.kudifx.remote.Service
import com.octacoresoftwares.kudifx.repo.*
import com.octacoresoftwares.kudifx.ui.spinner.Country
import com.octacoresoftwares.kudifx.ui.spinner.CountryAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val originCountries by lazy {
        val names = resources.getStringArray(R.array.origin_entries)
        generateCountries(names, R.array.origin_currency_icons)
    }

    private val destinationCountries by lazy {
        val names = resources.getStringArray(R.array.destination_entries)
        generateCountries(names, R.array.destination_currency_icons)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val remote = NetworkRepoImpl(Service.createService()) as NetworkRepo
        val local = DatabaseRepoImpl(AppDatabase.getDatabase(this).ratesDao()) as DatabaseRepo
        val repo = Repository(local, remote)

        val model = ViewModelProvider(
            this,
            MainActivityViewModelFactory(repo)
        )[MainActivityViewModel::class.java]

        val originAdapter = CountryAdapter(this, R.layout.currency_spinner, originCountries)
        val destinationAdapter = CountryAdapter(this, R.layout.currency_spinner, destinationCountries)
        binding.currentAdapter = originAdapter
        binding.destinationAdapter = destinationAdapter
        binding.spinnerListener = model.listener

        model.getAllRates()
        model.getMostRecentRate()
    }

    @SuppressLint("Recycle")
    private fun generateCountries(names: Array<String>, flagsArray: Int): List<Country> {
        var position = 0
        val countries = mutableListOf<Country>()
        val flags = resources.obtainTypedArray(flagsArray)
        names.forEach { name ->
            countries.add(Country(shortName = name, flagUrl = flags.getResourceId(position, 0)))
            position++
        }
        return countries
    }
}