package com.cobaltumapps.simplecalculator.v15.converter

abstract class ConverterFormula: DefaultFormula {
    private var argumentX = 0.0
    override fun calculate(): Number {
        return 0.0
    }

    override fun setArgument(x: Number) {
        argumentX = x.toDouble()
    }
}

interface DefaultFormula {
    fun calculate(): Number
    fun setArgument(x: Number)
}