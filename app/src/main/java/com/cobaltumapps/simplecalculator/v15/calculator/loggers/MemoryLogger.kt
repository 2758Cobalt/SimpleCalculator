package com.cobaltumapps.simplecalculator.v15.calculator.loggers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryCRUD

open class MemoryLogger: MemoryCRUD {
    private var memoryListener: MemoryCRUD? = null

    fun setListener(newListener: MemoryCRUD){
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

