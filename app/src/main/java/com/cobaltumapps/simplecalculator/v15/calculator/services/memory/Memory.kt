package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryCRUD


open class Memory: MemoryCRUD {
    private var memoryStorage = 0f

    override fun save(value: Number) {
        memoryStorage = value.toFloat()
    }

    override fun read(): Float {
        return memoryStorage
    }

    override fun clear() {
        memoryStorage = 0f
    }
}

