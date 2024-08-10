package com.cobaltumapps.simplecalculator.v15.calculator.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayNBinding
import com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces.DisplayManager
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

class DisplayFragmentN: Fragment(), DisplayManager {
    private lateinit var binding: FragmentDisplayNBinding

    private val angleModeManager = AngleModeManager()
    private lateinit var displayAnimator: DisplayAnimator

    // Логика
    private var isResult = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDisplayNBinding.inflate(layoutInflater)
        displayAnimator = DisplayAnimator(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMemoryField(52.502)

        binding.displayResultField.pivotY = 100f


        displayAnimator.playAnimationIsResult(false)
    }

    // Устанавливаем значение любого числового типа в поле выражения
    override fun setExpressionField(newExpression: String) {
        binding.displayExpressionField.text = newExpression
        if (isResult)
            displayAnimator.playAnimationIsResult(false)
        isResult = false
    }

    // Устанавливаем значение любого числового типа в поле выражения
    override fun setCalculatedExpression(result: String) {
        binding.displayResultField.text = "= $result"
        displayAnimator.playAnimationIsResult(true)
        isResult = true
    }

    override fun clearDisplay() {
        displayAnimator.playClearAnimation {
            binding.displayExpressionField.text = ""
        }

        if (isResult)
            displayAnimator.playAnimationIsResult(false)

        isResult = false
    }

    // Устанавливаем значение любого числового типа в поле памяти
    override fun setMemoryField(newValueMemory: Number) {
        binding.displayMemoryField.text = getString(R.string.display_memory_n).format(newValueMemory.toString())
    }

    // Устанавливает перевод угла в нужный тип
    override fun setAngleMode(angleMode: AngleMode) {
        binding.displayAngleModeField.text = angleModeManager.setRadAngleMode(angleMode)
    }

}

