package com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering

import android.view.View
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.EngineeringBottomBehaviorListener

class EngineeringSwiper(private var viewItem: View): EngineeringBottomBehaviorListener {

    override fun onStateEngNumpadChanged(bottomSheet: View, newState: Int) {
    }

    override fun onSlideEngNumpad(bottomSheet: View, slideOffset: Float) {
        viewItem.alpha = slideOffset
    }
}