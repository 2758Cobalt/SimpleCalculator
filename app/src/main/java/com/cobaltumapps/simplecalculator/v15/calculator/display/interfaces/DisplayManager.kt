package com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces

interface DisplayManager: DisplayFieldManager, DisplayMemoryManager {

}

// Интерфейс отвечающий за взаимодействие с полями
interface DisplayFieldManager {
    fun setExpressionField(newExpression: Number)
}

interface DisplayMemoryManager {
    fun setMemoryField(newValueMemory: Number)
    fun setRadAngleMode(isEnabled: Boolean)
}