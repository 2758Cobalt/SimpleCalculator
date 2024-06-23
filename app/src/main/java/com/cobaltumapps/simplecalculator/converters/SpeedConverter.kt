package com.cobaltumapps.simplecalculator.converters

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter


class SpeedConverter : UnitConverter() {

    init {
        valuesToConvert = arrayOf(
            arrayOf(9.2656693110598e-10, 0.277778, 1.0, 0.000277778, 0.621371, 0.539957, 0.911344),                   // Км/ч (километры в час)
            arrayOf(1.4911649311738e-9, 0.44704, 1.609344, 0.00044704, 1.0, 0.00027, 0.868976),                       // Мл/ч (миль в час)
            arrayOf(3.3356409519815e-9, 1000.0, 3600.0, 1.0, 2236.9362920544, 1943.8444924574, 3280.84),              // Км/с (километры в секунду)
            arrayOf(5.3681937522257e-10, 83333.0, 5793.6384, 1.609344, 3600.0, 1.0, 3128.31447087),                   // Мл/с (миль в секунду)
            arrayOf(3.3356e-9, 1.0, 3.6, 1000.0, 2.23694, 1.94384, 3.28084),                                          // М/с (метры в секунду)
            arrayOf(1.7160019563934e-10, 0.5144, 1.851999999984, 0.000514, 1.1507794480136, 0.00031966095778156, 1.0),// Уз (узлы)
            arrayOf(1.0, 299792458.0, 1079252848.8, 299792.458, 670616629.3844, 186282.39705122, 582749918.36357)     // Скорость света (c=299 792 458)
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Заголовок
        title = getString(R.string.converter_speed)

        // Установка спец. символов
        specialSymbols = resources.getStringArray(R.array.speed_symbols).toList()

        // Устанвока единиц изм.
        unitsParams = resources.getStringArray(R.array.speed_unit).toList()

        // Установка иконки
        thumbnailResource = R.drawable.ic_speed

    }

}