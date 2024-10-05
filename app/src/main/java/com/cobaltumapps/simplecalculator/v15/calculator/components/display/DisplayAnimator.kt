package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import androidx.core.view.isVisible
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayNBinding
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayAnimations
import com.cobaltumapps.simplecalculator.v15.constants.Property

class DisplayAnimator : DisplayAnimations {
    private var displaybinding: FragmentDisplayNBinding? = null
    private var animationDuration = 200L

    fun setNewBinding(newBinding: FragmentDisplayNBinding) {
        displaybinding = newBinding
    }

    override fun playDisplayResultAnim() {
        displaybinding?.displayResultField?.isVisible = true
        Animations.animatePropertyChange(
            displaybinding?.displayResultField!!,
            Property.scaleY.name,
             displaybinding?.displayResultField?.scaleY!!,
            1f,
            animationDuration,
            Animations.overshootInterpolator
        )
    }

    override fun playHiddenResultAnim() {
        Animations.animatePropertyChange(
            displaybinding?.displayResultField!!,
            Property.scaleY.name,
            displaybinding?.displayResultField?.scaleY!!,
            0f,
            animationDuration,
            Animations.overshootInterpolator
        ) {
            displaybinding?.displayResultField?.isVisible = false
        }
    }

    override fun playClearAnimation(onEnd: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun playMemoryFieldAnimation(onEnd: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun playAngleFieldAnimation(onEnd: (() -> Unit)?) {
        TODO("Not yet implemented")
    }
}