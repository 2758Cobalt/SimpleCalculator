package com.cobaltumapps.simplecalculator.v15.calculator.services.expression_manager

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.preferences.SharedKeys
import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression
import com.cobaltumapps.simplecalculator.v15.calculator.services.expression_manager.interfaces.ExpressionLoaderListener

class ExpressionLoader(
    private val sharedPreferences: SharedPreferences,
    private var loaderListener: ExpressionLoaderListener? = null
): ExpressionLoaderListener {
    override fun loadExpression(): Expression {
        return Expression(
            with(sharedPreferences) {
                getString(SharedKeys.inputExpressionKey, "")
            }!!
        )

    }
}