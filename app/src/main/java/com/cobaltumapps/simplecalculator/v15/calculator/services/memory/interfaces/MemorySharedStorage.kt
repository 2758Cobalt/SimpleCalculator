package com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces

interface MemorySharedStorage {
    fun saveMemoryToStorage()
    fun loadMemoryToStorage()
    fun updateMemoryInStorage()
    fun removeMemoryInStorage()
}