package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import android.content.Context
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayAngleViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayComponentProperties
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayMemoryViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayViewerListener
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

open class DisplayComponent:
    Fragment(),
    DisplayComponentProperties,
    DisplayViewerListener,
    DisplayMemoryViewer,
    DisplayAngleViewer
{
    private var displayControllerImpl: DisplayControllerImpl? = null
    var displayAnimator: DisplayAnimator? = DisplayAnimator()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Передача экземпляра this -> DisplayControllerImpl
        displayControllerImpl?.setNewDisplayInstance(this)
    }

    override fun setAngleField(angleMode: AngleMode) {
        displayControllerImpl?.setAngleField(angleMode)
    }

    override fun setMemoryField(memoryValue: Number) {
        displayControllerImpl?.setMemoryField(memoryValue)
    }

    override fun setExpressionField(newExpression: String) {
        displayControllerImpl?.setExpressionField(newExpression)
    }

    override fun setResultField(newResult: String) {
        displayControllerImpl?.setResultField(newResult)
    }

    override fun setNewDisplayController(controller: DisplayControllerImpl) {
        this.displayControllerImpl = controller
    }

    override fun setNewDisplayAnimator(animator: DisplayAnimator) {
        this.displayAnimator = animator
    }
}