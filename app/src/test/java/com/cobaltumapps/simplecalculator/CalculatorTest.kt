package com.cobaltumapps.simplecalculator

import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.BracketCloser
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorTest {
    private val bracketCloser = BracketCloser()

    @Test
    fun bracketCloser_test() {
        val expression = "5+2-3("
        val result = bracketCloser.closeBracketExpression(expression)
        assertEquals("5+2-3()", result)
    }
}