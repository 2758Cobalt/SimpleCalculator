package com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering

import android.view.View
import androidx.core.view.isVisible
import com.cobaltumapps.simplecalculator.references.Animations

interface EngineeringAnimator {
    fun swapEnable(view: View, isEnabled: Boolean, duration: Long = 400L)
}
class EngineeringSwiper: EngineeringAnimator {
    var duration: Long = 400L

    override fun swapEnable(view: View, isEnabled: Boolean, duration: Long) {
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