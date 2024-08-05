package com.cobaltumapps.simplecalculator.references

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator

object Animations {
    val overshootInterpolator = OvershootInterpolator()

    fun playAnimation(activity: Activity, animObject: View, animResource: Int, interpolator: Interpolator? = null) {
        val animation = AnimationUtils.loadAnimation(activity, animResource)
        interpolator?.let { animation.interpolator = it }
        animObject.startAnimation(animation)
    }

    fun animatePropertyChange(
        view: View,
        property: String,
        startValue: Float,
        endValue: Float,
        duration: Long,
        interpolator: Interpolator? = null,
        onEnd: (() -> Unit)? = null
    ) {
        val animator = ObjectAnimator.ofFloat(view, property, startValue, endValue)
        animator.duration = duration
        interpolator?.let { animator.interpolator = it }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                onEnd?.invoke()
            }
        })

        animator.start()
    }

}