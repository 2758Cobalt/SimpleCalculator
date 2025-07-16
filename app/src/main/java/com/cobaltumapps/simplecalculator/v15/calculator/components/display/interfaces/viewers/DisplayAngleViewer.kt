package com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers

import com.cobaltumapps.simplecalculator.data.calculator.enums.AngleMode

interface DisplayAngleViewer {
    fun setAngleField(angleMode: AngleMode)
}