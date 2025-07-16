package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric

import android.content.Context
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector.ExtraAlgebraCalculatorFabric
import com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector.ExtraDateTimeCalculatorFabric
import com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector.ExtraGeometryCalculatorFabric
import com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector.ExtraMechanicsCalculatorFabric
import com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector.ExtraUnitCalculatorFabric
import com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces.ExtraSelectableCalculatorFabric

/** Класс-обёртка для фабрики калькуляторов */
class ExtraSelectorCalculatorFabric: ExtraSelectableCalculatorFabric {
    private val extraUnitCalculatorFabric = ExtraUnitCalculatorFabric()
    private val extraAlgebraCalculatorFabric = ExtraAlgebraCalculatorFabric()
    private val extraGeometryCalculatorFabric = ExtraGeometryCalculatorFabric()
    private val extraMechanicsCalculatorFabric = ExtraMechanicsCalculatorFabric()
    private val extraDateTimeCalculatorFabric = ExtraDateTimeCalculatorFabric()

    private val extraFabrics by lazy {
        listOf(extraUnitCalculatorFabric)
    }

    override fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo> {
        return buildList {
            extraFabrics.forEach { addAll(it.createCalculators(context)) }
        }
    }
}