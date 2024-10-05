package com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayAnimator
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayController

interface DisplayComponentProperties {
    fun setNewDisplayController(controller: DisplayController)
    fun setNewDisplayAnimator(animator: DisplayAnimator)
}