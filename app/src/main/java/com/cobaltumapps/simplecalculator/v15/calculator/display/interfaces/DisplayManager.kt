package com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

interface
DisplayManager:
    DisplayExpressionFieldManager,
    DisplayCleaningManager,
    DisplayMemoryManager,
    DisplayAngleModeManager

// Интерфейс отвечающий за взаимодействие с полями
interface DisplayExpressionFieldManager {
    fun setExpressionField(newExpression: String)
    fun setCalculatedExpression(result: String)
}

// Интерфейс отвечающий за очистку полей
interface DisplayCleaningManager {
    fun clearDisplay()
}

// Интерфейс отвечающий за управление поля памяти
interface DisplayMemoryManager {
    fun setMemoryField(newValueMemory: Number)
}

// Интерфейс отвечающий за взаимодействие с полями
interface DisplayAngleModeManager {
    fun setAngleMode(angleMode: AngleMode)
}