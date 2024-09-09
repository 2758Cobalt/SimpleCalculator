package com.cobaltumapps.simplecalculator.v15.fragments.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.activities.CalculatorListener
import com.cobaltumapps.simplecalculator.activities.MainActivityListener
import com.cobaltumapps.simplecalculator.databinding.FragmentCalculatorNBinding
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.CalculatorControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayAnimator
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.cobaltumapps.simplecalculator.v15.calculator.components.mediator.MediatorImpl
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering.EngineeringSwiper
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.HistoryControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.HistoryStorageController
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.system.CalculatorCore
import com.cobaltumapps.simplecalculator.v15.constants.Properties
import com.cobaltumapps.simplecalculator.v15.constants.UserPreferences
import com.cobaltumapps.simplecalculator.v15.fragments.HistoryDisplayFragment
import com.cobaltumapps.simplecalculator.v15.fragments.display.DisplayFragmentN
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.EngineeringNumpadFragmentN
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.NumpadFragmentN
import com.google.android.material.bottomsheet.BottomSheetBehavior

// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class CalculatorFragmentN(private val calculatorListener: CalculatorListener): Fragment(), MainActivityListener {
    private val binding by lazy { FragmentCalculatorNBinding.inflate(layoutInflater) }

    private val historyDisplayFragment by lazy { HistoryDisplayFragment(mediatorImpl) }

    private val displayInstance = DisplayFragmentN()
    private val numpadInstance = NumpadFragmentN()

    private val engineeringNumpadInstance = EngineeringNumpadFragmentN()
    private val engineeringSwiper = EngineeringSwiper()

    private val numpadController = NumpadController()
    private val engineeringController = EngineeringController()

    private val displayControllerImpl = DisplayControllerImpl()

    private val calculatorControllerImpl = CalculatorControllerImpl()

    private val mediatorImpl = MediatorImpl()

    private val calculatorCoreInstance = CalculatorCore()

    private var isReduced = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediatorImpl.apply {
            displayController = displayControllerImpl
            numpadController = this@CalculatorFragmentN.numpadController
            engNumpadController = engineeringController
            calculatorController = calculatorControllerImpl
            historyService = HistoryControllerImpl(historyDisplayFragment.historyAdapter)
            memoryService = MemoryControllerImpl()

            historyService?.historyStorageController = HistoryStorageController()
        }

        calculatorControllerImpl.setNewCalculatorInstance(calculatorCoreInstance)

        numpadInstance.setNewKeyboardController(numpadController)
        engineeringNumpadInstance.setNewKeyboardController(engineeringController)

        displayControllerImpl.setNewDisplayInstance(displayInstance)
        displayControllerImpl.setNewDisplayAnimator(DisplayAnimator())

        numpadController.setNewMediator(mediatorImpl)
        engineeringController.setNewMediator(mediatorImpl)

        binding.apply {
            calculatorBackSpaceIcon.isVisible = false
            calculatorBackSpaceIcon.setOnClickListener {
                mediatorImpl.handleSpecialFunctionClick(KeyboardSpecialFunction.Backspace)
            }

            calculatorConvertersIcon.setOnClickListener { calculatorListener.goConverters() }
            calculatorSettingsIcon.setOnClickListener { calculatorListener.goSettings() }
            calculatorMinimizeIcon.setOnClickListener { reduceKeyboard() }
        }

        parentFragmentManager.commit {
            with(binding) {
                replace(calculatorDisplayHolder.id, displayInstance)
                replace(numpadHolder.id, numpadInstance)
                replace(engineeringNumpadHolder.id, engineeringNumpadInstance)
                replace(calculatorHistoryHolder.id, historyDisplayFragment)
            }
        }

        val bottomNumpad = BottomSheetBehavior.from(binding.numpadHolder)
        val bottomNumpadEngineering = BottomSheetBehavior.from(binding.engineeringNumpadHolder)

        bottomNumpad.state = BottomSheetBehavior.STATE_EXPANDED
        bottomNumpadEngineering.state = BottomSheetBehavior.STATE_HIDDEN

        bottomNumpad.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                    // Numpad развернут
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        historyDisplayFragment.hideHistory()
                        enableEngineeringKeyboard(true, bottomNumpadEngineering)
                    }

                    // Numpad закрыт
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        historyDisplayFragment.showHistory()
                    }

                    // Панель в процессе перетаскивания
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        enableEngineeringKeyboard(false, bottomNumpadEngineering)
                    }

                    // Панель в процессе фиксации состояния (когда юзер отпустил панель, и она сама себя фиксирует)
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }

                    // Панель скрыта
                    BottomSheetBehavior.STATE_HIDDEN -> { }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) { }
        })

        bottomNumpadEngineering.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                engineeringSwiper.engineeringIsEnable(binding.calculatorBackSpaceIcon,
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED ->  true
                        BottomSheetBehavior.STATE_COLLAPSED -> false
                        else -> false
                    })
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) { }
        })

    }

    // Включает/Выключает инженерную панель
    private fun enableEngineeringKeyboard(isEnabled: Boolean, bottomSheetBehavior: BottomSheetBehavior<FrameLayout>) {
        bottomSheetBehavior.isDraggable = isEnabled

        Animations.animatePropertyChange(
            binding.engineeringNumpadHolder,
            Properties.alpha,
            binding.engineeringNumpadHolder.alpha,
            if (isEnabled) 1f else 0f,
            300L)
    }

    override fun updatePreferences(newUserPreferences: UserPreferences) {
    }

    private fun reduceKeyboard() {
        isReduced = !isReduced
        with(binding) {
            coordinatorLayout.apply {
                pivotX = width.toFloat()
                pivotY = height.toFloat()

                scaleY =  if (isReduced) 0.75f else 1f
                scaleX = scaleY

            }
        }
    }

    companion object {
        const val LOG_TAG = "SC_CalculatorFragmentTag"
    }

}