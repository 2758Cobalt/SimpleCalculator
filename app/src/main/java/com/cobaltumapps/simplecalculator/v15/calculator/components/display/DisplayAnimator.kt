package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayNBinding
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayAnimations
import com.cobaltumapps.simplecalculator.v15.constants.Properties

class DisplayAnimator : DisplayAnimations {
    private var displaybinding: FragmentDisplayNBinding? = null
    private var animationDuration = 200L

    fun setNewBinding(newBinding: FragmentDisplayNBinding) {
        displaybinding = newBinding
    }

    override fun playAnimationIsResult(isReversed: Boolean) {
        Animations.animatePropertyChange(
            displaybinding?.displayResultField!!,
            Properties.scaleY,
            if (isReversed) 1f else 0f,
            if (isReversed) 0f else 1f,
            animationDuration,
            Animations.overshootInterpolator
        )
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