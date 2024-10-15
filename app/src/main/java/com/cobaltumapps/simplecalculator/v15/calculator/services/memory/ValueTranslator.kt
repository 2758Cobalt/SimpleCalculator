package com.cobaltumapps.simplecalculator.v15.calculator.services.memory


import android.util.Log

/**
Класс, преобразующий число типа Double эквивалентному числу  типа Int
*/

class ValueTranslator {
    fun translate(value: Double): String {
        return(
                try {
                    if (value % 1 == 0.0) value.toInt()
                    else value
                }
                catch (ex: NumberFormatException) {
                    Log.d(LOG_TAG, "NumberFormatException: Value can't translated to Int")
                    value
                }

                ).toString()

    }

    companion object {
        const val LOG_TAG = "SC_ValueTranslator"
    }
}