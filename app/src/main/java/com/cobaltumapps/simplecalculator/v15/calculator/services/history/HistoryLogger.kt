package com.cobaltumapps.simplecalculator.v15.calculator.services.history

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData

class HistoryLogger: HistoryController {
    private val msgPrefix = javaClass.simpleName

    override fun addExpressionItem(historyData: HistoryData) {
        Log.d(LOG_TAG, "$msgPrefix: addExpressionItem:" +
                "\nInstance: $historyData" +
                "\nExpression: ${historyData.expression}" +
                "\nResult: ${historyData.result}")
    }

    override fun updateExpressionItem(historyData: HistoryData, pos: Int) {
        Log.d(LOG_TAG, "$msgPrefix: updateExpressionItem" +
                "\nPosition: $pos" +
                "\nInstance: $historyData" +
                "\nExpression: ${historyData.expression}" +
                "\nResult: ${historyData.result}")
    }

    override fun removeExpressionItem(index: Int) {
        Log.d(LOG_TAG, "$msgPrefix: removeExpressionItem:" +
                "\nIndex: $index")
    }

    override fun clearExpressionItem() {
        Log.d(LOG_TAG, "$msgPrefix: clearExpressionItem")
    }

    companion object {
        const val LOG_TAG = "SC_ServiceHistoryLogTag"
    }
}