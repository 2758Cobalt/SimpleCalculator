package com.cobaltumapps.simplecalculator.converters

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter


class FrequencyConverter : UnitConverter() {

    init {
        valuesToConvert = arrayOf(
            arrayOf(1.0, 1e3, 1e6, 1e9),           // Герц
            arrayOf(1e-3, 1.0, 1e3, 1e6),          // Килогерц
            arrayOf(1e-6, 1e-3, 1.0, 1e3),         // Мегагерц
            arrayOf(1e-9, 1e-6, 1e-3, 1.0)         // Гигагерц
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Заголовок
        title = getString(R.string.converter_frequency)

        // Установка спец. символов
        specialSymbols = resources.getStringArray(R.array.frequency_symbols).toList()

        unitsParams = resources.getStringArray(R.array.frequency_unit).toList()

        // Установка иконки
        thumbnailResource = R.drawable.ic_frequency

    }

}