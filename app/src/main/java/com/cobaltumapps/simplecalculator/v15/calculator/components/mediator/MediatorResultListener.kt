package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

interface MediatorResultListener {
    fun isResultCondition(onCalculatedResult: ((condition: Boolean) -> Unit?)?)
}