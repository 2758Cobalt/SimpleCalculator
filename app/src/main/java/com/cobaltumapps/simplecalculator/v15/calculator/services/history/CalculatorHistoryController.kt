package com.cobaltumapps.simplecalculator.v15.calculator.services.history

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

/** Контроллер, управляющий адаптера отображения списка и контроллера базы данных, для сохранения и удаления записей в БД */
class CalculatorHistoryController(private var listener: HistoryController? = null): HistoryController {

    override fun addHistoryItem(history: History) {
        listener?.addHistoryItem(history)
    }

    override fun updateHistoryItem(history: History) {
        listener?.updateHistoryItem(history)
    }

    override fun deleteHistoryItem(history: History) {
        listener?.deleteHistoryItem(history)
    }
}