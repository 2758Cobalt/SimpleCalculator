package com.cobaltumapps.simplecalculator.converters

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter


class LengthConverter : UnitConverter() {

    init {
        valuesToConvert = arrayOf(
            arrayOf(1.0, 1000.0, 1e6, 1e7, 1e8, 1e9, 1e12),             // Нанометры
            arrayOf(0.001, 1.0, 1000.0, 1e4, 1e5, 1e6, 1e9),            // Микрометры
            arrayOf(1e-6, 1e-3, 1.0, 1e-1, 1e-2, 1e-3, 1e-6),           // Миллиметры
            arrayOf(1e-7, 1e-4, 1e-1, 1.0, 1e-1, 1e-2, 1e-5),           // Сантиметры
            arrayOf(1e-8, 1e-5, 1e-2, 1e-1, 1.0, 1e-1, 1e-4),           // Дециметры
            arrayOf(1e-9, 1e-6, 1e-3, 1e-2, 1e-1, 1.0, 1e-4),           // Метры
            arrayOf(1e-12, 1e-9, 1e-6, 1e-5, 1e-4, 1e-3, 1.0)           // Километры
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Установка заголовка
        title = getString(R.string.converter_length)

        // Установка спец. символов
        specialSymbols = resources.getStringArray(R.array.length_symbols).toList()

        // Устанвока единиц изм.
        val unitsParams = resources.getStringArray(R.array.length_unit).toList()

        // Установка иконки
        thumbnailResource = R.drawable.ic_length

    }

}