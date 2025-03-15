package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import android.util.Log
import androidx.core.view.isVisible
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.animation.DisplayAnimationCleaning
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.animation.DisplayAnimationResult
import com.cobaltumapps.simplecalculator.v15.constants.Property
import com.cobaltumapps.simplecalculator.v15.references.LogTags
import com.cobaltumapps.simplecalculator.v15.services.AnimationsService

class DisplayAnimator : DisplayAnimationResult, DisplayAnimationCleaning {
    private lateinit var binding: FragmentDisplayBinding

    fun setNewBinding(newBinding: FragmentDisplayBinding) {
        binding = newBinding
    }

    override fun playDisplayResultAnim(onEnd: (() -> Unit)?) {
        if (this::binding.isInitialized) {
            binding.displayResultField.isVisible = true
            AnimationsService.animatePropertyChange(
                binding.displayResultField,
                Property.scaleY.name,
                 binding.displayResultField.scaleY,
                1f,
                ANIM_DURATION,
                AnimationsService.overshootInterpolator
            )
        }
        else
            Log.e(LogTags.LOG_DISPLAY_ANIMATOR, "Called method playDisplayResultAnim.\nDisplay binding is not initialized !!!")
    }

    override fun playHiddenResultAnim(onEnd: (() -> Unit)?) {
        if (this::binding.isInitialized) {
            AnimationsService.animatePropertyChange(
                binding.displayResultField,
                Property.scaleY.name,
                binding.displayResultField.scaleY,
                0f,
                ANIM_DURATION,
                AnimationsService.overshootInterpolator
            ) {
                binding.displayResultField.isVisible = false
                onEnd?.invoke()
            }
        }
        else
            Log.e(LogTags.LOG_DISPLAY_ANIMATOR, "Called method playHiddenResultAnim.\nDisplay binding is not initialized !!!")
    }

    override fun playClearAnimation(onEnd: (() -> Unit)?) {
        if (this::binding.isInitialized) {
            AnimationsService.animatePropertyChange(
                binding.displayResultField,
                Property.scaleY.name,
                binding.displayResultField.scaleY,
                0f,
                ANIM_DURATION,
                AnimationsService.overshootInterpolator
            ) {
                binding.displayResultField.isVisible = false
                onEnd?.invoke()
            }
        }
        else
            Log.e(LogTags.LOG_DISPLAY_ANIMATOR, "Called method playClearAnimation.\nDisplay binding is not initialized !!!")
    }

    companion object {
        const val ANIM_DURATION = 200L
    }
}