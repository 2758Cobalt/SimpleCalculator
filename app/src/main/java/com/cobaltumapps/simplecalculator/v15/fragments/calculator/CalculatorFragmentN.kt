package com.cobaltumapps.simplecalculator.v15.fragments.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.activities.CalculatorListener
import com.cobaltumapps.simplecalculator.activities.MainActivityListener
import com.cobaltumapps.simplecalculator.databinding.FragmentCalculatorNBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.CalculatorController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.cobaltumapps.simplecalculator.v15.calculator.components.mediator.MediatorController
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering.EngineeringSwiper
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.HistoryControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.system.CalculatorCore
import com.cobaltumapps.simplecalculator.v15.constants.UserPreferences
import com.cobaltumapps.simplecalculator.v15.fragments.HistoryDisplayFragment
import com.cobaltumapps.simplecalculator.v15.fragments.display.DisplayFragmentN
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.EngineeringNumpadFragmentN
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.NumpadFragmentN
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.EngineeringBottomBehaviorListener
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.NumpadBottomBehaviorListener
import com.google.android.material.bottomsheet.BottomSheetBehavior

/** Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора */
class CalculatorFragmentN(
    private val calculatorListener: CalculatorListener
): Fragment(), MainActivityListener, NumpadBottomBehaviorListener,
    EngineeringBottomBehaviorListener {

    private val binding by lazy { FragmentCalculatorNBinding.inflate(layoutInflater) }
    private val historyDisplayFragment by lazy { HistoryDisplayFragment(mediatorController) }

    // Fragments
    private val displayFragment = DisplayFragmentN()
    private val numpadFragment = NumpadFragmentN(this)
    private val engineeringFragment = EngineeringNumpadFragmentN(this)

    // Controllers
    private val numpadController = NumpadController()
    private val engineeringController = EngineeringController()

    private val engineeringSwiper by lazy { EngineeringSwiper(binding.calculatorBackSpaceIcon) }

    private val calculatorCoreInstance = CalculatorCore()
    private val calculatorController by lazy { CalculatorController(calculatorCoreInstance) }

    private val mediatorController = MediatorController()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            mediatorController.apply {
                displayController = displayFragment.displayController
                numpadController = this@CalculatorFragmentN.numpadController
                engNumpadController = engineeringController
                historyService = HistoryControllerImpl(historyDisplayFragment.historyAdapter)
                memoryService = MemoryControllerImpl()

                // System
                calculatorController = this@CalculatorFragmentN.calculatorController
            }

            numpadFragment.setNewKeyboardController(numpadController)
            engineeringFragment.setNewKeyboardController(engineeringController)

            // Setup mediator
            numpadController.setNewMediator(mediatorController)
            engineeringController.setNewMediator(mediatorController)

            // Backspace icon
            calculatorBackSpaceIcon.apply {
                isVisible = false
                alpha = 0f
                setOnClickListener {
                    mediatorController.handleSpecialFunctionClick(KeyboardSpecialFunction.Backspace)
                }
            }

            calculatorConvertersIcon.setOnClickListener { calculatorListener.goConverters() }
            calculatorSettingsIcon.setOnClickListener { calculatorListener.goSettings() }

            // Display fragments
            parentFragmentManager.commit {
                replace(calculatorDisplayHolder.id, displayFragment)
                replace(numpadHolder.id, numpadFragment)
                replace(engineeringNumpadHolder.id, engineeringFragment)
                replace(calculatorHistoryHolder.id, historyDisplayFragment)
            }
        }
    }

    /** Метод, который вызывается при смене состояния BottomSheet Numpad-клавиатуры */
    override fun onStateNumpadChanged(bottomSheet: View, newState: Int) {
        historyDisplayFragment.onStateNumpadChanged(bottomSheet, newState)
        when(newState) {
            BottomSheetBehavior.STATE_EXPANDED -> engineeringFragment.setActiveKeyboard(true)
            BottomSheetBehavior.STATE_DRAGGING -> engineeringFragment.setActiveKeyboard(false)
        }
    }

    /** Метод, который вызывается при смене состояния BottomSheet EngineeringNumpad-клавиатуры */
    override fun onStateEngNumpadChanged(bottomSheet: View, newState: Int) {
        historyDisplayFragment.onStateEngNumpadChanged(bottomSheet, newState)
        engineeringSwiper.onStateEngNumpadChanged(bottomSheet, newState)
    }

    /** Метод, обновляющий предпочтения (нгстройки) */
    override fun updatePreferences(newUserPreferences: UserPreferences) {
    }

}