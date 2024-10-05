package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryCRUD

open class Memory: MemoryCRUD {
    private var memoryStorage: Double = 0.0

    override fun save(value: Number) {
        memoryStorage = value.toDouble()
    }

    override fun read(): Double {
        return memoryStorage
    }

    override fun clear() {
        memoryStorage = 0.0
    }
}

