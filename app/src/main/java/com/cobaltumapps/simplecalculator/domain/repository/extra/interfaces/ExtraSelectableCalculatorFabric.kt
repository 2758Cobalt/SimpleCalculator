package com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces

import android.content.Context
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo

interface ExtraSelectableCalculatorFabric {
    fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo>
}