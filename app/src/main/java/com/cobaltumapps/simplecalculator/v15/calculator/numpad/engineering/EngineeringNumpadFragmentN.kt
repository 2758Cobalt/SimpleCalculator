package com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentEngineeringNBinding
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MemoryOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.TrigonometryOperation
import com.cobaltumapps.simplecalculator.v15.calculator.host.interfaces.EngineeringNumpadKeyboard


// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class EngineeringNumpadFragmentN: Fragment(), EngineeringNumpadKeyboard {
    private lateinit var binding: FragmentEngineeringNBinding
    private var engineeringNumpadListener: EngineeringNumpadKeyboard? = null


    private var angleType = AngleMode.Radian

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEngineeringNBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numpadEngMS.setOnClickListener { onClickMemoryOperation(MemoryOperation.MemorySave) }
        binding.numpadEngMR.setOnClickListener { onClickMemoryOperation(MemoryOperation.MemoryRead) }
        binding.numpadEngMC.setOnClickListener { onClickMemoryOperation(MemoryOperation.MemoryClear) }


        binding.numpadEngMemoryAdd.setOnClickListener { onClickMemoryOperation(MemoryOperation.MemoryAdd) }
        binding.numpadEngMemorySub.setOnClickListener { onClickMemoryOperation(MemoryOperation.MemorySub) }
        binding.numpadEngMemoryMul.setOnClickListener { onClickMemoryOperation(MemoryOperation.MemoryMul) }
        binding.numpadEngMemoryDiv.setOnClickListener { onClickMemoryOperation(MemoryOperation.MemoryDiv) }


        binding.numpadEngSin.setOnClickListener { onClickTrigonometryOperation(TrigonometryOperation.Sine) }
        binding.numpadEngCos.setOnClickListener { onClickTrigonometryOperation(TrigonometryOperation.Cosine) }
        binding.numpadEngTan.setOnClickListener { onClickTrigonometryOperation(TrigonometryOperation.Tangent) }
        binding.numpadEngCot.setOnClickListener { onClickTrigonometryOperation(TrigonometryOperation.Cotangent) }


        binding.numpadEngAngleType.setOnClickListener {
            angleType = when(angleType) {
                AngleMode.Radian -> AngleMode.Degree
                AngleMode.Degree -> AngleMode.Radian
            }

            onClickAngleMode(angleType)
        }
    }

    fun setEngNumpadListener(listener: EngineeringNumpadKeyboard) {
        this.engineeringNumpadListener = listener
    }

    override fun onClickOperation(operation: MathOperation) {
        engineeringNumpadListener?.onClickOperation(operation)
    }

    override fun onClickMemoryOperation(memoryOperation: MemoryOperation) {
        engineeringNumpadListener?.onClickMemoryOperation(memoryOperation)
    }

    override fun onClickTrigonometryOperation(trigonometryOperation: TrigonometryOperation) {
        engineeringNumpadListener?.onClickTrigonometryOperation(trigonometryOperation)
    }

    override fun onClickAngleMode(angleMode: AngleMode) {
        engineeringNumpadListener?.onClickAngleMode(angleMode)
    }

}