package com.cobaltumapps.simplecalculator.v15.calculator.services.history

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.models.Expression

// Класс, который надблюдает за списком истории
class HistoryWatcher: HistoryWatcherListener {
    private var historyListExpressions: MutableList<Expression>? = null
    private var historyWatcherListener: HistoryWatcherListener? = null

    // Назначаем список за которым следим
    fun setWatchList(historyList: MutableList<Expression>) {
        this.historyListExpressions = historyList
    }

    override fun watch(historyList: MutableList<Expression>, watched: ((historyIsNotEmpty: Boolean, historySize: Int) -> Unit)?) {
        // Просматриваем не пустой ли список
        val historyFullness = historyIsNotEmpty(historyList)
        val historySize = getHistorySize(historyList)

        Log.w(TAG, if (historyFullness) "История не пуста - показать" else "История пуста - скрыть")
        watched?.invoke(historyFullness, historySize)
    }

    companion object {
        const val TAG = "SC_HistoryWatcher"
    }


}

interface HistoryWatcherListener {
    fun watch(historyList: MutableList<Expression>, watched: ((
            historyIsNotEmpty: Boolean,
            historySize: Int
            ) -> Unit)? )

    fun historyIsNotEmpty(historyList: MutableList<Expression>): Boolean {
        return historyList.isNotEmpty()
    }

    fun getHistorySize(historyList: MutableList<Expression>): Int {
        return historyList.size
    }

    fun historyIsCleared(): Boolean {
        return true
    }
}