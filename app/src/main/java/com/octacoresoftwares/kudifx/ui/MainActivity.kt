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
    }
}