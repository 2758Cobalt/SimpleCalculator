package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master

import android.content.Context
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardInstanceSender

open class KeyboardMaster: Fragment(), KeyboardInstanceSender {
    protected var keyboardController: KeyboardControllerMaster? = null

    fun setNewKeyboardController(controller: KeyboardControllerMaster) {
        keyboardController = controller
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        keyboardController?.getInstance(this)
    }

    override fun sendInstance(instance: KeyboardMaster) {
        keyboardController?.getInstance(instance)
    }
}