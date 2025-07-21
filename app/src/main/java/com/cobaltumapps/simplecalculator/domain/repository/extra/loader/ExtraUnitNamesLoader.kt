package com.cobaltumapps.simplecalculator.domain.repository.extra.loader

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.ExtraCalculatorsKeys as ECK

class ExtraUnitNamesLoader(private val context: Context) {

    fun getUnitNames(calculatorId: String): List<String> {
        return with(context) {
            when(calculatorId) {
                ECK.CALC_UNIT_WEIGHT_ID -> applicationContext.resources.getStringArray(
                    R.array.weight_unit).toList()
                ECK.CALC_UNIT_LENGTH_ID -> applicationContext.resources.getStringArray(
                    R.array.length_unit).toList()
                ECK.CALC_UNIT_TIME_ID -> applicationContext.resources.getStringArray(
                    R.array.time_unit).toList()
                ECK.CALC_UNIT_SPEED_ID -> applicationContext.resources.getStringArray(
                    R.array.speed_unit).toList()
                ECK.CALC_UNIT_TEMPERATURE_ID -> applicationContext.resources.getStringArray(
                    R.array.temperature_unit).toList()
                ECK.CALC_UNIT_VOLUME_ID -> applicationContext.resources.getStringArray(
                    R.array.volume_unit).toList()
                ECK.CALC_UNIT_AREA_ID -> applicationContext.resources.getStringArray(
                    R.array.area_unit).toList()
                ECK.CALC_UNIT_FREQUENCY_ID -> applicationContext.resources.getStringArray(
                    R.array.frequency_unit).toList()
                ECK.CALC_UNIT_DATA_STORAGE_ID -> applicationContext.resources.getStringArray(
                    R.array.data_storage_unit).toList()
                ECK.CALC_UNIT_DATA_TRANSFER_ID -> applicationContext.resources.getStringArray(
                    R.array.data_transfer_unit).toList()
                ECK.CALC_UNIT_ANGLE_ID -> applicationContext.resources.getStringArray(
                    R.array.angle_unit).toList()

                else -> getUnitNames(ECK.CALC_UNIT_WEIGHT_ID)
            }
        }
    }

}