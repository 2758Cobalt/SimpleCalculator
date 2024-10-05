package com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces

interface MemoryController {
    fun saveMemoryValue(value: Number, onSuccessful: ((result: Boolean) -> Unit?))
    fun readMemory(): Double
    fun clearMemory()
}