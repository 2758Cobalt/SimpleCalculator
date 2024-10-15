package com.cobaltumapps.simplecalculator.v15.calculator.services.expression_manager

import android.content.Context
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.calculator.services.expression_manager.interfaces.ExpressionStorageListener

class ExpressionStorageManager(context: Context): ExpressionStorageListener {
    private val sharedPreferences by lazy { context.getSharedPreferences(ConstantsCalculator.vault,Context.MODE_PRIVATE) }

    private val saver = ExpressionSaver(sharedPreferences)
    private val loader = ExpressionLoader(sharedPreferences)

    /** Вызывает метод сохранения класса ExpressionSaver */
    override fun save() {
        saver.saveExpression()
    }

    /** Вызывает метод загрузки класса ExpressionLoader */
    override fun load() {
        loader.loadExpression()
    }
}

