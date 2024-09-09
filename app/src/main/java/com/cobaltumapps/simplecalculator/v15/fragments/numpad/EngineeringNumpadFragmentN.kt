package com.cobaltumapps.simplecalculator.v15.fragments.numpad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentEngineeringNBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.EngineeringNumpadKeyboard
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation


// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class EngineeringNumpadFragmentN: EngineeringNumpadKeyboard() {
    private var angleModeChanged = false
    private val binding by lazy { FragmentEngineeringNBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numpadEngMS.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemorySave) }
        binding.numpadEngMR.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryRead) }
        binding.numpadEngMC.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryClear) }

        binding.numpadEngMemoryAdd.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryAdd) }
        binding.numpadEngMemorySub.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemorySubtract) }
        binding.numpadEngMemoryMul.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryMultiply) }
        binding.numpadEngMemoryDiv.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryDivide) }

        binding.numpadEngInvert.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.Invert) }
        binding.numpadEngPercent.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Percent) }

        binding.numpadEngSin.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Sinus) }
        binding.numpadEngCos.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Cosine) }
        binding.numpadEngTan.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Tangent) }
        binding.numpadEngCot.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Cotangent) }

        binding.numpadEngLog.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Log) }
        binding.numpadEngLn.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Ln) }

        binding.numpadEngExp.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Exp) }
        binding.numpadEngPi.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.PI) }

        binding.numpadEngSqrt.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.SQRT) }
        binding.numpadEngFactorial.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Factorial) }

        binding.numpadEngAngleType.setOnClickListener {
            onClickSpecialFunction(KeyboardSpecialFunction.AngleMode)
            changeAngleModeText()
        }
    }

    private fun changeAngleModeText() {
        angleModeChanged = !angleModeChanged
        if (angleModeChanged)
            binding.numpadEngAngleType.text = getString(R.string.symbolDeg)
        else
            binding.numpadEngAngleType.text = getString(R.string.symbolRad)
    }

}