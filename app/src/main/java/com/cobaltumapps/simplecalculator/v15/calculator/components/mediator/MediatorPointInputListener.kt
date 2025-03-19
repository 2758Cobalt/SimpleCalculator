package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

interface MediatorPointInputListener {
    fun isAllowPointInput(onAllowed: ((condition: Boolean) -> Unit?)?)
}