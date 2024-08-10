package com.cobaltumapps.simplecalculator.v15.calculator.host.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.enums.ActionOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MemoryOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.TrigonometryOperation

interface HostManager {
}

interface NumpadListener: NumpadActionsListener {
    fun onClickNumber(number: Number)
    fun onClickOperation(operation: MathOperation)
    fun onClickAction(action: ActionOperation)
}

interface NumpadActionsListener {
    fun onActionAllClear()
    fun onActionEqual()
    fun onActionBackspace()
    fun onActionGroupClean()
}

interface EngineeringNumpadKeyboard {
    fun onClickOperation(operation: MathOperation)
    fun onClickMemoryOperation(memoryOperation: MemoryOperation)
    fun onClickTrigonometryOperation(trigonometryOperation: TrigonometryOperation)
    fun onClickAngleMode(angleMode: AngleMode)
}

interface QuickOperationsIcons {
    fun onClickMinimize()
    fun onClickQuickBackspace()
}

interface NavigationKeys {
    fun goSettings()
    fun goConverters()
}