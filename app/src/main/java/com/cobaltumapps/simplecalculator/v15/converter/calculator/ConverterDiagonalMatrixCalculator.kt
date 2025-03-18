package com.cobaltumapps.simplecalculator.v15.converter.calculator

import java.math.BigDecimal
import java.math.MathContext

class ConverterDiagonalMatrixCalculator {
    private var valuesToConvertArray: Array<Array<Double>> = arrayOf(arrayOf())

    fun setNewValuesToConvert(newValues: Array<Array<Double>>) {
        valuesToConvertArray = newValues
    }

    private fun getValue(sourceValue: Number, selectedItemPos: Int): Number {
        var result = 0.0

        if (valuesToConvertArray.isNotEmpty() && selectedItemPos >= 0) {
            for (column in valuesToConvertArray.indices) {
                try {
                    val valueToConvert = valuesToConvertArray[selectedItemPos][column]
                    result = BigDecimal(sourceValue.toDouble()).also {
                        it.multiply(
                            valueToConvert.toBigDecimal(),
                            MathContext.DECIMAL128
                        )
                    }.toDouble()

                } catch (ex: ArrayIndexOutOfBoundsException) {
                    return -256
                }

            }

        }
        return result
    }

}