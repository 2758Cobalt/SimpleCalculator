package com.cobaltumapps.simplecalculator.v15.fragments.display

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.view.isVisible
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayComponent
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.DisplayPreferencesManager
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName
import com.cobaltumapps.simplecalculator.v15.calculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.preferences.PreferencesKeys

/** Фрагмент, содержащий дисплей калькулятора. Является наследником Display-компонента */
class DisplayFragment: DisplayComponent()  {
    private val binding by lazy { FragmentDisplayBinding.inflate(layoutInflater) }

    private lateinit var sharedPreferences: SharedPreferences
    private val displayPreferencesManager by lazy { DisplayPreferencesManager(sharedPreferences) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
         sharedPreferences = context.getSharedPreferences(ConstantsCalculator.vaultPreferences, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        displayAnimator.setNewBinding(binding)

        binding.apply {
            displayResultField.apply {
                pivotX = 100f
                pivotY = 100f
                isVisible = false
            }

            displayExpressionField.inputType = InputType.TYPE_NULL

            displayAnimator.playHiddenResultAnim {
                displayResultField.isVisible = true
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayPreferencesManager.getPreferenceCondition(OptionName.KeepLastRecord) { condition ->
            if (condition)
                loadLastExpression()
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
        binding.displayMemoryField.text = getString(R.string.display_memory).format(memoryValue.toString())
    }

    // Установка любого числового значения в поле выражения
    override fun setExpressionField(newExpression: String) {
        binding.displayExpressionField.text = newExpression
        binding.displayExpressionField.requestLayout()
    }

    // Установка значение в поле с результатом
    override fun setResultField(newResult: String) {
        binding.displayResultField.text = newResult
    }

    private fun loadLastExpression() {
        val getExpression = sharedPreferences.getString(PreferencesKeys.keyLastExpression, "")

        if (getExpression != null)
            binding.displayExpressionField.text = getExpression
    }

    override fun onStop() {
        super.onStop()
        sharedPreferences.edit {
            putString(PreferencesKeys.keyLastExpression, binding.displayExpressionField.text.toString())
        }
    }

    companion object {
        const val KEY_DISPLAY_FIELD = "SC_SavedStateDisplayField"
        const val KEY_RESULT_FIELD = "SC_SavedStateResultField"
        const val KEY_MEMORY_FIELD = "SC_SavedStateMemoryField"
        const val KEY_ANGLE_FIELD = "SC_SavedStateAngleField"
        const val TAG = "SC_DisplayFragmentTag"
    }

}

