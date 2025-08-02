package com.cobaltumapps.simplecalculator.domain.calculator.managers

import com.cobaltumapps.simplecalculator.data.calculator.enums.AngleMode

class AngleModeManager {
    private var mode = AngleMode.RAD

    fun switchMode(onSwitched: ((angleMode: AngleMode) -> Unit)? = null) {
        val newMode = when(mode) {
            AngleMode.RAD -> AngleMode.DEG
            AngleMode.DEG -> AngleMode.RAD
        }
        onSwitched?.invoke(newMode)
    }

    fun switchMode(angleMode: AngleMode, onSwitched: ((angleMode: AngleMode) -> Unit)? = null) {
        mode = angleMode
        onSwitched?.invoke(mode)
    }

    fun getAngleMode(): AngleMode = mode
}