package com.cobaltumapps.simplecalculator.ui.fragments.calculator.keyboard.interfaces

import android.view.View

interface NumpadBottomBehaviorListener {
    fun onStateNumpadChanged(bottomSheet: View, newState: Int)
}