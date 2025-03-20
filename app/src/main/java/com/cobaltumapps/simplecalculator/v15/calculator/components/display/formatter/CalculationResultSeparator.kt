package com.cobaltumapps.simplecalculator.v15.calculator.components.display.formatter

class CalculationResultSeparator {
    private val separatorSymbol = " "

    fun separateResult(sourceResult: String): String {
        var currentSymbol = 0

        val resultString = StringBuilder()

        sourceResult.reversed().forEach {
            if (currentSymbol == 3) {
                resultString.append(separatorSymbol)
                currentSymbol = 0
            }
            resultString.append(it)
            currentSymbol += 1
        }
        return resultString.reversed().toString()
    }

}