package com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces

interface MemorySharedStorage {
    fun saveMemoryToStorage(keyName: String)
    fun updateMemoryInStorage(keyName: String)
    fun removeMemoryInStorage(keyName: String)
}