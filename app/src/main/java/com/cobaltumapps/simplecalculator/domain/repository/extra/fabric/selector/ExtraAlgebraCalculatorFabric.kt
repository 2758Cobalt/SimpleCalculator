package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces.ExtraSelectableCalculatorFabric
import com.cobaltumapps.simplecalculator.data.extra.constants.ExtraCalculatorsKeys as ECK

class ExtraAlgebraCalculatorFabric: ExtraSelectableCalculatorFabric {
    override fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo> {
        val extraDrawables = with(context) {
            mapOf(
                ECK.CALC_ALGEBRA_PROPORTION_ID to R.drawable.ic_proportional,
                ECK.CALC_ALGEBRA_AVERAGE_ID to R.drawable.ic_average,
                ECK.CALC_ALGEBRA_PERCENTAGE_ID to R.drawable.ic_weight,
                ECK.CALC_ALGEBRA_RATIO_ID to R.drawable.ic_weight
            )
        }

        val extraTitles = with(context) {
            mapOf(
                ECK.CALC_ALGEBRA_PROPORTION_ID to getString(R.string.extra_calculator_proportion_algebra_name),
                ECK.CALC_ALGEBRA_AVERAGE_ID to getString(R.string.extra_calculator_average_algebra_name),
                ECK.CALC_ALGEBRA_PERCENTAGE_ID to getString(R.string.extra_calculator_percentage_algebra_name),
                ECK.CALC_ALGEBRA_RATIO_ID to getString(R.string.extra_calculator_ratio_algebra_name)
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
                drawableResId = extraDrawables[calcId],
                previewValues = extraUnits[calcId] ?: "",
                type = ExtraCalculatorType.Algebra
            )
        }
    }
}