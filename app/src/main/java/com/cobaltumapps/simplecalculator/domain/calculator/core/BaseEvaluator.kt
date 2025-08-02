package com.cobaltumapps.simplecalculator.domain.calculator.core

import android.util.Log
import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator.Companion.peek
import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator.Companion.pop
import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator.Companion.push
import com.cobaltumapps.simplecalculator.domain.calculator.parsing.BaseCalculatorParser
import com.cobaltumapps.simplecalculator.domain.calculator.rules.ImplicitMultiplicationAfterCloseBracketRule
import com.cobaltumapps.simplecalculator.domain.calculator.rules.ImplicitMultiplicationBeforeOpenBracketRule
import com.cobaltumapps.simplecalculator.domain.calculator.rules.UnaryMinusRule
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolAdd
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolCloseBracket
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolDiv
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolExponent
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolFactorial
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolMul
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolOpenBracket
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolPI
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolPercent
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolPoint
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolPower
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolSqrt
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator.symbolSub
import com.cobaltumapps.simplecalculator.v15.references.LogTags
import java.math.BigDecimal
import java.util.EmptyStackException

class BaseEvaluator {
    private val baseCalculatorParser = BaseCalculatorParser()

    // Rules
    private val unaryMinusRule = UnaryMinusRule()
    private val mulBeforeOpenBracketRule = ImplicitMultiplicationBeforeOpenBracketRule()
    private val mulAfterCloseBracketRule = ImplicitMultiplicationAfterCloseBracketRule()

    fun evaluateExpression(sourceExpression: String): BigDecimal {
        val stackOperands = ArrayDeque<BigDecimal>()
        val stackOperators = ArrayDeque<Char>()

        val currentNumber = StringBuilder()

        try {
            sourceExpression.forEachIndexed { index, symbol ->
                when {
                    symbol.isDigit() || symbol == symbolPoint -> currentNumber.append(symbol)

                    symbol == symbolOpenBracket -> {
                        mulBeforeOpenBracketRule.apply(index, sourceExpression, currentNumber, stackOperands, stackOperators)

                        stackOperators.push(symbol)

                        // Проверка на унарный минус после открывающей скобки
                        unaryMinusRule.apply(index, sourceExpression, currentNumber, stackOperands, stackOperators)
                    }

                    symbol == symbolCloseBracket -> {

                        // Добавляет оператор умножение после закрывающейся скобкой при его отсутствии
                        mulAfterCloseBracketRule.apply(index, sourceExpression, currentNumber, stackOperands, stackOperators)

                        currentNumber.pushIfNotEmpty(stackOperands)

                        // Перебирает операторы до открывающей скобки
                        while (stackOperators.isNotEmpty() && stackOperators.peek() != symbolOpenBracket) {
                            baseCalculatorParser.parseExpression(stackOperands, stackOperators)
                        }

                        if (stackOperators.isNotEmpty() && stackOperators.peek() == symbolCloseBracket) {
                            stackOperators.pop()
                        }
                    }

                    symbol == symbolPI -> {
                        stackOperands.push(BigDecimal.valueOf(Math.PI))
                    }

                    symbol == symbolExponent -> {
                        stackOperands.push(BigDecimal.valueOf(Math.E))
                    }

                    else -> {
                        currentNumber.pushIfNotEmpty(stackOperands)
                        while (stackOperators.isNotEmpty()
                            && getPrecedence(symbol)
                            <= getPrecedence(stackOperators.peek())) {
                            baseCalculatorParser.parseExpression(stackOperands, stackOperators)
                        }
                        stackOperators.push(symbol)
                    }

                }
            }

            currentNumber.pushIfNotEmpty(stackOperands)

            while (stackOperators.isNotEmpty()) {
                baseCalculatorParser.parseExpression(stackOperands, stackOperators)
            }

            return try {
                stackOperands.peek()
            } catch (ex: EmptyStackException) {
                Log.e(LogTags.LOG_CALCULATOR_CORE, "EmptyStackException: ${javaClass.simpleName}\nERROR:${ex.localizedMessage}")
                BigDecimal("0.0")
            }

        } catch (ex: EmptyStackException) {
            Log.e(LogTags.LOG_CALCULATOR_CORE, "EmptyStackException: ${javaClass.simpleName}\nERROR:${ex.localizedMessage}")
            return BigDecimal("0.0")
        }

    }

    /** Function of changing precedence */
    private fun getPrecedence(operator: Char): Int {
        return when (operator) {
            symbolAdd, symbolSub -> 1
            symbolMul, symbolFactorial, symbolDiv, symbolSqrt -> 2
            symbolPower -> 3
            symbolPercent -> 4
            else -> 0
        }
    }

    companion object {
        fun StringBuilder.pushIfNotEmpty(stack: ArrayDeque<BigDecimal>) {
            if (this.isNotEmpty()) {
                stack.push(BigDecimal(this.toString()))
                this.clear()
            }
        }
    }
}