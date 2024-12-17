package com.cobaltumapps.simplecalculator.v15.fragments.numpad

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentEngineeringNBinding
import com.cobaltumapps.simplecalculator.databinding.LayoutEngineeringBinding
import com.cobaltumapps.simplecalculator.v15.services.AnimationsService
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.EngineeringNumpadKeyboard
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.constants.Property
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.EngineeringBottomBehaviorListener
import com.google.android.material.bottomsheet.BottomSheetBehavior


class EngineeringNumpadFragment(
    private var engController: EngineeringController? = null,
    private var bottomBehaviorListener: EngineeringBottomBehaviorListener? = null
): EngineeringNumpadKeyboard() {

    private val binding by lazy { FragmentEngineeringNBinding.inflate(layoutInflater) }
    private val bindingEngContent by lazy { LayoutEngineeringBinding.bind(binding.root) }

    private val numpadEngBottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.numpadEngLayout) }

    private var angleModeChanged = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (engController == null) {
            engController = EngineeringController()
            setNewKeyboardController(engController!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            numpadEngBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            numpadEngBottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    bottomBehaviorListener?.onStateEngNumpadChanged(bottomSheet, newState)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    bottomBehaviorListener?.onSlideEngNumpad(bottomSheet, slideOffset)
                }
            })

            bindingEngContent.apply {
                numpadEngMS.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemorySave) }
                numpadEngMR.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryRead) }
                numpadEngMC.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryClear) }

                numpadEngMemoryAdd.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryAdd) }
                numpadEngMemorySub.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemorySubtract) }
                numpadEngMemoryMul.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryMultiply) }
                numpadEngMemoryDiv.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.MemoryDivide) }

                numpadEngInvert.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.Invert) }
                numpadEngPercent.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Percent) }

                numpadEngSin.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Sinus) }
                numpadEngCos.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Cosine) }
                numpadEngTan.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Tangent) }
                numpadEngCot.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Cotangent) }

                numpadEngLog.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Log) }
                numpadEngLn.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Ln) }

                numpadEngExp.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Exp) }
                numpadEngPi.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.PI) }

                numpadEngSqrt.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.SQRT) }
                numpadEngFactorial.setOnClickListener { onClickSpecialOperation(KeyboardSpecialOperation.Factorial) }

                numpadEngAngleType.setOnClickListener {
                    onClickSpecialFunction(KeyboardSpecialFunction.AngleMode)
                    changeAngleModeText()
                }
            }


        }
    }

    private fun changeAngleModeText() {
        with(bindingEngContent) {
            angleModeChanged = !angleModeChanged
            numpadEngAngleType.text = if (angleModeChanged) getString(R.string.symbolDeg) else getString(R.string.symbolRad)
        }
    }

    fun setActiveKeyboard(isActivated: Boolean) {
        AnimationsService.animatePropertyChange(
            binding.root,
            Property.alpha.name,
            binding.root.alpha,
            if (isActivated) 1f else 0f,
            300L) {
            numpadEngBottomSheetBehavior.isDraggable = isActivated
        }
    }

    companion object {
        const val LOG_TAG = "SC_EngNumpadFragmentTag"
        const val TAG = "SC_EngNumpadFragmentTag"
    }

}

