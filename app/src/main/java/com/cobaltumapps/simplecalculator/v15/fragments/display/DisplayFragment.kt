package com.cobaltumapps.simplecalculator.v15.fragments.display

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayComponent
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

// Фрагмент, содержащий дисплей калькулятора. Является наследником Display-компонента
class DisplayFragment: DisplayComponent()  {
    private val binding by lazy { FragmentDisplayBinding.inflate(layoutInflater) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        displayAnimator.setNewBinding(binding)

        binding.apply {
            displayResultField.apply {
                pivotX = 100f
                pivotY = 100f
            }

            displayExpressionField.inputType = InputType.TYPE_NULL
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMemoryField(0)

        /* Если состояние пересоздано - восстанавливаем значения в полях */
        savedInstanceState?.apply {
            binding.displayExpressionField.setText(getString(KEY_DISPLAY_FIELD, ""))
            binding.displayResultField.text = getString(KEY_RESULT_FIELD, "")
            binding.displayMemoryField.text = getString(KEY_MEMORY_FIELD, "")
            binding.displayAngleModeField.text = getString(KEY_ANGLE_FIELD, "")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(binding) {
            with(outState) {
                putString(KEY_DISPLAY_FIELD, displayExpressionField.text.toString())
                putString(KEY_RESULT_FIELD, displayResultField.text.toString())
                putString(KEY_MEMORY_FIELD, displayMemoryField.text.toString())
                putString(KEY_ANGLE_FIELD, displayAngleModeField.text.toString())
            }
        }
    }

    // Установка типа угла
    override fun setAngleField(angleMode: AngleMode) {
        binding.displayAngleModeField.text = angleMode.name
    }

    // Установка любого числового значения в поле памяти
    override fun setMemoryField(memoryValue: Number) {
        binding.displayMemoryField.text = getString(R.string.display_memory_n).format(memoryValue.toString())
    }

    // Установка любого числового значения в поле выражения
    override fun setExpressionField(newExpression: String) {
        binding.displayExpressionField.setText(newExpression)
    }

    // Установка значение в поле с результатом
    override fun setResultField(newResult: String) {
        binding.displayResultField.text = newResult
    }

    companion object {
        const val KEY_DISPLAY_FIELD = "SC_SavedStateDisplayField"
        const val KEY_RESULT_FIELD = "SC_SavedStateResultField"
        const val KEY_MEMORY_FIELD = "SC_SavedStateMemoryField"
        const val KEY_ANGLE_FIELD = "SC_SavedStateAngleField"
        const val TAG = "SC_DisplayFragmentTag"
    }

}

