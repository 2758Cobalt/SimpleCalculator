package com.cobaltumapps.simplecalculator.v15.fragments.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayNBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayComponent
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

class DisplayFragmentN: DisplayComponent()  {
    private val binding by lazy { FragmentDisplayNBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        displayAnimator?.setNewBinding(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMemoryField(0)
        binding.displayResultField.pivotY = 100f
    }

    override fun setAngleField(angleMode: AngleMode) {
        binding.displayAngleModeField.text = angleMode.name
    }

    // Устанавливаем значение любого числового типа в поле памяти
    override fun setMemoryField(memoryValue: Number) {
        binding.displayMemoryField.text = getString(R.string.display_memory_n).format(memoryValue.toString())
    }

    // Устанавливаем значение любого числового типа в поле выражения
    override fun setExpressionField(newExpression: String) {
        binding.displayExpressionField.text = newExpression
    }

    // Устанавливаем значение в поле с результатом
    override fun setResultField(newResult: String) {
        binding.displayResultField.text = "= $newResult"
    }

}

