package com.cobaltumapps.simplecalculator.v15.converter.loader

import android.content.Context
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterData
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterPageData
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitsModel
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoLoader

class ConverterInfoLoader(
    private val applicationContext: Context
): InfoLoader {
    private val converterInfoTitleLoader = ConverterInfoTitleLoader(applicationContext)
    private val converterInfoDrawableLoader = ConverterInfoDrawableLoader(applicationContext)
    private val converterInfoUnitLoader = ConverterInfoUnitLoader(applicationContext)

    override fun getConverterData(converterType: ConverterType): ConverterData {
        val unitTitle = converterInfoTitleLoader.getTitle(converterType)
        val unitDrawable = converterInfoDrawableLoader.getDrawable(converterType)
        val unitResourcesList = converterInfoUnitLoader.getUnitModel(converterType)

        return ConverterData(
            ConverterPageData(unitTitle, unitDrawable),
            ConverterUnitsModel(
                unitResourcesList.unitsNameList,
                unitResourcesList.unitsSpecialSymbolsList
            )
        )
    }
}