package com.cobaltumapps.simplecalculator.v15.fragments.converter

import com.cobaltumapps.simplecalculator.v15.converter.adapters.OnAdapterSelectedItem
import com.cobaltumapps.simplecalculator.v15.converter.calculator.ConverterDiagonalMatrixCalculator
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterUserInputHandlerListener

class ConverterCalculatorManager: ConverterUserInputHandlerListener, OnAdapterSelectedItem {
    private val converterCalculatorSelector = ConverterCalculatorSelector()
    private val converterDiagonalMatrixCalculator = ConverterDiagonalMatrixCalculator()

    private var userEntry = "0"

    fun setNewData(newData: Array<Array<Double>>) {
        converterDiagonalMatrixCalculator.setNewValuesToConvert(newData)
    }

    fun calculate(): Array<Number> {
        val selectedItem = converterCalculatorSelector.getSelectedPosition()
        val results = converterDiagonalMatrixCalculator.calculate(userEntry, selectedItem)
        return results
    }

    override fun receiveUserEntry(receivedEntry: String) {
        userEntry = receivedEntry
    }

    override fun selectedItemPosition(position: Int) {
        converterCalculatorSelector.selectedItemPosition(position)
    }
}