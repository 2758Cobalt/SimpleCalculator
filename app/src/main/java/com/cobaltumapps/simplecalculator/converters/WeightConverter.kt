package com.cobaltumapps.simplecalculator.converters

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter

/* Конвертер веса */
class WeightConverter : UnitConverter() {

    init {
        valuesToConvert = arrayOf(
            arrayOf(1.0, 1e3, 1e6, 1e9, 1e12, 1e15, 1e18),              // Микрограммы
            arrayOf(0.001, 1.0, 1000.0, 1e6, 1e9, 1e12, 1e15, 1e18),    // Миллиграммы
            arrayOf(1e-6, 1e-3, 1.0, 1e-1, 1e-2, 1e-3, 1e-6),           // Граммы
            arrayOf(1e-9, 1e-6, 1e-3, 1.0, 1e3, 1e6, 1e9, 1e12),        // Килограммы
            arrayOf(1e-12, 1e-9, 1e-6, 1e-3, 1.0, 1e3, 1e6, 1e9),       // Тонны
            arrayOf(1e-15, 1e-12, 1e-9, 1e-6, 1e-3, 1.0, 1e3, 1e6),     // Килотонны
            arrayOf(1e-18, 1e-15, 1e-12, 1e-9, 1e-6, 1e-3, 1.0))        // Мегатонны

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Установка заголовка
        title = getString(R.string.converter_weight)

        // Установка спец. символов
        specialSymbols = resources.getStringArray(R.array.weight_symbols).toList()

        // Устанвока единиц изм.
        unitsParams = resources.getStringArray(R.array.weight_unit).toList()

        // Установка иконки
        thumbnailResource = R.drawable.ic_weight


    }

}