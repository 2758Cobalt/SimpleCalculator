package com.cobaltumapps.simplecalculator.v15.calculator.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.databinding.FragmentCalculatorNBinding
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.v15.calculator.display.DisplayFragmentN
import com.cobaltumapps.simplecalculator.v15.calculator.history.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.history.Expression
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.EngineeringNumpadFragmentN
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.NumpadFragmentN
import com.google.android.material.bottomsheet.BottomSheetBehavior

// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class CalculatorFragmentN: Fragment() {
    private lateinit var binding: FragmentCalculatorNBinding

    private lateinit var historyAdapter : CalculatorHistoryRecyclerAdapter

    private val display = DisplayFragmentN()
    private val numpad = NumpadFragmentN()
    private val engineeringNumpad = EngineeringNumpadFragmentN()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorNBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.beginTransaction().replace(binding.displayHolder.id, display).commit()
        parentFragmentManager.beginTransaction().replace(binding.numpadHolder.id, numpad).commit()
        parentFragmentManager.beginTransaction().replace(binding.engineeringNumpadHolder.id, engineeringNumpad).commit()

        val bottomNumpad = BottomSheetBehavior.from(binding.numpadBottomSheet)
        val bottomNumpadEngineering = BottomSheetBehavior.from(binding.engineeringNumpadHolder)

        bottomNumpad.state = BottomSheetBehavior.STATE_EXPANDED
        bottomNumpadEngineering.state = BottomSheetBehavior.STATE_HIDDEN


        val exampleExpressions = listOf(
            Expression("1 + 58"),
            Expression("2 * 123.56"),
            Expression("3 / 51")
            )

        historyAdapter = CalculatorHistoryRecyclerAdapter(exampleExpressions)

        binding.calculatorRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }

        bottomNumpad.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        historyAdapter.updateDisplay(false)
                        Animations.animatePropertyChange(binding.engineeringNumpadHolder, "alpha", binding.engineeringNumpadHolder.alpha, 1f, 100L)
                        bottomNumpadEngineering.isDraggable = true
                        // Панель открыта
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        historyAdapter.updateDisplay(true)
                        Animations.animatePropertyChange(binding.engineeringNumpadHolder, "alpha", binding.engineeringNumpadHolder.alpha, 0f, 100L)
                        // Панель закрыта
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        // Панель в процессе перетаскивания
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        bottomNumpadEngineering.isDraggable = false
                        // Панель в процессе фиксации состояния
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        // Панель скрыта
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

    }



}