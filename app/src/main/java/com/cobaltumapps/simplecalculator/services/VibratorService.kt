package com.cobaltumapps.simplecalculator.services

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.cobaltumapps.simplecalculator.references.LogTags

class VibratorService {
    var context: Context? = null
    var isBlocked = false

    // Воспроизводит вибрацию
    fun playVibration(duration: Long = 100) {
        if (context != null){

            if (!isBlocked){
                // Получаем экземпляр Vibrator из системы
                val vibrator = context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

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
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(duration)
                }
            }
            else{
                Log.w(LogTags.LOG_VIBRATOR_SERVICE, "Vibrator is blocked")
            }

        }
    }
}