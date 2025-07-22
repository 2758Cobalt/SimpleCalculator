package com.cobaltumapps.simplecalculator.domain.repository.extra.loader

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.ExtraCalculatorsKeys as ECK

class ExtraUnitNamesLoader(private val context: Context) {

    private val unitResourceMap = mapOf(
        ECK.CALC_UNIT_WEIGHT_ID to R.array.weight_unit,
        ECK.CALC_UNIT_LENGTH_ID to R.array.length_unit,
        ECK.CALC_UNIT_TIME_ID to R.array.time_unit,
        ECK.CALC_UNIT_SPEED_ID to R.array.speed_unit,
        ECK.CALC_UNIT_TEMPERATURE_ID to R.array.temperature_unit,
        ECK.CALC_UNIT_VOLUME_ID to R.array.volume_unit,
        ECK.CALC_UNIT_AREA_ID to R.array.area_unit,
        ECK.CALC_UNIT_FREQUENCY_ID to R.array.frequency_unit,
        ECK.CALC_UNIT_PRESSURE_ID to R.array.pressure_unit,
        ECK.CALC_UNIT_ANGLE_ID to R.array.angle_unit,
        ECK.CALC_UNIT_POWER_ID to R.array.power_unit,
        ECK.CALC_UNIT_DATA_STORAGE_ID to R.array.data_storage_unit,
        ECK.CALC_UNIT_DATA_TRANSFER_ID to R.array.data_transfer_unit,
    )

    fun getUnitNames(calculatorId: String): List<String> {
        val resId = unitResourceMap[calculatorId] ?: return emptyList()
        return loadFromResources(resId)
    }

    private fun loadFromResources(resId: Int): List<String> {
        return context.resources.getStringArray(resId).toList()
    }

}