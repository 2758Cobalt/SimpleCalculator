package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces.ExtraSelectableCalculatorFabric
import com.cobaltumapps.simplecalculator.data.extra.ExtraCalculatorsKeys as ECK

class ExtraMechanicsCalculatorFabric: ExtraSelectableCalculatorFabric {
    override fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo> {
        val extraDrawables = with(context) {
            mapOf(
                ECK.CALC_MECHANICS_ENERGY_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_power, theme)
            )
        }

        val extraTitles = with(context) {
            mapOf(
                ECK.CALC_MECHANICS_ENERGY_ID to getString(R.string.extra_calculator_energy_name)
            )
        }

        val extraIds = listOf(
            ECK.CALC_MECHANICS_ENERGY_ID
        )

        return extraIds.map { calcId ->
            ExtraSelectableCalculatorInfo(
                calculatorId = calcId,
                title = extraTitles[calcId] ?: "Unnamed",
                drawable = extraDrawables[calcId],
                previewValues = "",
                type = ExtraCalculatorType.Algebra
            )
        }
    }
}

