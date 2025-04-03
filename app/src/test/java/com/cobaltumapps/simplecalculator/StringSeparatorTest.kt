package com.cobaltumapps.simplecalculator

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.formatter.DisplayFormatter
import org.junit.Assert.assertEquals
import org.junit.Test

class StringSeparatorTest {
    private val displayFormatter = DisplayFormatter()

    @Test
    fun formatString_test() {
        val formattedStringTest1 = displayFormatter.formatResult("600600")
        val formattedStringTest2 = displayFormatter.formatResult("6006007")
        val formattedStringTest3 = displayFormatter.formatResult("60060078")
        val formattedStringTest4 = displayFormatter.formatResult("600600.123")
        val formattedStringTest5 = displayFormatter.formatResult("600630")
        val formattedStringTest6 = displayFormatter.formatResult("6.00600")

        assertEquals("600 600", formattedStringTest1)
        assertEquals("6 600 007", formattedStringTest2)
        assertEquals("60 060 078", formattedStringTest3)
        assertEquals("600 600.123", formattedStringTest4)
        assertEquals("600 630", formattedStringTest5)
        assertEquals("6.00600", formattedStringTest6)

    }
}