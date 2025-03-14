package com.cobaltumapps.simplecalculator.v15.converter.loggers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.constants.LogTags
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterData
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoLoaderListener

class ConverterPageLogger: InfoLoaderListener {
    override fun updateConverterData(converterData: ConverterData) {
        Log.i(
            LogTags.converterPageTag,
            "Received a converter data:\n" +
                    "Title: ${converterData.converterPageData.title}\n" +
                    "DrawableId: ${converterData.converterPageData.drawable}\n" +
                    "UnitsDataId: ${converterData.converterUnitsModel}"
        )
    }
}