package com.cobaltumapps.simplecalculator.data.extra.selector

import android.os.Parcelable
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import kotlinx.parcelize.Parcelize

/**
 * @param calculatorId keep unique calculator id
 * @param title title of the calculator
 * @param type the type of calculator */

@Parcelize
data class ExtraSelectableCalculatorInfo(
    val calculatorId: String,
    val title: String,
    val drawableResId: Int?,
    val previewValues: String,
    val type: ExtraCalculatorType = ExtraCalculatorType.Unit
) : Parcelable