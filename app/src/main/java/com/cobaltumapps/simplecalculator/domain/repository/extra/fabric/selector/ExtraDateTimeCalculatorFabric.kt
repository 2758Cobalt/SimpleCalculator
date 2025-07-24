package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector

import android.content.Context
import androidx.core.content.res.ResourcesCompat
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
                ECK.CALC_DATETIME_AGE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_DATETIME_DATE_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_DATETIME_TIME_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme),
                ECK.CALC_DATETIME_INTERVAL_ID to ResourcesCompat.getDrawable(resources, R.drawable.ic_weight, theme)
            )
        }

        val extraTitles = with(context) {
            mapOf(
                ECK.CALC_DATETIME_AGE_ID to getString(R.string.extra_calculator_age_name),
                ECK.CALC_DATETIME_DATE_ID to getString(R.string.extra_calculator_date_name),
                ECK.CALC_DATETIME_TIME_ID to getString(R.string.extra_calculator_time_name),
                ECK.CALC_DATETIME_INTERVAL_ID to getString(R.string.extra_calculator_interval_name)
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
                drawable = extraDrawables[calcId],
                previewValues = "",
                type = ExtraCalculatorType.Date
            )
        }
    }

}