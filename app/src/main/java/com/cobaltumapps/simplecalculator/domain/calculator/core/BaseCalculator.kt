package com.cobaltumapps.simplecalculator.domain.calculator.core

import android.util.Log
import com.cobaltumapps.simplecalculator.data.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.domain.calculator.core.interfaces.AngleSwitcher
import com.cobaltumapps.simplecalculator.domain.calculator.core.interfaces.Calculator
import com.cobaltumapps.simplecalculator.domain.calculator.parsing.BaseLogarithmParser
import com.cobaltumapps.simplecalculator.domain.calculator.parsing.BaseTrigonometryParser
import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression
import com.cobaltumapps.simplecalculator.v15.references.LogTags
import java.io.IOException
import java.math.BigDecimal

class BaseCalculator: Calculator<BigDecimal>, AngleSwitcher {
    private var userExpression: Expression = Expression()
    private var _angleMode: AngleMode = AngleMode.RAD

    override var angleMode: AngleMode
        get() = _angleMode
        set(value) {
            _angleMode = value
            baseTrigonometryParser.angleMode = value
        }

    var calculationResult: BigDecimal = BigDecimal("0.0")

    private val baseTrigonometryParser = BaseTrigonometryParser()
    private val baseLogarithmParser = BaseLogarithmParser()
    private val baseEvaluator = BaseEvaluator()

    override fun evaluate(): BigDecimal {
        return try {

            if (userExpression.getExpression().isNotEmpty()) {
                val resultParse = parseTrigonometry(userExpression.getExpression())
                val resultCalculation = baseEvaluator.evaluateExpression(resultParse)

                calculationResult = resultCalculation
                calculationResult
            }
            else calculationResult.also { BigDecimal("0.0") }

        } catch (anyEx: IOException) {
            Log.e(LogTags.LOG_CALCULATOR_CORE, "ERROR: Unhandled exception:\n${anyEx.localizedMessage}")
            BigDecimal("0.0")
        }
    }

    private fun parseTrigonometry(sourceExpression: String): String {
        val resultParseTrigonometry = baseTrigonometryParser.parse(sourceExpression)
        return baseLogarithmParser.parse(resultParseTrigonometry)
    }

    fun setNewExpression(expression: Expression) {
        this.userExpression = expression
    }

    fun closeExpressionString(input: String) : String{
        val stringBuilder = StringBuilder(input)
        stringBuilder.insert(0,"1${'/'}${'('}")
        stringBuilder.insert(stringBuilder.lastIndex + 1,"${')'}")
        return stringBuilder.toString()
    }

    companion object {
        fun<T> ArrayDeque<T>.push(item: T) {
            this.addFirst(item)
        }

        fun<T> ArrayDeque<T>.peek(): T {
            return this.first()
        }

        fun<T> ArrayDeque<T>.pop(): T {
            return this.removeFirst()
        }
    }
}