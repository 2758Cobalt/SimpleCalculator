package com.cobaltumapps.simplecalculator.v15.calculator.display

import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayNBinding
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces.DisplayAnimations
import com.cobaltumapps.simplecalculator.v15.constants.Properties

class DisplayAnimator(private val binding: FragmentDisplayNBinding) : DisplayAnimations {

    override fun playAnimationIsResult(isResult: Boolean) {
        val animationDuration = 300L
        Animations.animatePropertyChange(
            binding.displayResultField,
            Properties.scaleY,
            if (isResult) 0f else 1f,
            if (isResult) 1f else 0f,
            animationDuration,
            Animations.overshootInterpolator
        )
    }



    override fun playClearAnimation(onEnd: (() -> Unit)?) {
        val animationDuration = 200L
        Animations.animatePropertyChange(
            binding.displayExpressionField,
            Properties.alpha,
            binding.displayExpressionField.alpha,
            0f,
            animationDuration,
            Animations.overshootInterpolator
        ) {
            onEnd?.invoke()
            Animations.animatePropertyChange(
                binding.displayExpressionField,
                Properties.alpha,
                binding.displayExpressionField.alpha,
                1f,
                animationDuration,
                Animations.overshootInterpolator
            )
        }
    }

    override fun playMemoryFieldAnimation(onEnd: (() -> Unit)?) {
    }

    override fun playAngleFieldAnimation(onEnd: (() -> Unit)?) {
    }
}