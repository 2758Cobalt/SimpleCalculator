package com.cobaltumapps.simplecalculator.v15.calculator.services.tallback

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.PreferencesManager
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName

class VibrationService(
    private val context: Context
): VibrationServiceController {
    var preferencesManager: PreferencesManager? = null

    // Воспроизводит вибрацию
    override fun playVibration(ignorePreference: Boolean) {
        if (!ignorePreference) {
            preferencesManager?.getPreference(OptionName.AllowVibration) { condition ->
                if (condition) {
                    vibrate()
                }
            }
        }
        else
            vibrate()
    }

    private fun vibrate() {
        // Получаем экземпляр Vibrator из системы
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Проверяем, поддерживается ли вибрация
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Для Android Oreo и выше
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val vibrationEffect = VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE)
                vibrator.vibrate(vibrationEffect)
            } else {
                // Для версий между Oreo и Q
                vibrator.vibrate(VIBRATION_DURATION)
            }
        } else {
            // Для старых версий Android
            vibrator.vibrate(VIBRATION_DURATION)
        }
    }

    companion object {
        const val VIBRATION_DURATION = 10L
    }
}
