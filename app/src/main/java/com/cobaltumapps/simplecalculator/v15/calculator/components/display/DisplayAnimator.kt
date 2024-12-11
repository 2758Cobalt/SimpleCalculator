package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import androidx.core.view.isVisible
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayNBinding
import com.cobaltumapps.simplecalculator.v15.services.AnimationsService
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayAnimations
import com.cobaltumapps.simplecalculator.v15.constants.Property

class DisplayAnimator : DisplayAnimations {
    private var displaybinding: FragmentDisplayNBinding? = null
    private var animationDuration = 200L

    fun setNewBinding(newBinding: FragmentDisplayNBinding) {
        displaybinding = newBinding
    }

    override fun playDisplayResultAnim(onEnd: (() -> Unit)?) {
        displaybinding?.displayResultField?.isVisible = true
        AnimationsService.animatePropertyChange(
            displaybinding?.displayResultField!!,
            Property.scaleY.name,
             displaybinding?.displayResultField?.scaleY!!,
            1f,
            animationDuration,
            AnimationsService.overshootInterpolator
        )
    }

    override fun playHiddenResultAnim(onEnd: (() -> Unit)?) {
        AnimationsService.animatePropertyChange(
            displaybinding?.displayResultField!!,
            Property.scaleY.name,
            displaybinding?.displayResultField?.scaleY!!,
            0f,
            animationDuration,
            AnimationsService.overshootInterpolator
        ) {
            displaybinding?.displayResultField?.isVisible = false
            onEnd?.invoke()
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