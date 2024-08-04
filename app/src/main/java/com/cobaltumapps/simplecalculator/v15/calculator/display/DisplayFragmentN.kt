package com.cobaltumapps.simplecalculator.v15.calculator.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayNBinding
import com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces.DisplayManager

class DisplayFragmentN: Fragment(), DisplayManager {
    private lateinit var binding: FragmentDisplayNBinding
    private val angleModeManager = AngleModeManager()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDisplayNBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMemoryField(52.502)
    }

    // Устанавливаем значение любого числового типа в поле памяти
    override fun setMemoryField(newValueMemory: Number) {
        binding.displayMemoryField.text = getString(R.string.display_memory_n).format(newValueMemory.toString())
    }

    // Устанавливает перевод угла в нужный тип
    override fun setRadAngleMode(isEnabled: Boolean) {
        binding.displayAngleModeField.text = angleModeManager.setRadAngleMode(isEnabled)
    }

    // Устанавливаем значение любого числового типа в поле выражения
    override fun setExpressionField(newExpression: Number) {
        binding.displayExpressionField.text = newExpression.toString()
    }
}