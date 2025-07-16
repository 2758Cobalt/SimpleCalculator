package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces.ExtraSelectableCalculatorFabric
import com.cobaltumapps.simplecalculator.data.extra.ExtraCalculatorsKeys as ECK

class ExtraAlgebraCalculatorFabric: ExtraSelectableCalculatorFabric {
    override fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo> {
        val extraDrawables = with(context) {
            mapOf(
                ECK.CALC_ALGEBRA_PROPORTION_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_proportional, theme),
                ECK.CALC_ALGEBRA_AVERAGE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_average, theme),
                ECK.CALC_ALGEBRA_PERCENTAGE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_ALGEBRA_RATIO_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
            )
        }

        val extraTitles = with(context) {
            mapOf(
                ECK.CALC_ALGEBRA_PROPORTION_ID to getString(R.string.extra_calculator_proportion_name),
                ECK.CALC_ALGEBRA_AVERAGE_ID to getString(R.string.extra_calculator_average_name),
                ECK.CALC_ALGEBRA_PERCENTAGE_ID to getString(R.string.extra_calculator_percentage_name),
                ECK.CALC_ALGEBRA_RATIO_ID to getString(R.string.extra_calculator_ratio_name)
            )
        }

        val extraUnits = with(context) {
            mapOf(
                ECK.CALC_ALGEBRA_PROPORTION_ID to getString(R.string.extra_calculator_weight_unit),
                ECK.CALC_ALGEBRA_AVERAGE_ID to getString(R.string.extra_calculator_length_unit),
                ECK.CALC_ALGEBRA_PERCENTAGE_ID to getString(R.string.extra_calculator_time_unit),
                ECK.CALC_ALGEBRA_RATIO_ID to getString(R.string.extra_calculator_speed_unit)
            )
        }

        val extraIds = listOf(
            ECK.CALC_ALGEBRA_PROPORTION_ID,
            ECK.CALC_ALGEBRA_AVERAGE_ID,
            ECK.CALC_ALGEBRA_PERCENTAGE_ID,
            ECK.CALC_ALGEBRA_RATIO_ID
        )

        return extraIds.map { calcId ->
            ExtraSelectableCalculatorInfo(
                calculatorId = calcId,
                title = extraTitles[calcId] ?: "Unnamed",
                drawable = extraDrawables[calcId],
                previewValues = extraUnits[calcId] ?: "",
                type = ExtraCalculatorType.Algebra
            )
        }
    }
}