package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import android.util.Log

interface MemoryController {
    fun saveMemoryValue(value: Number)
    fun readMemory(): Float
    fun clearMemory()
}

class MemoryControllerImpl: MemoryController {
    private val memory = Memory()
    private val memoryLogger = MemoryLogger()

    override fun saveMemoryValue(value: Number) {
        memory.save(value)
        memoryLogger.saveMemoryValue(value)
    }

    override fun readMemory(): Float {
        memoryLogger.readMemory()
        return memory.read()
    }

    override fun clearMemory() {
        memory.clear()
        memoryLogger.clearMemory()
    }
}

class MemoryLogger: MemoryController {
    override fun saveMemoryValue(value: Number) {
        Log.d(LOG_TAG, "The value: $value has been saved to the memory")
    }

    override fun readMemory(): Float {
        Log.d(LOG_TAG, "The memory has been read")
        return 0f
    }

    override fun clearMemory() {
        Log.d(LOG_TAG, "The memory has been cleared")
    }

    companion object {
        const val LOG_TAG = "SC_MemoryControllerTag"
    }

}