package com.cobaltumapps.simplecalculator.data.extra.constants

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit.LinearConversionStrategy
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit.TemperatureConversionStrategy
import com.cobaltumapps.simplecalculator.data.extra.ExtraCalculatorsKeys as ECK

object ConversionContext {
    val conversionContext = mapOf(
        ECK.CALC_UNIT_WEIGHT_ID to LinearConversionStrategy(),
        ECK.CALC_UNIT_TIME_ID to LinearConversionStrategy(),
        ECK.CALC_UNIT_LENGTH_ID to LinearConversionStrategy(),
        ECK.CALC_UNIT_TEMPERATURE_ID to TemperatureConversionStrategy(),
        ECK.CALC_UNIT_VOLUME_ID to LinearConversionStrategy(),
        ECK.CALC_UNIT_AREA_ID to LinearConversionStrategy(),
        ECK.CALC_UNIT_FREQUENCY_ID to LinearConversionStrategy(),
        ECK.CALC_UNIT_DATA_STORAGE_ID to LinearConversionStrategy(),
        ECK.CALC_UNIT_DATA_TRANSFER_ID to LinearConversionStrategy(),
    )
}