package com.cobaltumapps.simplecalculator.v15.calculator.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentCalculatorNBinding
import com.cobaltumapps.simplecalculator.v15.calculator.display.DisplayFragmentN
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.EngineeringNumpadFragmentN
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.NumpadFragmentN
import com.google.android.material.bottomsheet.BottomSheetBehavior

// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class CalculatorFragmentN: Fragment() {
    private val display = DisplayFragmentN()
    private val numpad = NumpadFragmentN()
    private val enginneringNumpad = EngineeringNumpadFragmentN()

    private lateinit var binding: FragmentCalculatorNBinding

    private enum class EnumBottomSheetBehavior {
        expanded, collapsed, dragging, settling, hidden
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorNBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.beginTransaction().replace(binding.displayHolder.id, display).commit()
        parentFragmentManager.beginTransaction().replace(binding.numpadHolder.id, numpad).commit()
        parentFragmentManager.beginTransaction().replace(binding.engineeringNumpadHolder.id, enginneringNumpad).commit()

        val bottomNumpad = BottomSheetBehavior.from(binding.numpadBottomSheet)
        val bottomNumpadEngineering = BottomSheetBehavior.from(binding.engineeringNumpadHolder)

        bottomNumpad.state = BottomSheetBehavior.STATE_EXPANDED
        bottomNumpadEngineering.state = BottomSheetBehavior.STATE_HIDDEN

        bottomNumpadEngineering.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // Панель открыта
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // Панель закрыта
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        // Панель в процессе перетаскивания
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
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