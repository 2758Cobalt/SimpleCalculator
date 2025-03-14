package com.cobaltumapps.simplecalculator.v15.converter.loader

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitsModel
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoUnitModelLoader

class ConverterInfoUnitLoader(private val applicationContext: Context): InfoUnitModelLoader {
    override fun getUnitModel(converterType: ConverterType): ConverterUnitsModel {
        return when(converterType) {
            ConverterType.Weight -> {
                ConverterUnitsModel(
                    applicationContext.resources.getStringArray(R.array.weight_unit).toList(),
                    applicationContext.resources.getStringArray(R.array.weight_symbols).toList()
                )
            }

            ConverterType.Length -> {
                ConverterUnitsModel(
                    applicationContext.resources.getStringArray(R.array.length_unit).toList(),
                    applicationContext.resources.getStringArray(R.array.length_symbols).toList()
                )
            }
            ConverterType.Time -> {
                ConverterUnitsModel(
                    applicationContext.resources.getStringArray(R.array.time_unit).toList(),
                    applicationContext.resources.getStringArray(R.array.time_symbols).toList()
                )
            }
            ConverterType.Speed -> {
                ConverterUnitsModel(
                    applicationContext.resources.getStringArray(R.array.speed_unit).toList(),
                    applicationContext.resources.getStringArray(R.array.speed_symbols).toList()
                )
            }
            ConverterType.Temperature -> {
                ConverterUnitsModel(
                    applicationContext.resources.getStringArray(R.array.temperature_unit).toList(),
                    applicationContext.resources.getStringArray(R.array.temperature_symbols)
                        .toList()
                )
            }
            ConverterType.Volume -> {
                ConverterUnitsModel(
                    applicationContext.resources.getStringArray(R.array.volume_unit).toList(),
                    applicationContext.resources.getStringArray(R.array.volume_symbols).toList()
                )
            }
            ConverterType.Area -> {
                ConverterUnitsModel(
                    applicationContext.resources.getStringArray(R.array.area_unit).toList(),
                    applicationContext.resources.getStringArray(R.array.area_symbols).toList()
                )
            }
            ConverterType.Frequency -> {
                ConverterUnitsModel(
                    applicationContext.resources.getStringArray(R.array.frequency_unit).toList(),
                    applicationContext.resources.getStringArray(R.array.frequency_symbols).toList()
                )
            }
            ConverterType.Data -> {
                ConverterUnitsModel(
                    applicationContext.resources.getStringArray(R.array.data_unit).toList(),
                    applicationContext.resources.getStringArray(R.array.data_symbols).toList()
                )
            }
            else -> getUnitModel(ConverterType.Weight)
        }
    }
}