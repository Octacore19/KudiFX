package com.octacoresoftwares.kudifx.utils

import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.octacoresoftwares.kudifx.ui.spinner.CountryAdapter
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["adapter"])
fun Spinner.setSpinnerAdapter(adapt: CountryAdapter){
    adapter = adapt
}

@BindingAdapter("onItemSelected")
fun Spinner.setSpinnerItemSelectedListener(listener: AdapterView.OnItemSelectedListener?) {
    onItemSelectedListener = listener
}

@BindingAdapter(value = ["setImage"])
fun ImageView.setImage(url: Int){
    this.setImageResource(url)
}