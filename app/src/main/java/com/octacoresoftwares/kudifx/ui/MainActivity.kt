package com.octacoresoftwares.kudifx.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.local.AppDatabase
import com.octacoresoftwares.kudifx.remote.Service
import com.octacoresoftwares.kudifx.repo.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val remote = NetworkRepoImpl(Service.createService()) as NetworkRepo
        val local = DatabaseRepoImpl(AppDatabase.getDatabase(this).ratesDao()) as DatabaseRepo
        val repo = Repository(local, remote)

        val model = ViewModelProvider(
            this,
            MainActivityViewModelFactory(repo)
        )[MainActivityViewModel::class.java]

        val key = resources.getString(R.string.access_key)
        model.getLatestRate(key = key)
    }
}