package com.cobaltumapps.simplecalculator.domain.calculator.core.interfaces

interface Calculator<T : Number> {
    fun evaluate(): T
}