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
import com.cobaltumapps.simplecalculator.v15.converter.controllers.MiniNumpadController

class MiniNumpadFragment(
    private var miniNumpadController: MiniNumpadController? = null
): Fragment() {

    private val binding by lazy { FragmentConverterNumpadBinding.inflate(layoutInflater) }
    private val bindingNumpadContent by lazy { LayoutConverterNumpadBinding.bind(binding.root) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View = binding.root

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            bindingNumpadContent.apply {
                converterNumpad0.setOnClickListener { miniNumpadController?.onClickNumber(0) }
                converterNumpad1.setOnClickListener { miniNumpadController?.onClickNumber(1) }
                converterNumpad2.setOnClickListener { miniNumpadController?.onClickNumber(2) }
                converterNumpad3.setOnClickListener { miniNumpadController?.onClickNumber(3) }
                converterNumpad4.setOnClickListener { miniNumpadController?.onClickNumber(4) }
                converterNumpad5.setOnClickListener { miniNumpadController?.onClickNumber(5) }
                converterNumpad6.setOnClickListener { miniNumpadController?.onClickNumber(6) }
                converterNumpad7.setOnClickListener { miniNumpadController?.onClickNumber(7) }
                converterNumpad8.setOnClickListener { miniNumpadController?.onClickNumber(8) }
                converterNumpad9.setOnClickListener { miniNumpadController?.onClickNumber(9) }

                converterNumpadBackspace.setOnClickListener { miniNumpadController?.onClickSpecialFunction(KeyboardSpecialFunction.Backspace) }
                //converterNumpadAC.setOnClickListener { miniNumpadController?.onClickSpecialFunction(KeyboardSpecialFunction.AllClear) }
            }
        }
    }

    companion object {
        const val FRAG_TAG = "ConverterNumpadFragmentTag"
    }
}