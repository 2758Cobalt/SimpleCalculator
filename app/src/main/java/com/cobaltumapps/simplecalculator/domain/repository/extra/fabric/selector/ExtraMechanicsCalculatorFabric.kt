package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces.ExtraSelectableCalculatorFabric
import com.cobaltumapps.simplecalculator.data.extra.constants.ExtraCalculatorsKeys as ECK

class ExtraMechanicsCalculatorFabric: ExtraSelectableCalculatorFabric {
    override fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo> {
        val extraDrawables = with(context) {
            mapOf(
                ECK.CALC_MECHANICS_ENERGY_ID to R.drawable.ic_power
            )
        }

        val extraTitles = with(context) {
            mapOf(
                ECK.CALC_MECHANICS_ENERGY_ID to getString(R.string.extra_calculator_energy_mechanic_name)
            )
        }

        val extraIds = listOf(
            ECK.CALC_MECHANICS_ENERGY_ID
        )

        return extraIds.map { calcId ->
            ExtraSelectableCalculatorInfo(
                calculatorId = calcId,
                title = extraTitles[calcId] ?: "Unnamed",
                drawableResId = extraDrawables[calcId],
                previewValues = "",
                type = ExtraCalculatorType.Algebra
            )
        }
    }
}

