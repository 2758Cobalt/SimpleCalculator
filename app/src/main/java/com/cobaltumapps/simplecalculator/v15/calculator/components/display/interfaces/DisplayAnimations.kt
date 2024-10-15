package com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces

interface DisplayAnimations {

    fun playDisplayResultAnim(onEnd: (() -> Unit)? = null)
    fun playHiddenResultAnim(onEnd: (() -> Unit)? = null)


    fun playClearAnimation(onEnd: (() -> Unit)?)
    fun playMemoryFieldAnimation(onEnd: (() -> Unit)?)
    fun playAngleFieldAnimation(onEnd: (() -> Unit)?)
}