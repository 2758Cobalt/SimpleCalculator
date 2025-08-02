package com.cobaltumapps.simplecalculator.data.extra.constants.context

import com.cobaltumapps.simplecalculator.data.extra.constants.ExtraCalculatorsKeys
import com.cobaltumapps.simplecalculator.data.extra.enums.units.AngleUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.AreaUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.DataStorageUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.DataTransferUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.FrequencyUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.LengthUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.PowerUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.PressureUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.SpeedUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.TimeUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.VolumeUnit
import com.cobaltumapps.simplecalculator.data.extra.enums.units.WeightUnit
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

object UniversalConversionStrategyContext {

    val unitsByCalculatorId: Map<String, Array<UnitConversion>> = mapOf(
        ExtraCalculatorsKeys.CALC_UNIT_WEIGHT_ID to WeightUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_TIME_ID to TimeUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_LENGTH_ID to LengthUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_SPEED_ID to SpeedUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_VOLUME_ID to VolumeUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_AREA_ID to AreaUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_FREQUENCY_ID to FrequencyUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_PRESSURE_ID to PressureUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_POWER_ID to PowerUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_ANGLE_ID to AngleUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_DATA_STORAGE_ID to DataStorageUnit.entries.toTypedArray<UnitConversion>(),
        ExtraCalculatorsKeys.CALC_UNIT_DATA_TRANSFER_ID to DataTransferUnit.entries.toTypedArray<UnitConversion>()
    )

}