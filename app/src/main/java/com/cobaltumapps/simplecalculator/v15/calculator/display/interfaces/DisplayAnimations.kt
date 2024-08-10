package com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces

interface DisplayAnimations {
    fun playAnimationIsResult(isResult: Boolean)
    fun playClearAnimation(onEnd: (() -> Unit)?)
    fun playMemoryFieldAnimation(onEnd: (() -> Unit)?)
    fun playAngleFieldAnimation(onEnd: (() -> Unit)?)
}