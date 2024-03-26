package com.cobaltumapps.simplecalculator.references

import android.util.Log
private const val DEBUG_TAG = "History"

open class History {
    private val vault = ""
}

class HistoryWriter: History() {

    fun read(history: HistoryData): HistoryData{
        Log.i(DEBUG_TAG,"\n" +
                "history object: $history\n" +
                "input expression: \"${history.inputExpression}\"\n" +
                "result expression: \"${history.resultExpression}\"")
        return history
    }

    fun write(history: HistoryData){
        TODO("Реализовать запись истории в хранилище")
    }
    fun clear(){
        TODO("Реализовать очистку всей истории")
    }

}
data class HistoryData(
    var inputExpression: String,
    var resultExpression: String
)
