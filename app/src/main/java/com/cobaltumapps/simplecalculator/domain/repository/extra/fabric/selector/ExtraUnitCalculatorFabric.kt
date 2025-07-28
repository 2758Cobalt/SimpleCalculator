package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.selector

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.interfaces.ExtraSelectableCalculatorFabric
import com.cobaltumapps.simplecalculator.data.extra.constants.ExtraCalculatorsKeys as ECK

/** Фабрика, которая создает заранее заготовленные Unit-калькуляторы.
 * Эти калькуляторы являются базовыми и должны всегда присувствовать в приложении. */
class ExtraUnitCalculatorFabric: ExtraSelectableCalculatorFabric {

    override fun createCalculators(context: Context): List<ExtraSelectableCalculatorInfo> {
        val extraDrawables = with(context) {
            mapOf(
                ECK.CALC_UNIT_WEIGHT_ID to R.drawable.ic_weight,
                ECK.CALC_UNIT_LENGTH_ID to R.drawable.ic_length,
                ECK.CALC_UNIT_TIME_ID to R.drawable.ic_time,
                ECK.CALC_UNIT_SPEED_ID to R.drawable.ic_speed,
                ECK.CALC_UNIT_TEMPERATURE_ID to R.drawable.ic_temperature,
                ECK.CALC_UNIT_VOLUME_ID to R.drawable.ic_volume,
                ECK.CALC_UNIT_AREA_ID to R.drawable.ic_area,
                ECK.CALC_UNIT_FREQUENCY_ID to R.drawable.ic_frequency,
                ECK.CALC_UNIT_POWER_ID to R.drawable.ic_power,
                ECK.CALC_UNIT_PRESSURE_ID to R.drawable.ic_pressure,
                ECK.CALC_UNIT_ANGLE_ID to R.drawable.ic_angle,
                ECK.CALC_UNIT_DATA_STORAGE_ID to R.drawable.ic_data_storage,
                ECK.CALC_UNIT_DATA_TRANSFER_ID to R.drawable.ic_data_transfer,
                ECK.CALC_UNIT_RADIATION_ID to R.drawable.ic_radiation
            )
        }

        val extraTitles = with(context) {
            mapOf(
                ECK.CALC_UNIT_WEIGHT_ID to getString(R.string.extra_calculator_weight_unit_name),
                ECK.CALC_UNIT_LENGTH_ID to getString(R.string.extra_calculator_length_unit_name),
                ECK.CALC_UNIT_TIME_ID to getString(R.string.extra_calculator_time_unit_name),
                ECK.CALC_UNIT_SPEED_ID to getString(R.string.extra_calculator_speed_unit_name),
                ECK.CALC_UNIT_TEMPERATURE_ID to getString(R.string.extra_calculator_temperature_unit_name),
                ECK.CALC_UNIT_VOLUME_ID to getString(R.string.extra_calculator_volume_unit_name),
                ECK.CALC_UNIT_AREA_ID to getString(R.string.extra_calculator_area_unit_name),
                ECK.CALC_UNIT_FREQUENCY_ID to getString(R.string.extra_calculator_frequency_unit_name),
                ECK.CALC_UNIT_POWER_ID to getString(R.string.extra_calculator_power_unit_name),
                ECK.CALC_UNIT_ANGLE_ID to getString(R.string.extra_calculator_angle_unit_name),
                ECK.CALC_UNIT_PRESSURE_ID to getString(R.string.extra_calculator_pressure_unit_name),
                ECK.CALC_UNIT_DATA_STORAGE_ID to getString(R.string.extra_calculator_data_storage_name),
                ECK.CALC_UNIT_DATA_TRANSFER_ID to getString(R.string.extra_calculator_dataTransfer_unit_name),
                ECK.CALC_UNIT_RADIATION_ID to getString(R.string.extra_calculator_radiation_unit_name)
            )
        }

        val extraUnits = with(context) {
            mapOf(
                ECK.CALC_UNIT_WEIGHT_ID to getString(R.string.extra_calculator_weight_unit),
                ECK.CALC_UNIT_LENGTH_ID to getString(R.string.extra_calculator_length_unit),
                ECK.CALC_UNIT_TIME_ID to getString(R.string.extra_calculator_time_unit),
                ECK.CALC_UNIT_SPEED_ID to getString(R.string.extra_calculator_speed_unit),
                ECK.CALC_UNIT_TEMPERATURE_ID to getString(R.string.extra_calculator_temperature_unit),
                ECK.CALC_UNIT_VOLUME_ID to getString(R.string.extra_calculator_volume_unit),
                ECK.CALC_UNIT_AREA_ID to getString(R.string.extra_calculator_area_unit),
                ECK.CALC_UNIT_FREQUENCY_ID to getString(R.string.extra_calculator_frequency_unit),
                ECK.CALC_UNIT_POWER_ID to getString(R.string.extra_calculator_power_unit),
                ECK.CALC_UNIT_PRESSURE_ID to getString(R.string.extra_calculator_pressure_unit),
                ECK.CALC_UNIT_ANGLE_ID to getString(R.string.extra_calculator_angle_unit),
                ECK.CALC_UNIT_DATA_STORAGE_ID to getString(R.string.extra_calculator_data_storage_unit),
                ECK.CALC_UNIT_DATA_TRANSFER_ID to getString(R.string.extra_calculator_data_transfer_unit),
                ECK.CALC_UNIT_RADIATION_ID to getString(R.string.extra_calculator_data_storage_unit)
            )
        }

        val extraIds = listOf(
            ECK.CALC_UNIT_WEIGHT_ID,
            ECK.CALC_UNIT_LENGTH_ID,
            ECK.CALC_UNIT_TIME_ID,
            ECK.CALC_UNIT_SPEED_ID,
            ECK.CALC_UNIT_TEMPERATURE_ID,
            ECK.CALC_UNIT_VOLUME_ID,
            ECK.CALC_UNIT_AREA_ID,
            ECK.CALC_UNIT_FREQUENCY_ID,
            ECK.CALC_UNIT_POWER_ID,
            ECK.CALC_UNIT_PRESSURE_ID,
            ECK.CALC_UNIT_ANGLE_ID,
            ECK.CALC_UNIT_DATA_STORAGE_ID,
            ECK.CALC_UNIT_DATA_TRANSFER_ID,
            ECK.CALC_UNIT_RADIATION_ID
        )

        return extraIds.map { calcId ->
            ExtraSelectableCalculatorInfo(
                calculatorId = calcId,
                title = extraTitles[calcId] ?: "Unnamed",
                drawableResId = extraDrawables[calcId],
                previewValues = extraUnits[calcId] ?: "",
                type = ExtraCalculatorType.Unit
            )
        }
    }
}