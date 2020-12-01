package com.octacoresoftwares.kudifx.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.octacoresoftwares.kudifx.App
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.databinding.ActivityMainBinding
import com.octacoresoftwares.kudifx.repo.Repository
import com.octacoresoftwares.kudifx.utils.NetworkUtilsCallback
import com.octacoresoftwares.kudifx.utils.STATUS
import com.octacoresoftwares.kudifx.utils.checkNetwork
import kotlinx.coroutines.Job
import javax.inject.Inject

private const val PERMISSIONS_REQUEST_ENABLE_INTERNET = 1000

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
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val model = ViewModelProvider(this, factory)[MainActivityViewModel::class.java]
        presenter = MainActivityPresenter(model, repo, this, applicationContext)

        binding.lifecycleOwner = this
        binding.vm = model
        binding.presenter = presenter

        checkNetwork(this, object : NetworkUtilsCallback {
            override fun isConnected(value: STATUS) {
                when (value) {
                    STATUS.LOST -> {
                        buildSnackToSettings(
                            "Ensure you have Internet Connection to get latest rate"
                        ) {
                            val intent = Intent(Settings.ACTION_SETTINGS)
                            startActivityForResult(intent, PERMISSIONS_REQUEST_ENABLE_INTERNET)
                        }
                    }
                    STATUS.UNAVAILABLE -> {
                        buildSnackToSettings(
                            "Ensure you have Internet Connection to get latest rate"
                        ) {
                            val intent = Intent(Settings.ACTION_SETTINGS)
                            startActivityForResult(intent, PERMISSIONS_REQUEST_ENABLE_INTERNET)
                        }
                    }
                    STATUS.AVAILABLE -> {
                        snackbar?.let {
                            if (it.isShown)
                                it.dismiss()
                        }
                    }
                    STATUS.ERROR -> {

                    }
                }
            }
        })
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

    private fun buildSnackToSettings(text: String, listener: View.OnClickListener) {
        snackbar = Snackbar.make(binding.root, text, Snackbar.LENGTH_INDEFINITE)
        snackbar?.setAction("Settings", listener)
        snackbar?.show()
    }
}