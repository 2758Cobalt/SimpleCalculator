package com.cobaltumapps.simplecalculator.v15.calculator.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorManagerListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryController


open class InputController {
    protected var memoryControllerInstance: MemoryController? = null
    protected var calculatorManagerListener: CalculatorManagerListener? = null

    fun setMemoryControllerListener(instance: MemoryController) {
        this.memoryControllerInstance = instance
    }

    fun setCalculatorManager(listener: CalculatorManagerListener) {
        this.calculatorManagerListener = listener
    }
}