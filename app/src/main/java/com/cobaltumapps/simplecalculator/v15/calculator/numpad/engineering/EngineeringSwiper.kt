package com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering

import android.view.View
import androidx.core.view.isVisible
import com.cobaltumapps.simplecalculator.references.Animations

class EngineeringSwiper {
    fun engineeringIsEnable(view: View, isEnabled: Boolean, duration: Long = 400L) {
        if (isEnabled){
            view.isVisible = true
            Animations.animatePropertyChange(view, "alpha", view.alpha, 1f, duration)
        }
        else
            Animations.animatePropertyChange(view, "alpha", view.alpha, 0f, duration) {
                view.isVisible = false
            }
    }
}