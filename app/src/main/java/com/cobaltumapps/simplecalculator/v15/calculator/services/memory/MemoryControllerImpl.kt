package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryOperationController
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.logger.MemoryLogger

class MemoryControllerImpl: MemoryController, MemoryOperationController {
    private val memoryStorageManager = MemoryStorageManager()
    private val memoryLogger = MemoryLogger()

    override fun saveMemoryValue(value: Number, onSuccessful: ((result: Double) -> Unit?)) {
        memoryStorageManager.save(value)
        memoryLogger.saveMemoryValue(value, onSuccessful)
        onSuccessful.invoke(memoryStorageManager.read())
    }

    override fun readMemory(): Double {
        memoryLogger.readMemory()
        return memoryStorageManager.read()
    }

    override fun clearMemory(onSuccessful: ((result: Double) -> Unit?)) {
        memoryStorageManager.clear()
        memoryLogger.clearMemory(onSuccessful)
        onSuccessful.invoke(memoryStorageManager.read())
    }

    override fun addToMemory(value: Number, onSuccessful: (result: Double) -> Unit?) {
        memoryStorageManager.save(memoryStorageManager.read() + value.toDouble())
        onSuccessful.invoke(memoryStorageManager.read())
    }

    override fun subtractFromMemory(value: Number, onSuccessful: (result: Double) -> Unit?) {
        memoryStorageManager.save(memoryStorageManager.read() - value.toDouble())
        onSuccessful.invoke(memoryStorageManager.read())
    }

    override fun multiplyToMemory(value: Number, onSuccessful: (result: Double) -> Unit?) {
        memoryStorageManager.save(memoryStorageManager.read() * value.toDouble())
        onSuccessful.invoke(memoryStorageManager.read())
    }

    override fun divideAtMemory(value: Number, onSuccessful: (result: Double) -> Unit?) {
        memoryStorageManager.save(memoryStorageManager.read() / value.toDouble())
        onSuccessful.invoke(memoryStorageManager.read())
    }
}

