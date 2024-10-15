package com.cobaltumapps.simplecalculator.v15.calculator.services.expression_manager

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.calculator.services.expression_manager.interfaces.ExpressionSaverListener

class ExpressionSaver(
    sharedPreferences: SharedPreferences,
    private var saverListener: ExpressionSaverListener? = null
) : ExpressionSaverListener {
    override fun saveExpression() {
    }
}