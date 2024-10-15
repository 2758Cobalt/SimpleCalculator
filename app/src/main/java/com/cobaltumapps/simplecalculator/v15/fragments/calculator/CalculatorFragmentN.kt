package com.cobaltumapps.simplecalculator.v15.fragments.calculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.databinding.FragmentCalculatorNBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.CalculatorController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.cobaltumapps.simplecalculator.v15.calculator.components.mediator.MediatorController
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering.EngineeringSwiper
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.PreferencesManager
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.HistoryControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.system.CalculatorCore
import com.cobaltumapps.simplecalculator.v15.fragments.HistoryDisplayFragment
import com.cobaltumapps.simplecalculator.v15.fragments.display.DisplayFragmentN
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.EngineeringNumpadFragmentN
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.NumpadFragmentN
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.EngineeringBottomBehaviorListener
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.NumpadBottomBehaviorListener
import com.google.android.material.bottomsheet.BottomSheetBehavior

/** Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора */
class CalculatorFragmentN: Fragment(), NumpadBottomBehaviorListener,
    EngineeringBottomBehaviorListener {

    private val binding by lazy { FragmentCalculatorNBinding.inflate(layoutInflater) }
    private val historyDisplayFragment by lazy { HistoryDisplayFragment(mediatorController) }

    // Fragments
    private val displayFragment by lazy { DisplayFragmentN() }
    private val numpadFragment by lazy { NumpadFragmentN(numpadController, this) }
    private val engineeringFragment by lazy { EngineeringNumpadFragmentN(engineeringController, this) }

    // Controllers
    private val numpadController = NumpadController()
    private val engineeringController = EngineeringController()

    private val calculatorController by lazy { CalculatorController(calculatorCoreInstance) }
    private val mediatorController = MediatorController()

    // Instance
    private val engineeringSwiper by lazy { EngineeringSwiper(binding.calculatorBackSpaceIcon) }

    private val calculatorCoreInstance = CalculatorCore()

    private val preferencesManager by lazy { PreferencesManager(requireContext()) }

    private var calculatorNavigationListener: CalculatorNavigationListener? = null

    fun setCalculatorNavigationListener(listener: CalculatorNavigationListener?) {
        calculatorNavigationListener = listener
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CalculatorNavigationListener) {
            calculatorNavigationListener = context
        } else {
            throw RuntimeException("$context must implement CalculatorNavigationListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup the keyboard controllers
        numpadController.setNewMediator(mediatorController)
        engineeringController.setNewMediator(mediatorController)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            // Setup the mediator controller
            mediatorController.apply {
                displayController = displayFragment.displayController
                numpadController = this@CalculatorFragmentN.numpadController
                engNumpadController = engineeringController
                historyService = HistoryControllerImpl(historyDisplayFragment.historyAdapter)
                memoryService = MemoryControllerImpl()

                calculatorController = this@CalculatorFragmentN.calculatorController
            }

            // Setup the controllers
            numpadFragment.setNewKeyboardController(numpadController)
            engineeringFragment.setNewKeyboardController(engineeringController)

            // The backspace icon
            calculatorBackSpaceIcon.apply {
                alpha = 0f
                setOnClickListener {
                    mediatorController.handleOnClickSpecialFunction(KeyboardSpecialFunction.Backspace)
                }
            }

            calculatorConvertersIcon.setOnClickListener { calculatorNavigationListener?.goConverters() }
            calculatorSettingsIcon.setOnClickListener { preferencesManager.openPreferencesDialog() }

            // Add mediator as a updater listener
            preferencesManager.updaterListener = mediatorController

            // Load data from preferences
            val loadedConfig = preferencesManager.loadData()
            mediatorController.updatePreferences(loadedConfig)

            // Display fragments
            if (savedInstanceState == null) {
                parentFragmentManager.commit {
                    add(calculatorDisplayHolder.id, displayFragment, DisplayFragmentN.TAG)
                    add(numpadHolder.id, numpadFragment, NumpadFragmentN.TAG)
                    add(engineeringNumpadHolder.id, engineeringFragment, EngineeringNumpadFragmentN.TAG)
                    add(calculatorHistoryHolder.id, historyDisplayFragment)
                }
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
    }

    override fun onSlideEngNumpad(bottomSheet: View, slideOffset: Float) {
        val multiplier = 25f
        binding.apply {
            numpadHolder.scaleX = 1f - (slideOffset / multiplier)
            numpadHolder.scaleY = 1f - (slideOffset / multiplier)
        }

        engineeringSwiper.onSlideEngNumpad(bottomSheet, slideOffset)
    }

    companion object {
        const val TAG = "SC_CalculatorFragmentTag"
    }
}