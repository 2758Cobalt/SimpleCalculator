package com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces

interface MemoryOperations {
    fun save(writtenValue: Number)
    fun read(): Number
    fun clear()
}