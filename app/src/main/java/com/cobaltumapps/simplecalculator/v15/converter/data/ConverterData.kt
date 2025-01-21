package com.cobaltumapps.simplecalculator.v15.converter.data

import android.graphics.drawable.Drawable
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType

/** Класс предоставляющий для каждого тип конвертера информацию в виде, названия, иконки, типа и списка моделей самих конвертеров */
data class ConverterData(
    var title: String = "",
    var drawable: Drawable? = null,
    var unitType: ConverterType = ConverterType.None,
    var converterUnitsModel: ConverterUnitsModel = ConverterUnitsModel()
)