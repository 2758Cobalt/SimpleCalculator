package com.cobaltumapps.simplecalculator.v15.calculator.components.display.formatter

class DisplayFormatter {
    private val calculationResultSeparator = CalculationResultSeparator()

    fun formatResult(sourceResult: String): String {
        return calculationResultSeparator.separateString(sourceResult)
    }

}