package com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces

import android.graphics.drawable.Drawable
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType

interface InfoDrawableLoader {
    fun getDrawable(converterType: ConverterType): Drawable?
}