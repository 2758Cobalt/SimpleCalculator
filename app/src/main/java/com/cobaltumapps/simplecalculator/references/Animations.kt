package com.cobaltumapps.simplecalculator.references

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat

object Animations {
    val overshootInterpolator = OvershootInterpolator()

    fun playAnimation(activity: Activity, animObject: View, animResource: Int) {
        animObject.startAnimation(AnimationUtils.loadAnimation(activity,animResource))
    }
    fun animatePropertyChange(view: View, property: String, startValue: Float, endValue: Float, duration: Long, interpolator: Interpolator?) {
        val animator = ObjectAnimator.ofFloat(view, property, startValue, endValue)
        animator.duration = duration
        interpolator?.let { animator.interpolator = it }
        animator.start()
    }

    fun animateTextColorChange(textView: TextView, startColorResId: Int, endColorResId: Int, duration: Long, interpolator: Interpolator? = null) {
        val startColor = ContextCompat.getColor(textView.context, startColorResId)
        val endColor = ContextCompat.getColor(textView.context, endColorResId)

        val animator = ObjectAnimator.ofObject(
            textView,
            "backgroundColor",
            ArgbEvaluator(),
            startColor,
            endColor
        )

        animator.duration = duration
        interpolator?.let { animator.interpolator = it }
        animator.start()
    }

}