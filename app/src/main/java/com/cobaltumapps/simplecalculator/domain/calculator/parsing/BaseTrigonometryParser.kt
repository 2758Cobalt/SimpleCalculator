package com.cobaltumapps.simplecalculator.domain.calculator.parsing

import android.util.Log
import com.cobaltumapps.simplecalculator.data.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseEvaluator
import com.cobaltumapps.simplecalculator.domain.calculator.core.interfaces.AngleSwitcher
import com.cobaltumapps.simplecalculator.domain.calculator.enums.BaseCalculatorTrigonometryString
import com.cobaltumapps.simplecalculator.domain.calculator.parsing.interfaces.BaseExpressionParse
import com.cobaltumapps.simplecalculator.v15.references.LogTags
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class BaseTrigonometryParser : BaseExpressionParse, AngleSwitcher {
    private val sinSymbol = BaseCalculatorTrigonometryString.SINE.symbol
    private val cosSymbol = BaseCalculatorTrigonometryString.COSINE.symbol
    private val tanSymbol = BaseCalculatorTrigonometryString.TANGENT.symbol
    private val cotSymbol = BaseCalculatorTrigonometryString.COTANGENT.symbol

    private val baseEvaluator = BaseEvaluator()

    override var angleMode: AngleMode = AngleMode.RAD

    override fun parse(sourceExpression: String): String {
        val regex = Regex("($sinSymbol|$cosSymbol|$tanSymbol|$cotSymbol)\\(([^()]+)\\)")
        var output = sourceExpression

        regex.findAll(sourceExpression).forEach { match ->
            val functionName = match.groupValues[1]
            val rawArgument = match.groupValues[2]

            try {
                val argumentValue = evaluateArgument(rawArgument)
                val value = calculateOfAngleMode(argumentValue, functionName)
                output = output.replace(match.value, "($value)")
            } catch (ex: Exception) {
                output = "0"
                Log.e(LogTags.LOG_CALCULATOR_CORE, "Trigonometry parse error: ${ex.message}\nCause: ${ex.cause}")
            }
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

    private fun calculateOfAngleMode(argument: Double, functionName: String): Double {
        val arg = if (angleMode == AngleMode.RAD) argument else Math.toRadians(argument)
        return when (functionName) {
            sinSymbol -> sin(arg)
            cosSymbol -> cos(arg)
            tanSymbol -> tan(arg)
            cotSymbol -> 1.0 / tan(arg)
            else -> {
                Log.e(LogTags.LOG_CALCULATOR_CORE, "Invalid trigonometry function: $functionName")
                0.0
            }
        }
    }
}
