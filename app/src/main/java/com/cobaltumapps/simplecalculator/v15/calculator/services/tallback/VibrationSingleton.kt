package com.cobaltumapps.simplecalculator.v15.calculator.services.tallback

import android.os.Vibrator

object VibrationSingleton: VibrationServiceController {

    @Volatile
    private var instance: VibrationManager? = null

    fun getInstance(vibrator: Vibrator): VibrationManager {
        return instance ?: synchronized(this) {
            instance ?: VibrationManager(vibrator).also { instance = it }
        }
    }

    override fun playVibration() {
        instance?.playVibration()
    }
}