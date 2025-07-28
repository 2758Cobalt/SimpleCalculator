package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces.ExtraSelectableCalculatorFabric
import com.cobaltumapps.simplecalculator.data.extra.constants.ExtraCalculatorsKeys as ECK

/** */
class ExtraDateTimeCalculatorFabric: ExtraSelectableCalculatorFabric {

    override fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo> {
        val extraDrawables = with(context) {
            mapOf(
                ECK.CALC_DATETIME_AGE_ID to R.drawable.ic_weight,
                ECK.CALC_DATETIME_DATE_ID to R.drawable.ic_weight,
                ECK.CALC_DATETIME_TIME_ID to R.drawable.ic_weight,
                ECK.CALC_DATETIME_INTERVAL_ID to R.drawable.ic_weight
            )
        }

        val extraTitles = with(context) {
            mapOf(
                ECK.CALC_DATETIME_AGE_ID to getString(R.string.extra_calculator_age_dateTime_name),
                ECK.CALC_DATETIME_DATE_ID to getString(R.string.extra_calculator_dateTime_date_name),
                ECK.CALC_DATETIME_TIME_ID to getString(R.string.extra_calculator_dateTime_time_name),
                ECK.CALC_DATETIME_INTERVAL_ID to getString(R.string.extra_calculator_interval_dateTime_name)
            )
        }

        val extraIds = listOf(
            ECK.CALC_DATETIME_AGE_ID,
            ECK.CALC_DATETIME_DATE_ID,
            ECK.CALC_DATETIME_TIME_ID,
            ECK.CALC_DATETIME_INTERVAL_ID
        )

        return extraIds.map { calcId ->
            ExtraSelectableCalculatorInfo(
                calculatorId = calcId,
                title = extraTitles[calcId] ?: "Unnamed",
                drawableResId = extraDrawables[calcId],
                previewValues = "",
                type = ExtraCalculatorType.Date
            )
        }
    }

}