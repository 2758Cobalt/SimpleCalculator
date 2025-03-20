package com.cobaltumapps.simplecalculator.v15.fragments.numpad

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterNumpadBinding
import com.cobaltumapps.simplecalculator.databinding.LayoutConverterNumpadBinding
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterNumpadController
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ConverterNumpadFragment(
    private var converterNumpadController: ConverterNumpadController? = null
): Fragment() {
    private val binding by lazy { FragmentConverterNumpadBinding.inflate(layoutInflater) }
    private val bindingNumpadContent by lazy { LayoutConverterNumpadBinding.bind(binding.root) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View = binding.root

    private val converterNumpadBottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.converterNumpadLayout) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(bindingNumpadContent) {
            converterNumpad0.setOnClickListener { converterNumpadController?.onClickNumber(0) }
            converterNumpad1.setOnClickListener { converterNumpadController?.onClickNumber(1) }
            converterNumpad2.setOnClickListener { converterNumpadController?.onClickNumber(2) }
            converterNumpad3.setOnClickListener { converterNumpadController?.onClickNumber(3) }
            converterNumpad4.setOnClickListener { converterNumpadController?.onClickNumber(4) }
            converterNumpad5.setOnClickListener { converterNumpadController?.onClickNumber(5) }
            converterNumpad6.setOnClickListener { converterNumpadController?.onClickNumber(6) }
            converterNumpad7.setOnClickListener { converterNumpadController?.onClickNumber(7) }
            converterNumpad8.setOnClickListener { converterNumpadController?.onClickNumber(8) }
            converterNumpad9.setOnClickListener { converterNumpadController?.onClickNumber(9) }

            converterNumpadBackspace.apply {
                setOnClickListener { converterNumpadController?.onClickSpecialFunction(KeyboardSpecialFunction.Backspace) }
                setOnLongClickListener { converterNumpadController?.onClickSpecialFunction(KeyboardSpecialFunction.AllClear); true }
            }
        }

        converterNumpadBottomSheetBehavior.addBottomSheetCallback(object:  BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    companion object {
        const val FRAG_TAG = "ConverterNumpadFragmentTag"
    }

}