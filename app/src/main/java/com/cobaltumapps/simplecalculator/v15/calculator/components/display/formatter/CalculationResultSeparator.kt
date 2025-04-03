package com.cobaltumapps.simplecalculator.v15.calculator.components.display.formatter

class CalculationResultSeparator {
    private val separatorSymbol = " "

    fun separateString(sourceString: String): String {
        val pointInString = sourceString.contains('.')

        var pointIsFound = false
        var currentSymbol = 0

        val resultString = StringBuilder()

        sourceString.reversed().forEach {
            if (pointInString && !pointIsFound) {
                resultString.append(it)

                if (it == '.') {
                    pointIsFound = true
                }
            }
            else {
                if (currentSymbol == 3) {
                    resultString.append(separatorSymbol)
                    currentSymbol = 0
                }
                resultString.append(it)
                currentSymbol += 1
            }
        }
        return resultString.reversed().toString()
    }

}