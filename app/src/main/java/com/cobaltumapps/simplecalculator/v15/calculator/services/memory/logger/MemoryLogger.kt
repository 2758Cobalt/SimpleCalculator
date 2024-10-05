package com.cobaltumapps.simplecalculator.v15.calculator.services.memory.logger

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryController

class MemoryLogger: MemoryController {
    override fun saveMemoryValue(value: Number, onSuccessful: ((result: Boolean) -> Unit?)) {
        Log.d(LOG_TAG, "The value: $value has been saved to the memory")
    }

    override fun readMemory(): Double {
        Log.d(LOG_TAG, "The memory has been read")
        return 0.0
    }

    override fun clearMemory() {
        Log.d(LOG_TAG, "The memory has been cleared")
    }

    companion object {
        const val LOG_TAG = "SC_MemoryControllerTag"
    }

}