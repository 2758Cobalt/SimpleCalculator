package com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering

import android.view.View
import android.widget.Button
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.EngineeringBottomBehaviorListener
import com.google.android.material.bottomsheet.BottomSheetBehavior

class EngineeringSwiper(private var viewButtonItem: Button): EngineeringBottomBehaviorListener {

    // Control of button target
    override fun onStateEngNumpadChanged(bottomSheet: View, newState: Int) {
        when(newState) {
            BottomSheetBehavior.STATE_COLLAPSED -> viewButtonItem.isEnabled = false
            else -> viewButtonItem.isEnabled = true
        }
    }

    override fun onSlideEngNumpad(bottomSheet: View, slideOffset: Float) {
        viewButtonItem.alpha = slideOffset
    }
}