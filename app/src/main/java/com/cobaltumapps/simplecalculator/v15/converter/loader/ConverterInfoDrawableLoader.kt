package com.cobaltumapps.simplecalculator.v15.converter.loader

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoDrawableLoader

class ConverterInfoDrawableLoader(private val applicationContext: Context): InfoDrawableLoader {
    override fun getDrawable(converterType: ConverterType): Drawable? {
        return with(applicationContext) {
            when(converterType) {
                ConverterType.Weight -> ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_weight,
                    applicationContext.theme
                )
                ConverterType.Length -> ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_length,
                    applicationContext.theme
                )
                ConverterType.Time -> ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_time,
                    applicationContext.theme
                )
                ConverterType.Speed -> ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_speed,
                    applicationContext.theme
                )
                ConverterType.Temperature -> ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_temperatures,
                    applicationContext.theme
                )
                ConverterType.Volume -> ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_volume,
                    applicationContext.theme
                )
                ConverterType.Area -> ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_area,
                    applicationContext.theme
                )
                ConverterType.Frequency -> ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_frequency,
                    applicationContext.theme
                )
                ConverterType.Data -> ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_data,
                    applicationContext.theme
                )
                else -> getDrawable(ConverterType.Weight)
            }
        }
    }
}