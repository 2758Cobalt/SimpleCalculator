package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.logger.MemoryLogger

class MemoryControllerImpl: MemoryController {
    private val memory = Memory()
    private val memoryLogger = MemoryLogger()

    override fun saveMemoryValue(value: Number, onSuccessful: ((result: Boolean) -> Unit?)) {
        memory.save(value)
        memoryLogger.saveMemoryValue(value, onSuccessful)
        onSuccessful.invoke(true)
    }

    override fun readMemory(): Double {
        memoryLogger.readMemory()
        return memory.read()
    }

    override fun clearMemory() {
        memory.clear()
        memoryLogger.clearMemory()
    }
}

