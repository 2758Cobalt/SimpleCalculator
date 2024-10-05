package com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces

interface DisplayAnimations {

    fun playDisplayResultAnim()
    fun playHiddenResultAnim()


    fun playClearAnimation(onEnd: (() -> Unit)?)
    fun playMemoryFieldAnimation(onEnd: (() -> Unit)?)
    fun playAngleFieldAnimation(onEnd: (() -> Unit)?)
}