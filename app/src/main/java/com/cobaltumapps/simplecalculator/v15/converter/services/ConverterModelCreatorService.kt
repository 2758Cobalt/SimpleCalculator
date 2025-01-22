package com.cobaltumapps.simplecalculator.v15.converter.services

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterData
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitsModel
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType

/** Класс который создаёт и возвращает готовую модель конвертера исходя из выбраного типа конвертера.
 * Не проходит SOLID */
class ConverterModelCreatorService(private val context: Context) {

    /** Возвращает созданую модель для конвертера исходя из типа выбраного конвертера*/
    fun getConverterModel(converterType: ConverterType): ConverterData {
        val unitTitle = getTitle(converterType)
        val unitDrawable = getDrawable(converterType)
        val unitResourcesList = getUnitModel(converterType)

        return ConverterData(unitTitle, unitDrawable, converterType, unitResourcesList)

    }

    /** Возвращает заголовок исходя из выбраного типа конвертера */
    private fun getTitle(converterType: ConverterType): String {
        return when(converterType) {
            ConverterType.Weight -> context.resources.getString(R.string.converter_weight)
            ConverterType.Length -> context.resources.getString(R.string.converter_length)
            ConverterType.Time -> context.resources.getString(R.string.converter_time)
            ConverterType.Speed -> context.resources.getString(R.string.converter_speed)
            ConverterType.Temperature -> context.resources.getString(R.string.converter_temperature)
            ConverterType.Volume -> context.resources.getString(R.string.converter_volume)
            ConverterType.Area -> context.resources.getString(R.string.converter_area)
            ConverterType.Frequency -> context.resources.getString(R.string.converter_frequency)
            ConverterType.Data -> context.resources.getString(R.string.converter_data)
            else -> getTitle(ConverterType.Weight)
        }
    }

    /** Возвращает иконку исходя из выбраного типа конвертера */
    private fun getDrawable(converterType: ConverterType): Drawable? {
        return when(converterType) {
            ConverterType.Weight -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_weight, context.theme)
            ConverterType.Length -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_length, context.theme)
            ConverterType.Time -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_time, context.theme)
            ConverterType.Speed -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_speed, context.theme)
            ConverterType.Temperature -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_temperatures, context.theme)
            ConverterType.Volume -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_volume, context.theme)
            ConverterType.Area -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_area, context.theme)
            ConverterType.Frequency -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_frequency, context.theme)
            ConverterType.Data -> ResourcesCompat.getDrawable(context.resources, R.drawable.ic_data, context.theme)
            else -> getDrawable(ConverterType.Weight)
        }
    }

    /** Возвращает модель нужной единицы исходя из выбраного типа конвертера */
    private fun getUnitModel(converterType: ConverterType): ConverterUnitsModel {
        return when(converterType) {
            ConverterType.Weight -> {
                ConverterUnitsModel(
                    context.resources.getStringArray(R.array.weight_unit).toList(),
                    context.resources.getStringArray(R.array.weight_symbols).toList())
            }

            ConverterType.Length -> {
                ConverterUnitsModel(
                    context.resources.getStringArray(R.array.length_unit).toList(),
                    context.resources.getStringArray(R.array.length_symbols).toList())
            }
            ConverterType.Time -> {
                ConverterUnitsModel(
                    context.resources.getStringArray(R.array.time_unit).toList(),
                    context.resources.getStringArray(R.array.time_symbols).toList())
            }
            ConverterType.Speed -> {
                ConverterUnitsModel(
                    context.resources.getStringArray(R.array.speed_unit).toList(),
                    context.resources.getStringArray(R.array.speed_symbols).toList())
            }
            ConverterType.Temperature -> {
                ConverterUnitsModel(
                    context.resources.getStringArray(R.array.temperature_unit).toList(),
                    context.resources.getStringArray(R.array.temperature_symbols).toList())
            }
            ConverterType.Volume -> {
                ConverterUnitsModel(
                    context.resources.getStringArray(R.array.volume_unit).toList(),
                    context.resources.getStringArray(R.array.volume_symbols).toList())
            }
            ConverterType.Area -> {
                ConverterUnitsModel(
                    context.resources.getStringArray(R.array.area_unit).toList(),
                    context.resources.getStringArray(R.array.area_symbols).toList())
            }
            ConverterType.Frequency -> {
                ConverterUnitsModel(
                    context.resources.getStringArray(R.array.frequency_unit).toList(),
                    context.resources.getStringArray(R.array.frequency_symbols).toList())
            }
            ConverterType.Data -> {
                ConverterUnitsModel(
                    context.resources.getStringArray(R.array.data_unit).toList(),
                    context.resources.getStringArray(R.array.data_symbols).toList())
            }

            else -> {
                getUnitModel(ConverterType.Weight)
            }
        }
    }
}