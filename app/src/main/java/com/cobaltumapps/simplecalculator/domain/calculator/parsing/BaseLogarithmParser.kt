package com.cobaltumapps.simplecalculator.domain.calculator.parsing

import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseEvaluator
import com.cobaltumapps.simplecalculator.domain.calculator.enums.BaseCalculatorTrigonometryString
import com.cobaltumapps.simplecalculator.domain.calculator.parsing.interfaces.BaseExpressionParse
import kotlin.math.ln
import kotlin.math.log10

class BaseLogarithmParser : BaseExpressionParse {
    private val logSymbol = BaseCalculatorTrigonometryString.LOG.symbol
    private val lnSymbol = BaseCalculatorTrigonometryString.LN.symbol

    private val baseEvaluator = BaseEvaluator()

    override fun parse(sourceExpression: String): String {
        val logRegex = Regex("($logSymbol|$lnSymbol)\\(([^()]+)\\)")
        var output = sourceExpression

        logRegex.findAll(sourceExpression).forEach { match ->
            val functionName = match.groupValues[1]
            val rawArgument = match.groupValues[2]

            val argumentValue = evaluateArgument(rawArgument)
            val value = when (functionName) {
                logSymbol -> log10(argumentValue)
                lnSymbol -> ln(argumentValue)
                else -> throw IllegalArgumentException("Invalid log function: $functionName")
            }

            output = output.replace(match.value, "($value)")
        }

        return output
    }

    private fun evaluateArgument(argument: String): Double {
        return try {
            argument.toDouble()
        } catch (e: NumberFormatException) {
            baseEvaluator.evaluateExpression(argument).toDouble()
        }
    }
}
