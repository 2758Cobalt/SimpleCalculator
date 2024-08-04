package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryOperations


open class Memory: MemoryOperations {
    private var memoryStorage = 0.0

    override fun save(writtenValue: Number) {
        memoryStorage = writtenValue.toDouble()
    }

    override fun read(): Double {
        return memoryStorage
    }

    override fun clear() {
        memoryStorage = 0.0
    }
}
