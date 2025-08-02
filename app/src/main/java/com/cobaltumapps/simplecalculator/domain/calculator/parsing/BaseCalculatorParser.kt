package com.cobaltumapps.simplecalculator.domain.calculator.parsing

import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator.Companion.peek
import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator.Companion.pop
import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator.Companion.push
import com.cobaltumapps.simplecalculator.domain.calculator.enums.BaseCalculatorSymbol
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.pow
import kotlin.math.sqrt

class BaseCalculatorParser {
    private val mathContext = MathContext.DECIMAL128

    val symbolAdd = BaseCalculatorSymbol.ADD.symbol
    val symbolSub = BaseCalculatorSymbol.SUBTRACT.symbol
    val symbolMul = BaseCalculatorSymbol.MULTIPLY.symbol
    val symbolDiv = BaseCalculatorSymbol.DIVIDE.symbol

    val symbolOpenBracket = BaseCalculatorSymbol.OPEN_BRACKET.symbol
    val symbolCloseBracket = BaseCalculatorSymbol.CLOSE_BRACKET.symbol
    val symbolPoint = BaseCalculatorSymbol.POINT.symbol

    val symbolFactorial = BaseCalculatorSymbol.FACTORIAL.symbol
    val symbolSqrt = BaseCalculatorSymbol.SQRT.symbol
    val symbolPower = BaseCalculatorSymbol.POWER.symbol
    val symbolPercent = BaseCalculatorSymbol.PERCENT.symbol

    fun parseExpression(stackOperand: ArrayDeque<BigDecimal>, stackOperator: ArrayDeque<Char>) {

        if (stackOperator.isNotEmpty() && stackOperand.isNotEmpty()) {
            when (stackOperator.peek()) {

                symbolAdd -> {
                    val result = if (stackOperand.size > 1) {
                        val a = stackOperand.pop()
                        val b = stackOperand.pop()
                        b.add(a, mathContext)
                    } else {
                        stackOperand.pop()
                    }
                    stackOperand.push(result)
                }

                symbolSub -> {
                    val result = if (stackOperand.size > 1) {
                        val a = stackOperand.pop()
                        val b = stackOperand.pop()
                        b.subtract(a, mathContext)
                    } else {
                        val a = stackOperand.pop()
                        BigDecimal.ZERO.subtract(a, mathContext)
                    }
                    stackOperand.push(result)
                }

                symbolMul -> {
                    val result = if (stackOperand.size > 1) {
                        val a = stackOperand.pop()
                        val b = stackOperand.pop()
                        a.multiply(b, mathContext)
                    } else {
                        val a = stackOperand.pop()
                        a.multiply(BigDecimal.ONE, mathContext)
                    }
                    stackOperand.push(result)
                }

                symbolDiv -> {
                    val result = if (stackOperand.size > 1) {
                        val a = stackOperand.pop()
                        val b = stackOperand.pop()
                        b.divide(a, mathContext)
                    } else {
                        val a = stackOperand.pop()
                        BigDecimal.ONE.divide(a, mathContext)
                    }
                    stackOperand.push(result)
                }

                symbolPower -> {
                    val a = stackOperand.pop()
                    val b = stackOperand.pop()
                    val result = try {
                        b.pow(a.toInt(), mathContext)
                    } catch (e: Exception) {
                        BigDecimal(b.toDouble().pow(a.toDouble()), mathContext)
                    }
                    stackOperand.push(result)
                }

                symbolSqrt -> {
                    val a = stackOperand.pop()
                    val sqrt = BigDecimal(sqrt(a.toDouble()), mathContext)
                    val result = if (stackOperand.isNotEmpty()) {
                        val b = stackOperand.pop()
                        if (b != BigDecimal.ZERO) b.multiply(sqrt, mathContext) else sqrt
                    } else {
                        sqrt
                    }
                    stackOperand.push(result)
                }

                symbolPercent -> {
                    val a = stackOperand.pop()
                    val result = a.divide(BigDecimal(100), mathContext)
                    stackOperand.push(result)
                }

                symbolFactorial -> {
                    val a = stackOperand.pop()
                    val result = factorial(a.toBigInteger().toInt())
                    stackOperand.push(result)
                }
            }
            stackOperator.pop()
        } else {
            stackOperand.push(BigDecimal.ZERO)
        }
    }

    private fun factorial(n: Int): BigDecimal {
        require(n >= 0) { "The factorial is defined only for non -negative numbers" }
        var result = BigDecimal.ONE
        for (i in 2..n) {
            result = result.multiply(BigDecimal(i))
        }
        return result
    }
}