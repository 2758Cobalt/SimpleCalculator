package com.cobaltumapps.simplecalculator.v15.calculator.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.databinding.FragmentCalculatorNBinding
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.v15.calculator.calculatorMonitor.CalculatorExpressionMonitor
import com.cobaltumapps.simplecalculator.v15.calculator.controllers.EngineeringInputController
import com.cobaltumapps.simplecalculator.v15.calculator.controllers.NumpadInputController
import com.cobaltumapps.simplecalculator.v15.calculator.display.DisplayFragmentN
import com.cobaltumapps.simplecalculator.v15.calculator.host.interfaces.HostManager
import com.cobaltumapps.simplecalculator.v15.calculator.managers.CalculatorManager
import com.cobaltumapps.simplecalculator.v15.calculator.models.Expression
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.NumpadFragmentN
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering.EngineeringNumpadFragmentN
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering.EngineeringSwiper
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.Memory
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryController
import com.cobaltumapps.simplecalculator.v15.calculator.system.CalculatorCore
import com.cobaltumapps.simplecalculator.v15.constants.Properties
import com.google.android.material.bottomsheet.BottomSheetBehavior

// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class CalculatorFragmentN: Fragment(), HostManager {
    private lateinit var binding: FragmentCalculatorNBinding

    private var exampleExpressions = mutableListOf(
                Expression("1 + 2"),
                Expression("2 + 3"),
                Expression("3 + 4"),
                Expression("4 + 5"),
                Expression("5 + 6"),
                Expression("6 + 7")
    )

    private lateinit var historyAdapter : CalculatorHistoryRecyclerAdapter

    private val display = DisplayFragmentN()
    private val numpad = NumpadFragmentN()
    private val engineeringNumpad = EngineeringNumpadFragmentN()

    private val engineeringSwiper = EngineeringSwiper()

    private val numpadInputController = NumpadInputController()
    private val engineeringInputController = EngineeringInputController()

    private val calculatorManager = CalculatorManager()
    private val calculatorExpressionMonitor = CalculatorExpressionMonitor()

    private val calculatorN = CalculatorCore()

    private val calculatorMemory = Memory()
    private val calculatorMemoryController = MemoryController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorNBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calculatorExpressionMonitor.setDisplayListener(display)

        // Настройка менеджера калькулятора
        calculatorManager.setCalculatorInstance(calculatorN)
        calculatorManager.setMonitor(calculatorExpressionMonitor)
        calculatorManager.setMemoryControllerInstance(calculatorMemoryController)

        // Настрйока контроллера памяти
        calculatorMemoryController.setDisplayListener(display)
        calculatorMemoryController.setCalculatorManager(calculatorManager)
        calculatorMemoryController.setMemoryInstance(calculatorMemory)

        // Настройка клавиатуры
        numpad.setNumpadListener(numpadInputController)
        numpadInputController.setCalculatorManager(calculatorManager)
        numpadInputController.setMemoryControllerListener(calculatorMemoryController)

        // Настройка продвинутой клавиатуры
        engineeringNumpad.setEngNumpadListener(engineeringInputController)
        engineeringInputController.setCalculatorManager(calculatorManager)
        engineeringInputController.setMemoryControllerListener(calculatorMemoryController)


        parentFragmentManager.beginTransaction().replace(binding.calculatorDisplayHolder.id, display).commit()
        parentFragmentManager.beginTransaction().replace(binding.numpadHolder.id, numpad).commit()
        parentFragmentManager.beginTransaction().replace(binding.engineeringNumpadHolder.id, engineeringNumpad).commit()

        val bottomNumpad = BottomSheetBehavior.from(binding.numpadHolder)
        val bottomNumpadEngineering = BottomSheetBehavior.from(binding.engineeringNumpadHolder)

        bottomNumpad.state = BottomSheetBehavior.STATE_EXPANDED
        bottomNumpadEngineering.state = BottomSheetBehavior.STATE_HIDDEN

        historyAdapter = CalculatorHistoryRecyclerAdapter(exampleExpressions)

        binding.calculatorRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }

        binding.calculatorRecyclerView.isVisible = false
        binding.calculatorHistoryClearFab.isVisible = false
        binding.calculatorBackSpaceIcon.isVisible = false

        binding.calculatorHistoryClearFab.setOnClickListener {
            historyAdapter.clearHistoryList()
        }

        // Список View которые нужно анимировать при отображении истории
        val listItemsToAnimateInHistory = listOf(
            binding.calculatorRecyclerView,
            binding.calculatorHistoryClearFab
        )

        bottomNumpad.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                    // Numpad развернут
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        showHistoryRecycler(false, listItemsToAnimateInHistory) // Скрываем историю
                        enableEngineeringKeyboard(true, bottomNumpadEngineering)
                    }

                    // Numpad закрыт
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        showHistoryRecycler(true, listItemsToAnimateInHistory)
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

    // Показывает RecyclerView с историей
    private fun showHistoryRecycler(isVisible: Boolean, listItemsToAnimate: List<View>) {
        for (item in listItemsToAnimate) {
            if (isVisible) {
                item.isVisible = true
                Animations.animatePropertyChange(item, Properties.alpha, 0f, 1f, 400L)
            }
            else {
                item.isVisible = false
            }
        }
    }

    companion object {
        const val TAG = "SC_CalculatorFragmentTag"
    }

}