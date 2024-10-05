package com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces

import android.view.View

interface NumpadBottomBehaviorListener {
    fun onStateNumpadChanged(bottomSheet: View, newState: Int)
}