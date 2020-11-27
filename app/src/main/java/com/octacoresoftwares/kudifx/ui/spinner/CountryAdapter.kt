package com.octacoresoftwares.kudifx.ui.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.databinding.CurrencySpinnerBinding
import com.octacoresoftwares.kudifx.databinding.CurrencySpinnerDropdownListBinding

class CountryAdapter(
    private val context: Context,
    @LayoutRes private val layoutResId: Int,
    private val countries: List<Country>
): BaseAdapter() {

    override fun getCount() = countries.size

    override fun getItem(p0: Int) = countries[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var rootView = convertView
        if (rootView == null) {
            val binding = DataBindingUtil.inflate<CurrencySpinnerBinding>(LayoutInflater.from(context), layoutResId, parent, false)
            binding.country = countries[position]
            rootView = binding.root
        }
        return rootView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var rootView = convertView
        if (rootView == null) {
            val binding = DataBindingUtil.inflate<CurrencySpinnerDropdownListBinding>(LayoutInflater.from(context), R.layout.currency_spinner_dropdown_list, parent, false)
            binding.country = countries[position]
            rootView = binding.root
        }
        return rootView
    }
}
