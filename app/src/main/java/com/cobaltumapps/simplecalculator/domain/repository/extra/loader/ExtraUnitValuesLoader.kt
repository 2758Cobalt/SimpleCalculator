package com.cobaltumapps.simplecalculator.domain.repository.extra.loader

import com.cobaltumapps.simplecalculator.data.extra.constants.ConvertersValues
import com.cobaltumapps.simplecalculator.data.extra.ExtraCalculatorsKeys as ECK

class ExtraUnitValuesLoader {

    fun getUnitValues(calculatorId: String): Array<Array<Double>> {
        return when(calculatorId) {
            ECK.CALC_UNIT_WEIGHT_ID -> ConvertersValues.weightValues
            ECK.CALC_UNIT_LENGTH_ID -> ConvertersValues.lengthValues
            ECK.CALC_UNIT_TIME_ID -> ConvertersValues.timeValues
            ECK.CALC_UNIT_SPEED_ID -> ConvertersValues.speedValues
            ECK.CALC_UNIT_TEMPERATURE_ID -> ConvertersValues.temperatureValue
            ECK.CALC_UNIT_VOLUME_ID -> ConvertersValues.volumeValues
            ECK.CALC_UNIT_AREA_ID -> ConvertersValues.areaValues
            ECK.CALC_UNIT_FREQUENCY_ID -> ConvertersValues.frequencyValues
            ECK.CALC_UNIT_DATA_STORAGE_ID -> ConvertersValues.dataValues
            else -> {
                getUnitValues(ECK.CALC_UNIT_WEIGHT_ID)
            }
        }
    }

}