package com.cobaltumapps.simplecalculator.data.extra.selector

import android.graphics.drawable.Drawable
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType

/**
 * @param calculatorId keep unique calculator id
 * @param title title of the calculator
 * @param type the type of calculator */

data class ExtraSelectableCalculatorInfo(
    val calculatorId: String,
    val title: String,
    val drawable: Drawable?,
    val previewValues: String,
    val type: ExtraCalculatorType = ExtraCalculatorType.Unit
)