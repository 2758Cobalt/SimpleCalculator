package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardInstanceGetter
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.loggers.KeyboardControllerLogger
import com.cobaltumapps.simplecalculator.v15.calculator.components.mediator.MediatorController
import com.cobaltumapps.simplecalculator.v15.calculator.services.tallback.VibrationServiceController

open class KeyboardControllerMaster(
    protected var mediator: MediatorController? = null
): KeyboardInstanceGetter {
    protected val keyboardControllerLogger = KeyboardControllerLogger()

    protected var controlledKeyboardMaster: KeyboardMaster? = null
    protected var vibrationServiceListener: VibrationServiceController? = null

    fun setVibrationListener(newListener: VibrationServiceController) {
        this.vibrationServiceListener = newListener
    }

    fun setNewMediator(mediatorController: MediatorController) {
        mediator = mediatorController
    }

    override fun getInstance(instance: KeyboardMaster) {
        keyboardControllerLogger.getInstance(instance)
    }
}

