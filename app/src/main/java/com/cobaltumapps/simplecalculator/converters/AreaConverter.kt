package com.cobaltumapps.simplecalculator.converters

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter


class AreaConverter : UnitConverter() {

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Установка заголовка
        title = getString(R.string.converter_area)

        // Установка спец. символов
        specialSymbols = resources.getStringArray(R.array.area_symbols).toList()

        // Устанвока единиц изм.
        unitsParams = resources.getStringArray(R.array.area_unit).toList()

        // Установка иконки
        thumbnailResource = R.drawable.ic_area
    }

}