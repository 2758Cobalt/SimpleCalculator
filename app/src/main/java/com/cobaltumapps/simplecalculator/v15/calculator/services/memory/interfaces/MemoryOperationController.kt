package com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces

interface MemoryOperationController {
    fun addToMemory(value: Number, onSuccessful: ((result: Double) -> Unit?))
    fun subtractFromMemory(value: Number, onSuccessful: ((result: Double) -> Unit?))
    fun multiplyToMemory(value: Number, onSuccessful: ((result: Double) -> Unit?))
    fun divideAtMemory(value: Number, onSuccessful: ((result: Double) -> Unit?))
}