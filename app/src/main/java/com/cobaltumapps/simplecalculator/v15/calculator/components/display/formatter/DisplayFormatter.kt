package com.cobaltumapps.simplecalculator.v15.calculator.components.display.formatter

class DisplayFormatter: Formatter {
    private val calculationResultSeparator = CalculationResultSeparator()

    override fun format(sourceString: String): String {
        return calculationResultSeparator.separateString(sourceString)
    }

}