package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

object MemoryStorageControllerSingleton {

    @Volatile
    private var instance: MemoryControllerImpl? = null

    fun getInstance(): MemoryControllerImpl {
        return instance ?: synchronized(this) {
            instance ?: MemoryControllerImpl().also { instance = it }
        }
    }
}