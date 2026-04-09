package com.rodrip.marketya.core.presentation.utils

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

fun Double.roundTo2Decimals(): Double {
    return (this * 100).roundToInt() / 100.0
}

fun Double.toLocalPriceAndSimbol(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return formatter.format(this)
}