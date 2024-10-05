package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardInstanceGetter
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.loggers.KeyboardControllerLogger
import com.cobaltumapps.simplecalculator.v15.calculator.components.mediator.MediatorController

open class KeyboardControllerMaster: KeyboardInstanceGetter {
    protected val keyboardControllerLogger = KeyboardControllerLogger()

    protected var controlledKeyboardMaster: KeyboardMaster? = null
    protected var mediator: MediatorController? = null

    fun setNewMediator(mediatorController: MediatorController) {
        mediator = mediatorController
    }

    protected fun setControlledKeyboard(keyboard: KeyboardMaster) {
        this.controlledKeyboardMaster = keyboard
    }

    override fun getInstance(instance: KeyboardMaster) {
        keyboardControllerLogger.getInstance(instance)
    }
}

