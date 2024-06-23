package com.cobaltumapps.simplecalculator.converters

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter


class TimeConverter : UnitConverter() {
    init {
        valuesToConvert = arrayOf(
            arrayOf(1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20, 3.17e-22),             // Пикосекунды
            arrayOf(1e3, 1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20),                  // Наносекунды
            arrayOf(1e6, 1e3, 1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20),             // Миллисекунды
            arrayOf(1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20, 3.17e-22),             // Секунды
            arrayOf(1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20, 3.17e-22),             // Минуты
            arrayOf(1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20, 3.17e-22),             // Часы
            arrayOf(1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20, 3.17e-22),             // Дни
            arrayOf(1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20, 3.17e-22),             // Недели
            arrayOf(1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20, 3.17e-22),             // Года
            arrayOf(1.0, 1e-3, 1e-6, 1e-9, 1e-12, 1.6e-14, 2.7e-16, 1.16e-17, 1.65e-18, 3.17e-20, 3.17e-22)              // Века
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Установка заголовка
        title = getString(R.string.converter_time)

        // Установка спец. символов
        specialSymbols = resources.getStringArray(R.array.time_symbols).toList()

        // Устанвока единиц изм.
        val unitsParams = resources.getStringArray(R.array.time_unit).toList()

        // Установка иконки
        thumbnailResource = R.drawable.ic_time


    }

}