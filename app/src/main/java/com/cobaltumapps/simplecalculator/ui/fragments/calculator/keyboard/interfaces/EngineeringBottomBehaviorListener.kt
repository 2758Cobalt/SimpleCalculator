package com.cobaltumapps.simplecalculator.ui.fragments.calculator.keyboard.interfaces

import android.view.View

interface EngineeringBottomBehaviorListener {
    fun onStateEngNumpadChanged(bottomSheet: View, newState: Int)
    fun onSlideEngNumpad(bottomSheet: View, slideOffset: Float)
}