package com.cobaltumapps.simplecalculator.references

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

object Vibration {

    fun playVibro(context: Context, duration: Long = 100) {
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
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }

}