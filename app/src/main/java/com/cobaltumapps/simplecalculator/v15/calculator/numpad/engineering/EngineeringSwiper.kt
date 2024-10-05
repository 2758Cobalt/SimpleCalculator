package com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering

import android.view.View
import androidx.core.view.isVisible
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.v15.constants.Property
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.EngineeringBottomBehaviorListener
import com.google.android.material.bottomsheet.BottomSheetBehavior

class EngineeringSwiper(
    private var viewItem: View): EngineeringBottomBehaviorListener
{
    private val duration: Long = 400L

    override fun onStateEngNumpadChanged(bottomSheet: View, newState: Int) {
        when(newState) {
            BottomSheetBehavior.STATE_EXPANDED -> {
                viewItem.isVisible = true
                Animations.animatePropertyChange(viewItem, Property.alpha.name, viewItem.alpha, 1f, duration)
            }

            BottomSheetBehavior.STATE_COLLAPSED -> {
                Animations.animatePropertyChange(viewItem, Property.alpha.name, viewItem.alpha, 0f, duration) {
                    viewItem.isVisible = false
                }
            }
        }
    }

}