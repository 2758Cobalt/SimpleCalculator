package com.cobaltumapps.simplecalculator.v15.fragments.calculator

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.databinding.FragmentCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.CalculatorController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.cobaltumapps.simplecalculator.v15.calculator.components.mediator.MediatorController
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryStorageControllerSingleton
import com.cobaltumapps.simplecalculator.v15.calculator.services.tallback.VibrationSingleton
import com.cobaltumapps.simplecalculator.v15.calculator.system.CalculatorCore
import com.cobaltumapps.simplecalculator.v15.fragments.display.DisplayFragment
import com.cobaltumapps.simplecalculator.v15.fragments.history.CalculatorHistoryDisplayFragment
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.EngineeringNumpadFragment
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.NumpadFragment
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.EngineeringBottomBehaviorListener
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.NumpadBottomBehaviorListener
import com.cobaltumapps.simplecalculator.v15.preferences.PreferencesKeys
import com.cobaltumapps.simplecalculator.v15.references.ConstantsCalculator
import com.google.android.material.bottomsheet.BottomSheetBehavior

/** Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора */
class CalculatorPageFragment(
    var calculatorNavigationListener: CalculatorNavigationListener? = null
): Fragment(), NumpadBottomBehaviorListener,
    EngineeringBottomBehaviorListener {

    private val binding by lazy { FragmentCalculatorBinding.inflate(layoutInflater) }
    private val sharedPreferences by lazy { requireContext().getSharedPreferences(ConstantsCalculator.vaultPreferences, Context.MODE_PRIVATE) }

    // Fragments
    private val displayFragment by lazy { DisplayFragment() }
    private val numpadFragment by lazy { NumpadFragment(numpadController, this) }
    private val engineeringFragment by lazy { EngineeringNumpadFragment(engineeringController, this) }
    private val calculatorHistoryDisplayFragment by lazy { CalculatorHistoryDisplayFragment(mediatorController) }

    // Controllers
    private val numpadController = NumpadController()
    private val engineeringController = EngineeringController()

    private val calculatorController by lazy { CalculatorController(calculatorCoreInstance) }

    private val mediatorController = MediatorController()

    // Instance
    private val calculatorCoreInstance = CalculatorCore()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        VibrationSingleton.getInstance(
            requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        )

        MemoryStorageControllerSingleton.getInstance()

        binding.apply {

            // Setup the keyboard controllers
            numpadController.setNewMediator(mediatorController)
            engineeringController.setNewMediator(mediatorController)

            // Setup the mediator controller
            mediatorController.apply {
                displayController = displayFragment.displayController
                numpadController = this@CalculatorPageFragment.numpadController
                engNumpadController = engineeringController

                historyService = calculatorHistoryDisplayFragment.calculatorHistoryController

                calculatorController = this@CalculatorPageFragment.calculatorController
            }
            // Setup the controllers
            numpadFragment.setNewKeyboardController(numpadController)
            engineeringFragment.setNewKeyboardController(engineeringController)

            // The icons OnClickListeners
            calculatorNavigationMenuIcon.setOnClickListener { calculatorNavigationListener?.openNavigationMenu() }
            calculatorSettingsIcon.setOnClickListener { calculatorNavigationListener?.openSettings() }

            // Display fragments
            if (savedInstanceState == null) {
                // Добавляет фрагменты, если родитель не был пересоздан
                parentFragmentManager.commit {
                    add(calculatorDisplayHolder.id, displayFragment, DisplayFragment.FRAG_TAG)
                    add(numpadHolder.id, numpadFragment, NumpadFragment.TAG)
                    add(engineeringNumpadHolder.id, engineeringFragment, EngineeringNumpadFragment.TAG)
                    add(calculatorHistoryHolder.id, calculatorHistoryDisplayFragment)
                }
            }
            else {
                // Заменяет старые фрагменты на новые, когда родитель был пересоздан
                parentFragmentManager.commit {
                    replace(calculatorDisplayHolder.id, displayFragment, DisplayFragment.FRAG_TAG)
                    replace(numpadHolder.id, numpadFragment, NumpadFragment.TAG)
                    replace(engineeringNumpadHolder.id, engineeringFragment, EngineeringNumpadFragment.TAG)
                    replace(calculatorHistoryHolder.id, calculatorHistoryDisplayFragment)
                }
            }
            mediatorController.getLastExpression()
        }
    }

    override fun onDestroyView() {
        sharedPreferences.edit {
            putString(PreferencesKeys.KEY_LAST_EXPRESSION, calculatorController.getUserExpression().getExpression())
        }
        super.onDestroyView()
    }

    /** Метод, который вызывается при смене состояния BottomSheet Numpad-клавиатуры */
    override fun onStateNumpadChanged(bottomSheet: View, newState: Int) {
        calculatorHistoryDisplayFragment.onStateNumpadChanged(bottomSheet, newState)
        when(newState) {
            BottomSheetBehavior.STATE_EXPANDED -> engineeringFragment.setActiveKeyboard(true)
            BottomSheetBehavior.STATE_DRAGGING -> engineeringFragment.setActiveKeyboard(false)
        }
    }

    /** Метод, который вызывается при смене состояния BottomSheet EngineeringNumpad-клавиатуры */
    override fun onStateEngNumpadChanged(bottomSheet: View, newState: Int) { }

    /** Метод, который вызывается при состояния слайда контейнера BottomSheet EngineeringNumpad-клавиатуры */
    override fun onSlideEngNumpad(bottomSheet: View, slideOffset: Float) {
        with(binding) {
            numpadHolder.scaleX = 1f - (slideOffset / NUMPAD_FADING_MULTIPLIER)
            numpadHolder.scaleY = 1f - (slideOffset / NUMPAD_FADING_MULTIPLIER)
        }
    }

    companion object {
        const val FRAG_TAG = "CalculatorPageFragmentTag"
        const val NUMPAD_FADING_MULTIPLIER = 25f
    }
}