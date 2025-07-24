package com.cobaltumapps.simplecalculator.domain.repository.extra.loader

import android.content.Context
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo
import java.math.BigDecimal

class ExtraUnitInfoLoader(context: Context) {
    private val extraUnitNamesLoader = ExtraUnitNamesLoader(context)
    private val extraUnitSymbolsLoader = ExtraUnitSymbolsLoader(context)

    fun getUnitInfo(calculatorId: String): List<ExtraUnitInfo> {
        val unitNames = extraUnitNamesLoader.getUnitNames(calculatorId)
        val unitSymbols = extraUnitSymbolsLoader.getUnitSymbols(calculatorId)

        val builtUnitInfo = unitNames.mapIndexed { index, item ->
            ExtraUnitInfo(
                item,
                unitSymbols[index],
                BigDecimal("0.0")
            )
        }

        return builtUnitInfo
    }

}