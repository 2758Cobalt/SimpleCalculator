package com.cobaltumapps.simplecalculator.converters

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter


class VolumeConverter : UnitConverter() {

    init {
        valuesToConvert = arrayOf(
            arrayOf(1.0, 1e3, 1e6, 28320.0, 16.387, 3785.4),                                // Миллилитры
            arrayOf(1e-3, 1.0, 1e3, 28.317, (1 / 61.024), 3.7854),                          // Литры
            arrayOf(1e-6, 1e-3, 1.0, (1 / 35.315), (1 / 61020.0), (1 /  264.2)),            // Кубические метры
            arrayOf((1 / 28320.0), (1 / 28.317), 35.315, 1.0, (1 / 1728.0), (1 / 7.481)),   // Кубические футы
            arrayOf( (1 /16.3871), 61.024, 61020.0, 1728.0, 1.0, 231.0),                    // Кубические дюймы
            arrayOf((1 / 3785.0), (1 / 3.785), 264.2, 7.48, (1 / 231.0), 1.0)               // Американские галлоны
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Установка заголовка
        title = getString(R.string.converter_volume)

        // Установка спец. символов
        specialSymbols = resources.getStringArray(R.array.volume_symbols).toList()

        // Устанвока единиц изм.
        unitsParams = resources.getStringArray(R.array.volume_unit).toList()

        // Установка иконки
        thumbnailResource = R.drawable.ic_volume


    }

}