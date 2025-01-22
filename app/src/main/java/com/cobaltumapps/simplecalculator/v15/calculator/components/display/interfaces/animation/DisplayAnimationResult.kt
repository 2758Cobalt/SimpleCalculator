package com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.animation

interface DisplayAnimationResult {
    fun playDisplayResultAnim(onEnd: (() -> Unit)? = null)
    fun playHiddenResultAnim(onEnd: (() -> Unit)? = null)
}