package com.cobaltumapps.simplecalculator.v15.calculator.services.tallback

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.cobaltumapps.simplecalculator.v15.calculator.components.settings.SettingsSingleton
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName

class VibrationManager(private val vibrator: Vibrator): VibrationServiceController {
    private val vibrationManagerLogger = VibrationManagerLogger()

    override fun playVibration() {
        val condition = SettingsSingleton.getPreferenceCondition(OptionName.AllowVibration.name, true)
        if (condition) {
            vibrationManagerLogger.playVibration()
            vibrate()
        }
    }

    private fun vibrate() {
        // For Android API 26 (Oreo) and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        }
        // For Android API older 26 (Oreo)
        else
            vibrator.vibrate(VIBRATION_DURATION)
    }

    companion object {
        const val VIBRATION_DURATION = 10L
    }
}

