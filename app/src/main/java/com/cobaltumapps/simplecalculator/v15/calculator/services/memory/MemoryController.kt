package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces.DisplayManager
import com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces.DisplayMemoryManager
import com.cobaltumapps.simplecalculator.v15.calculator.managers.CalculatorManager


class MemoryController: MemoryControllerListener {
    var canPerformAction = true
    private var memoryInstance: Memory? = null
    private var calculatorManager: CalculatorManager? = null

    private var displayListener : DisplayMemoryManager? = null

    // Установка экземпляров и слушателей
    fun setDisplayListener(listener: DisplayManager) {
        displayListener = listener
    }

    fun setMemoryInstance(memory: Memory) {
        this.memoryInstance = memory
    }

    fun setCalculatorManager(manager: CalculatorManager) {
        this.calculatorManager = manager
    }

    override fun actionSave() {
        if (canPerformAction){
            val result = calculatorManager?.getResult()!!.toFloat()
            memoryInstance?.save(result)
            displayListener?.setMemoryField(result)
        }
    }

    override fun actionRead() {
        val memoryStorage = memoryInstance?.read()!!

        calculatorManager?.setExpressionFromMemory(memoryStorage.toString())
        displayListener?.setMemoryField(memoryStorage)
    }

    override fun actionClear() {
        memoryInstance?.clear()
        displayListener?.setMemoryField(memoryInstance?.read()!!)
    }

    override fun actionMemoryAdd() {
        if (canPerformAction) {
            val memoryStorage = memoryInstance?.read()!!

            memoryInstance?.save(memoryStorage + calculatorManager?.getResult()!!.toFloat())
            updateDisplay()
        }
    }

    override fun actionMemorySub() {
        if (canPerformAction) {
            val memoryStorage = memoryInstance?.read()!!

            memoryInstance?.save(memoryStorage - calculatorManager?.getResult()!!.toFloat())
            updateDisplay()
        }
    }

    override fun actionMemoryMul() {
        if (canPerformAction) {
        val memoryStorage = memoryInstance?.read()!!

        memoryInstance?.save(memoryStorage * calculatorManager?.getResult()!!.toFloat())
        updateDisplay()
        }
    }

    override fun actionMemoryDiv() {
        if(canPerformAction) {
        val memoryStorage = memoryInstance?.read()!!

        memoryInstance?.save(memoryStorage / calculatorManager?.getResult()!!.toFloat())
        updateDisplay()
        }
    }

    private fun updateDisplay() {
        val memoryStorage = memoryInstance?.read()!!
        displayListener?.setMemoryField(memoryStorage)
    }
}