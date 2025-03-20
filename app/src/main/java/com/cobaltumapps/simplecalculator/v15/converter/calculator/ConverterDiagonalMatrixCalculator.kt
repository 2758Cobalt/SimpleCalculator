package com.cobaltumapps.simplecalculator.v15.converter.calculator

import java.math.BigDecimal
import java.math.MathContext

class ConverterDiagonalMatrixCalculator {
    private var valuesToConvertArray: Array<Array<Double>> = arrayOf(arrayOf())

    fun setNewValuesToConvert(newValues: Array<Array<Double>>) {
        valuesToConvertArray = newValues
    }

    private fun getValue(userInput: String, selectedItemPos: Int): Array<Number> {
        val resultArray = mutableListOf<Number>()

        if (valuesToConvertArray.isNotEmpty() && selectedItemPos >= 0 && userInput.isNotEmpty() && userInput != "0") {
            for (column in valuesToConvertArray.indices) {
                try {
                    val valueToConvert = valuesToConvertArray[selectedItemPos][column]

                    val result = BigDecimal(userInput).multiply(
                        valueToConvert.toBigDecimal(), MathContext.DECIMAL128)

                    resultArray.add(result)

                } catch (ex: ArrayIndexOutOfBoundsException) {
                    return arrayOf()
                }

            }

        }
        return resultArray.toTypedArray()
    }

    fun calculate(userInput: String, selectedItemPos: Int): Array<Number> {
        return getValue(userInput, selectedItemPos)
    }

}