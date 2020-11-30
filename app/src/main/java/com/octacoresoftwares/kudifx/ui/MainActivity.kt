package com.octacoresoftwares.kudifx.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.octacoresoftwares.kudifx.App
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.databinding.ActivityMainBinding
import com.octacoresoftwares.kudifx.local.AppDatabase
import com.octacoresoftwares.kudifx.remote.RetrofitModule
import com.octacoresoftwares.kudifx.repo.*
import kotlinx.coroutines.Job
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var repo: Repository

    private var allRatesJob: Job? = null
    private var mostRecentJob: Job? = null
    private var allLatestJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val model = ViewModelProvider(this, factory)[MainActivityViewModel::class.java]
        presenter = MainActivityPresenter(model, repo, this, applicationContext)

        binding.lifecycleOwner = this
        binding.vm = model
        binding.presenter = presenter
    }

    override fun onStart() {
        super.onStart()
        allRatesJob = presenter.getAllRates()
        mostRecentJob = presenter.getMostRecentRate()
        allLatestJob = presenter.getAllLatestRate()
    }

    override fun onStop() {
        allRatesJob?.cancel()
        mostRecentJob?.cancel()
        allLatestJob?.cancel()
        super.onStop()
    }
}