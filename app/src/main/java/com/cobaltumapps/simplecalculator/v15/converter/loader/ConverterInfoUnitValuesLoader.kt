package com.cobaltumapps.simplecalculator.v15.converter.loader

import com.cobaltumapps.simplecalculator.v15.converter.deprecated.ConvertersValues
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoUnitValuesLoader

class ConverterInfoUnitValuesLoader: InfoUnitValuesLoader {
    override fun getValuesToConvert(converterType: ConverterType): Array<Array<Double>> {
        return when(converterType) {
            ConverterType.Weight -> ConvertersValues.weightValues
            ConverterType.Length -> ConvertersValues.lengthValues
            ConverterType.Time -> ConvertersValues.timeValues
            ConverterType.Speed -> ConvertersValues.speedValues
            ConverterType.Volume -> ConvertersValues.volumeValues
            ConverterType.Area -> ConvertersValues.areaValues
            ConverterType.Frequency -> ConvertersValues.frequencyValues
            ConverterType.Data -> ConvertersValues.dataValues
            else -> {
                getValuesToConvert(ConverterType.Weight)
            }
        }
    }
}