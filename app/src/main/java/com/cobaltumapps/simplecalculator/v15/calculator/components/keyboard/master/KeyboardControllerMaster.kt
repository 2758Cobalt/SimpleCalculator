package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardInstanceGetter
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.loggers.KeyboardControllerLogger
import com.cobaltumapps.simplecalculator.v15.calculator.components.mediator.MediatorController

open class KeyboardControllerMaster(
    protected var mediator: MediatorController? = null
): KeyboardInstanceGetter {
    protected val keyboardControllerLogger = KeyboardControllerLogger()

    protected var controlledKeyboardMaster: KeyboardMaster? = null

    fun setNewMediator(mediatorController: MediatorController) {
        mediator = mediatorController
    }

    override fun getInstance(instance: KeyboardMaster) {
        keyboardControllerLogger.getInstance(instance)
    }
}

