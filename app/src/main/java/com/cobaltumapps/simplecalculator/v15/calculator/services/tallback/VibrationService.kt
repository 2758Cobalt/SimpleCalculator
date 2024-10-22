package com.cobaltumapps.simplecalculator.v15.calculator.services.tallback

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.cobaltumapps.simplecalculator.references.LogTags
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesUpdaterListener

class VibrationService(
    private val context: Context
): VibrationServiceController, PreferencesUpdaterListener {
    var isEnalbed = true
    var duration = 25L


    // Воспроизводит вибрацию
    override fun playVibration() {
        if (isEnalbed){
            // Получаем экземпляр Vibrator из системы
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            // Проверяем, поддерживается ли вибрация
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Для Android Oreo и выше
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val vibrationEffect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
                    vibrator.vibrate(vibrationEffect)
                } else {
                    // Для версий между Oreo и Q
                    vibrator.vibrate(duration)
                }
            } else {
                // Для старых версий Android
                vibrator.vibrate(duration)
            }
        }
        else{
            Log.w(LogTags.LOG_VIBRATOR_SERVICE, "VibrationService is blocked")
        }
    }

    /** Обновляет настройку с силой вибрации */
    override fun updatePreferences(newPrefConfig: PreferencesUserData) {
        duration = newPrefConfig.vibrationStrength
    }
}

