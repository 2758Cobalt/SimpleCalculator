package com.cobaltumapps.simplecalculator.domain.repository.extra.loader

import android.content.Context
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo

class ExtraUnitInfoLoader(
    private val context: Context
) {
    private val extraUnitNamesLoader = ExtraUnitNamesLoader(context)
    private val extraUnitSymbolsLoader = ExtraUnitSymbolsLoader(context)

    fun getUnitInfo(calculatorId: String): List<ExtraUnitInfo> {
        val unitNames = extraUnitNamesLoader.getUnitNames(calculatorId)
        val unitSymbols = extraUnitSymbolsLoader.getUnitSymbols(calculatorId)

        val builtUnitInfo = unitNames.mapIndexed { index, item ->
            ExtraUnitInfo(
                item,
                unitSymbols?.get(index),
                0f
            )
        }

        return builtUnitInfo
    }

}