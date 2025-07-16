package com.cobaltumapps.simplecalculator.domain.repository.extra.loader

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.ExtraCalculatorsKeys as ECK

class ExtraUnitSymbolsLoader(private val context: Context) {

    fun getUnitSymbols(calculatorId: String): List<String>? {
        return with(context) {
            when(calculatorId) {
                ECK.CALC_UNIT_WEIGHT_ID -> applicationContext.resources.getStringArray(
                    R.array.weight_symbols).toList()
                ECK.CALC_UNIT_LENGTH_ID -> applicationContext.resources.getStringArray(
                    R.array.length_symbols).toList()
                ECK.CALC_UNIT_TIME_ID -> applicationContext.resources.getStringArray(
                    R.array.time_symbols).toList()
                ECK.CALC_UNIT_SPEED_ID -> applicationContext.resources.getStringArray(
                    R.array.speed_symbols).toList()
                ECK.CALC_UNIT_TEMPERATURE_ID -> applicationContext.resources.getStringArray(
                    R.array.temperature_symbols).toList()
                ECK.CALC_UNIT_VOLUME_ID -> applicationContext.resources.getStringArray(
                    R.array.volume_symbols).toList()
                ECK.CALC_UNIT_AREA_ID -> applicationContext.resources.getStringArray(
                    R.array.area_symbols).toList()
                ECK.CALC_UNIT_FREQUENCY_ID -> applicationContext.resources.getStringArray(
                    R.array.frequency_symbols).toList()
                ECK.CALC_UNIT_DATA_STORAGE_ID -> applicationContext.resources.getStringArray(
                    R.array.data_symbols).toList()
                ECK.CALC_UNIT_POWER_ID -> applicationContext.resources.getStringArray(
                    R.array.time_symbols).toList()
                ECK.CALC_UNIT_DATA_TRANSFER_ID -> applicationContext.resources.getStringArray(
                    R.array.time_symbols).toList()
                ECK.CALC_UNIT_ANGLE_ID -> applicationContext.resources.getStringArray(
                    R.array.time_symbols).toList()
                ECK.CALC_UNIT_RADIATION_ID -> applicationContext.resources.getStringArray(
                    R.array.time_symbols).toList()

                else -> getUnitSymbols(ECK.CALC_UNIT_WEIGHT_ID)
            }
        }
    }

}