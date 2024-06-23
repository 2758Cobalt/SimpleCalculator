package com.cobaltumapps.simplecalculator.converters

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter


class DataConverter : UnitConverter() {

    init {
        valuesToConvert = arrayOf(
            arrayOf(1.0, 0.12, 1.25e-4, 1.25e-7, 1.25e-10, 1.25e-13, 1.25e-16, 1.25e-19, 1.25e-22, 1.25e-25),   // Бит
            arrayOf(8.0, 1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1e-15, 1e-18, 1e-21, 1e-24),                             // Байт
            arrayOf(8e3, 1e3, 1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1e-15, 1e-18, 1e-21),                               // Килобайт
            arrayOf(8e6, 1e6, 1e3, 1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1e-15, 1e-18),                                 // Мегабайт
            arrayOf(8e9, 1e9, 1e6, 1e3, 1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1e-15),                                   // Гигабайт
            arrayOf(8e12, 1e12, 1e9, 1e6, 1e3, 1.0, 1e-3, 1e-6, 1e-9, 1e-12),                                   // Терабайт
            arrayOf(8e15, 1e15, 1e12, 1e9, 1e6, 1e3, 1.0, 1e-3, 1e-6, 1e-9),                                    // Петабайт
            arrayOf(8e18, 1e18, 1e15, 1e12, 1e9, 1e6, 1e3, 1.0, 1e-3, 1e-6),                                    // Эксабайт
            arrayOf(8e21, 1e21, 1e18, 1e15, 1e12, 1e9, 1e6, 1e3, 1.0, 1e-3),                                    // Зеттабайт
            arrayOf(8e24, 1e24, 1e21, 1e18, 1e15, 1e12, 1e9, 1e6, 1e3, 1.0)                                     // Йоттабайт
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Установка заголовка
        title = getString(R.string.converter_data)

        // Установка спец. символов
        specialSymbols = resources.getStringArray(R.array.data_symbols).toList()

        // Установка единиц изм.
        val unitsParams = resources.getStringArray(R.array.data_unit).toList()

        // Иконка
        thumbnailResource = R.drawable.ic_data


    }

}