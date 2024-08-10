package com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces


interface MemoryCRUD {
    fun save(value: Number)
    fun read(): Number
    fun clear()
}