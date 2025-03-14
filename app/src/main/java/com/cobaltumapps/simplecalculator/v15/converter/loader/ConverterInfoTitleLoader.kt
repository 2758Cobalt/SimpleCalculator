package com.cobaltumapps.simplecalculator.v15.converter.loader

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoTitleLoader

class ConverterInfoTitleLoader(private val applicationContext: Context): InfoTitleLoader {
    override fun getTitle(converterType: ConverterType): String {
        return with(applicationContext) {
            when(converterType) {
                ConverterType.Weight -> applicationContext.resources.getString(R.string.converter_weight)
                ConverterType.Length -> applicationContext.resources.getString(R.string.converter_length)
                ConverterType.Time -> applicationContext.resources.getString(R.string.converter_time)
                ConverterType.Speed -> applicationContext.resources.getString(R.string.converter_speed)
                ConverterType.Temperature -> applicationContext.resources.getString(R.string.converter_temperature)
                ConverterType.Volume -> applicationContext.resources.getString(R.string.converter_volume)
                ConverterType.Area -> applicationContext.resources.getString(R.string.converter_area)
                ConverterType.Frequency -> applicationContext.resources.getString(R.string.converter_frequency)
                ConverterType.Data -> applicationContext.resources.getString(R.string.converter_data)
                else -> getTitle(ConverterType.Weight)
            }
        }
    }
}