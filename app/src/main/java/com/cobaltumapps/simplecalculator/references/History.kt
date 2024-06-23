package com.cobaltumapps.simplecalculator.references

import android.util.Log

open class History {
    //Ключ к хранилищу
    private val vaultKey = "SC_HistoryVault"
}

class HistoryWriter: History() {

    fun read(history: HistoryData): HistoryData{
        Log.i(LogTags.LOG_HISTORY,"\n" +
                "history object: $history\n" +
                "input expression: \"${history.inputExpression}\"\n" +
                "result expression: \"${history.resultExpression}\"")
        return history
    }

    fun save(history: HistoryData){
        TODO("Реализовать сохранение записи в историю записей")
    }

    fun clear(){
        TODO("Реализовать очистку всех записей истории")
    }

    fun edit(entryId: Int){
        TODO("Реализовать редактирование записи истории")
    }

}
data class HistoryData(
    var inputExpression: String,
    var resultExpression: String
)
