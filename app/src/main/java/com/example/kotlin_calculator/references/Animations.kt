package com.example.kotlin_calculator.references

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils

object Animations {
    fun playAnimation(activity: Activity, animObject: View, animResource: Int) {
        animObject.startAnimation(AnimationUtils.loadAnimation(activity,animResource))
    }
}