package com.cobaltumapps.simplecalculator.v15.converter.loader

import android.content.Context
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterLoaderData
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
    private val converterInfoUnitValuesLoader = ConverterInfoUnitValuesLoader()

    override fun getConverterData(converterType: ConverterType): ConverterLoaderData {
        val unitTitle = converterInfoTitleLoader.getTitle(converterType)
        val unitDrawable = converterInfoDrawableLoader.getDrawable(converterType)
        val unitResourcesList = converterInfoUnitLoader.getUnitModel(converterType)
        val unitValues = converterInfoUnitValuesLoader.getValuesToConvert(converterType)

        return ConverterLoaderData(
            ConverterPageData(unitTitle, unitDrawable),
            ConverterUnitsModel(
                unitResourcesList.unitsNameList,
                unitResourcesList.unitsSpecialSymbolsList,
                unitValues
            )
        )
    }
}

