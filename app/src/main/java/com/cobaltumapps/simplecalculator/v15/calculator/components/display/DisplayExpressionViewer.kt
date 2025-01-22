package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayAngleViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayExpressionViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayMemoryViewer
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

class DisplayExpressionViewer(private val context: Context): DisplayExpressionViewer, DisplayMemoryViewer,
    DisplayAngleViewer {
    private var displayBinding: FragmentDisplayBinding? = null

    fun setNewDisplayBinding(binding: FragmentDisplayBinding) {
        displayBinding = binding
    }

    override fun setExpressionField(newExpression: String) {
        displayBinding?.displayExpressionField?.text = newExpression
    }

    override fun setResultField(newResult: String) {
        displayBinding?.displayResultField?.text = newResult
    }

    override fun setAngleField(angleMode: AngleMode) {
        displayBinding?.displayAngleModeField?.text = angleMode.name
    }

    override fun setMemoryField(memoryValue: Number) {
        displayBinding?.displayMemoryField?.text = getString(context, R.string.display_memory_n).format(memoryValue.toString())
    }

}