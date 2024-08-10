package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryCRUD


open class Memory: MemoryCRUD {
    private var memoryStorage = 0f
    private var memoryLogger = MemoryLogger()


    override fun save(value: Number) {
        memoryStorage = value.toFloat()
//        memoryLogger.save(value)
    }

    override fun read(): Float {
//        memoryLogger.read()
        return memoryStorage
    }

    override fun clear() {
        memoryStorage = 0f
//        memoryLogger.clear()
    }
}

