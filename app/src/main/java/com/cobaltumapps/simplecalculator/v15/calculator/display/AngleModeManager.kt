package com.cobaltumapps.simplecalculator.v15.calculator.display

class AngleModeManager {
    // Класс, отвечающий за смену режима перевода углов
    private enum class RadAngleMode {
        DEG, RAD
    }

    fun setRadAngleMode(isEnabled: Boolean): String {
        return when(isEnabled){
            true -> RadAngleMode.RAD.toString()
            false -> RadAngleMode.DEG.toString()
        }
    }
}