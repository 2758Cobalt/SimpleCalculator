package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryOperations

open class MemoryLogger: MemoryOperations {
    private var memoryListener: MemoryOperations? = null

    fun setListener(newListener: MemoryOperations){
        memoryListener = newListener
    }

    override fun save(writtenValue: Number) {
        memoryListener.let {
            Log.d(LOG_TAG, "New value is added to storage. Storage: $writtenValue")
        }
    }

    override fun read(): Number {
        memoryListener.let {
            Log.d(LOG_TAG, "Storage was read")
        }
        return -1
    }

    override fun clear() {
        memoryListener.let {
            Log.d(LOG_TAG, "Storage is cleared")
        }
    }

    companion object{
        private const val LOG_TAG = "LogMemory"
    }
}

