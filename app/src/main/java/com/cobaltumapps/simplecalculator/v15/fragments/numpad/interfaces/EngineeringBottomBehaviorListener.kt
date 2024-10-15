package com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces

import android.view.View

interface EngineeringBottomBehaviorListener {
    fun onStateEngNumpadChanged(bottomSheet: View, newState: Int)
    fun onSlideEngNumpad(bottomSheet: View, slideOffset: Float)
}