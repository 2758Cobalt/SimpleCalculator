package com.cobaltumapps.simplecalculator.converters

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter


class TemperatureConverter : UnitConverter() {

    init {
        valuesToConvert = arrayOf(
            arrayOf(1.0, (9.0 / 5.0), 273.15, 273.15 * 1.8, (4.0 / 5.0)),                   // Цельсий
            arrayOf((5.0 / 9.0), 1.0, 459.67 * (5.0 / 9.0), 459.67, (4.0 / 9.0)),           // Фаренгейт
            arrayOf(1.0, 9.0 / 5.0, 1.0, 9.0 / 5.0, 4.0 / 5.0),                             // Кельвин
            arrayOf((5.0 / 9.0), 1.0 - 459.67, 5.0 / 9.0, 1.0, 4.0 / 9.0),                  // Ранкин
            arrayOf(5.0 / 4.0, (9.0 / 4.0), 5.0 / 4.0 + 273.15, 9.0 / 4.0 + 491.67, 1.0)    // Реомюр
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Заголовок
        title = getString(R.string.converter_temperature)

        // Установка спец. символов
        specialSymbols = resources.getStringArray(R.array.temperature_symbols).toList()

        // Устанвока единиц изм.
        val unitsParams = resources.getStringArray(R.array.temperature_unit).toList()

        // Установка иконки
        thumbnailResource = R.drawable.ic_temperatures

    }
}