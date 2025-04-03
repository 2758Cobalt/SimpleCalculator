package com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces

interface MemoryController {
    fun saveMemoryValue(value: Number, onSuccessful: ((result: Double) -> Unit?)? = null)
    fun readMemory(): Double
    fun clearMemory(onSuccessful: ((result: Double) -> Unit?)? = null)
}