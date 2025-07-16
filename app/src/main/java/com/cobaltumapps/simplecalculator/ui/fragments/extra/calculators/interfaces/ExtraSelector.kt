package com.cobaltumapps.simplecalculator.ui.fragments.extra.calculators.interfaces

import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo

interface ExtraSelector {
    fun onSelectedItem(calculatorItem: ExtraSelectableCalculatorInfo)
}