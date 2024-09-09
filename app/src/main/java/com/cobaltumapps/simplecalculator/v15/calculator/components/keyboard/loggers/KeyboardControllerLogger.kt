package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.loggers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardInstanceGetter
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master.KeyboardMaster

class KeyboardControllerLogger: KeyboardInstanceGetter {

    override fun getInstance(instance: KeyboardMaster) {
        Log.d(TAG, "${javaClass.simpleName}: Назначен новый экземпляр клавиатуры!\n$instance")
    }

    companion object {
        const val TAG = "KeyboardControllerLoggerTag"
    }
}