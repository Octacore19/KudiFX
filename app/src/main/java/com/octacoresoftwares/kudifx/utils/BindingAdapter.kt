package com.octacoresoftwares.kudifx.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.button.MaterialButtonToggleGroup
import com.octacoresoftwares.kudifx.R
import com.octacoresoftwares.kudifx.ui.spinner.CountryAdapter

@BindingAdapter(value = ["mainTextRes", "text"], requireAll = true)
fun TextView.setText(res: Int, text: String) {
    val main = this.context.resources.getString(res)
    val content = SpannableString("$main $text GMT")
    content.setSpan(UnderlineSpan(), 0, content.length, 0)
    this.text = content
}

@BindingAdapter(value = ["adapter"])
fun Spinner.setSpinnerAdapter(adapt: CountryAdapter){
    adapter = adapt
}

@BindingAdapter(value = ["defaultSelection"])
fun Spinner.setDefaultSelection(position: Int) {
    setSelection(position)
}

@BindingAdapter("onItemSelected")
fun Spinner.setSpinnerItemSelectedListener(listener: AdapterView.OnItemSelectedListener?) {
    onItemSelectedListener = listener
}

@BindingAdapter("onButtonChecked")
fun MaterialButtonToggleGroup.addButtonCheckedListener(listener: MaterialButtonToggleGroup.OnButtonCheckedListener) {
    this.isSelectionRequired = true
    this.isSingleSelection = true
    this.check(R.id.thirty_days)
    this.addOnButtonCheckedListener(listener)
}

@BindingAdapter(value = ["setImage"])
fun ImageView.setImage(url: Int){
    this.setImageResource(url)
}

@BindingAdapter(value = ["entries", "labels"], requireAll = false)
fun LineChart.setDataEntries(entries: List<Entry>?, labels: List<String>?) {
    if (entries != null) {
        val lineDataSet = LineDataSet(entries, "rates")
        lineDataSet.axisDependency = YAxis.AxisDependency.RIGHT
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.isHighlightEnabled = false
        lineDataSet.lineWidth = 0f
        lineDataSet.circleHoleRadius = 2.5f
        lineDataSet.cubicIntensity = 0.2f
        lineDataSet.fillColor = ContextCompat.getColor(this.context, R.color.color_blue1)
        lineDataSet.setDrawFilled(true)
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)

        var formatter: ValueFormatter? = null
        if (labels != null) {
            formatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return labels[value.toInt()]
                }
            }
        }

        val xAxis = this.xAxis
        xAxis.granularity = 5f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = formatter
        xAxis.textColor = ContextCompat.getColor(this.context, R.color.color_grey1)
        xAxis.typeface = ResourcesCompat.getFont(this.context, R.font.ubuntu)
        xAxis.axisLineColor = Color.TRANSPARENT
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.setDrawGridLines(false)

        val yAxis = this.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.setDrawLabels(false)
        yAxis.axisLineColor = Color.TRANSPARENT

        this.setDrawGridBackground(false)
        this.setBackgroundColor(Color.TRANSPARENT)
        this.setPinchZoom(false)
        this.axisRight.isEnabled = false
        this.description.isEnabled = false
        this.legend.isEnabled = false

        this.data = LineData(lineDataSet)
        this.invalidate()
    }
}