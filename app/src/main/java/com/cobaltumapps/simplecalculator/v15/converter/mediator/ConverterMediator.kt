package com.cobaltumapps.simplecalculator.v15.converter.mediator

import com.cobaltumapps.simplecalculator.v15.converter.adapters.OnAdapterSelectedItem
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterNumpadControllerListener
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterLoaderData
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoLoaderListener
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterCalculatorFragment
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterCalculatorManager
import com.cobaltumapps.simplecalculator.v15.fragments.converter.interfaces.ConverterCalculatorListener

class ConverterMediator :
    OnAdapterSelectedItem, ConverterCalculatorListener,
    ConverterNumpadControllerListener, InfoLoaderListener,
    ConverterMediatorHardUpdater
{
    private val converterCalculatorManager = ConverterCalculatorManager()

    // Сюда лучше интерфейсы фрагментов
    var calculatorFragmentInstance: ConverterCalculatorFragment? = null
    var pageFragmentListener: ConverterCalculatorListener? = null

    private var calculatedResults: Array<Number> = arrayOf()

    override fun selectedItemPosition(position: Int) {
        converterCalculatorManager.selectedItemPosition(position)
    }

    override fun listenCalculatedResults(results: Array<Number>) {
        pageFragmentListener?.listenCalculatedResults(results)
    }

    override fun receiveUserEntry(userEntry: String) {
        converterCalculatorManager.receiveUserEntry(userEntry)
    }

    override fun confirmEntry() {
        calculatedResults = converterCalculatorManager.calculate()
        listenCalculatedResults(calculatedResults)
    }

    override fun updateConverterData(converterLoaderData: ConverterLoaderData) {
        converterCalculatorManager.setNewData(converterLoaderData.converterUnitsModel.unitsValuesToConvertArray)
    }

    override fun hardUpdateCalculations() {
        confirmEntry()
    }

}