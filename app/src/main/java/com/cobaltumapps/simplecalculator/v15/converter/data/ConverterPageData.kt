package com.cobaltumapps.simplecalculator.v15.converter.data

import android.graphics.drawable.Drawable
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType

/** Информация о странице (фрагмента) конвертера */
data class ConverterPageData(
    var title: String = "N/Ac",
    var drawable: Drawable? = null,
    var unitType: ConverterType = ConverterType.None,
)