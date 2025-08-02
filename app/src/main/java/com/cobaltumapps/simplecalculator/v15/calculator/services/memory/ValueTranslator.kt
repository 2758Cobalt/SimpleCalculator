package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import android.util.Log
import java.math.BigDecimal

class ValueTranslator {
    fun translate(value: String): String {
        return try {
            val decimal = BigDecimal(value)

            if (decimal.stripTrailingZeros().scale() <= 0) decimal.toBigInteger().toString()
            else decimal.toPlainString()

        } catch (ex: NumberFormatException) {
            Log.d(LOG_TAG, "NumberFormatException: Value can't be parsed to BigDecimal")
            value
        }
    }

    companion object {
        const val LOG_TAG = "SC_ValueTranslator"
    }
}