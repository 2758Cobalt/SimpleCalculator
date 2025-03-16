package com.cobaltumapps.simplecalculator.v15.converter.loggers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.constants.LogTags
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterLoaderData
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoLoaderListener

class ConverterPageLogger: InfoLoaderListener {
    override fun updateConverterData(converterLoaderData: ConverterLoaderData) {
        Log.i(
            LogTags.converterPageTag,
            "Received a converter data:\n" +
                    "Title: ${converterLoaderData.converterPageData.title}\n" +
                    "DrawableId: ${converterLoaderData.converterPageData.drawable}\n" +
                    "UnitsDataId: ${converterLoaderData.converterUnitsModel}"
        )
    }
}