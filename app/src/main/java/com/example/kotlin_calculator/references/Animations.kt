package com.example.kotlin_calculator.references

import android.animation.ObjectAnimator
import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator

object Animations {
    fun playAnimation(activity: Activity, animObject: View, animResource: Int) {
        animObject.startAnimation(AnimationUtils.loadAnimation(activity,animResource))
    }
    fun animatePropertyChange(view: View, property: String, startValue: Float, endValue: Float, duration: Long, interpolator: Interpolator?) {
        val animator = ObjectAnimator.ofFloat(view, property, startValue, endValue)
        animator.duration = duration
        interpolator?.let { animator.interpolator = it }
        animator.start()
    }

}