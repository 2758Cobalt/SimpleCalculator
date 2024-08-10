package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

interface MemoryControllerListener :
    MemoryControllerBaseAction,
    MemoryControllerMathAction

interface MemoryControllerBaseAction {
    fun actionSave()
    fun actionRead()
    fun actionClear()
}

interface MemoryControllerMathAction {
    fun actionMemoryAdd()
    fun actionMemorySub()
    fun actionMemoryMul()
    fun actionMemoryDiv()
}