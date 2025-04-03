package com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces

interface MemoryOperationController {
    fun addToMemory(value: Number, onSuccessful: ((result: Double) -> Unit?)? = null)
    fun subtractFromMemory(value: Number, onSuccessful: ((result: Double) -> Unit?)? = null)
    fun multiplyToMemory(value: Number, onSuccessful: ((result: Double) -> Unit?)? = null)
    fun divideAtMemory(value: Number, onSuccessful: ((result: Double) -> Unit?)? = null)
}