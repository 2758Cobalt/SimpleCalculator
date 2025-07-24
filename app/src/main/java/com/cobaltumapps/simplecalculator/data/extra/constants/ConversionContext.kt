package com.cobaltumapps.simplecalculator.data.extra.constants

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit.TemperatureConversionStrategy
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit.UniversalConversionStrategy
import com.cobaltumapps.simplecalculator.data.extra.constants.ExtraCalculatorsKeys as ECK

object ConversionContext {
    val conversionContext = mapOf(
        ECK.CALC_UNIT_WEIGHT_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_TIME_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_LENGTH_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_SPEED_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_TEMPERATURE_ID to TemperatureConversionStrategy(),
        ECK.CALC_UNIT_VOLUME_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_AREA_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_FREQUENCY_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_PRESSURE_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_POWER_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_DATA_STORAGE_ID to UniversalConversionStrategy(),
        ECK.CALC_UNIT_DATA_TRANSFER_ID to UniversalConversionStrategy(),
    )
}