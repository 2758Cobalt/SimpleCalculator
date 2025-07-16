package com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces

import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo

interface ExtraSelectableCalculator {
    fun setCalculatorInfo(calcInfo: ExtraSelectableCalculatorInfo)
}