package com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayAnimator
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayControllerImpl

interface DisplayComponentProperties {
    fun setNewDisplayController(controller: DisplayControllerImpl)
    fun setNewDisplayAnimator(animator: DisplayAnimator)
}