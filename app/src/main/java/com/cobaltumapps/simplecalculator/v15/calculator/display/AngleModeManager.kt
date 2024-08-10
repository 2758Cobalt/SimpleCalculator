package com.cobaltumapps.simplecalculator.v15.calculator.display

import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

// Класс, отвечающий за смену режима перевода углов
class AngleModeManager {

    fun setRadAngleMode(angleMode: AngleMode): String {
        return angleMode.name
    }
}