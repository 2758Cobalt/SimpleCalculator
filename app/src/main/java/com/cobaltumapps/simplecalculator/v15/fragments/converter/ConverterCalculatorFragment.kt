package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterCalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterNumpadController
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterNumpadControllerListener
import com.cobaltumapps.simplecalculator.v15.converter.mediator.ConverterMediator
import com.cobaltumapps.simplecalculator.v15.fragments.display.ConverterDisplayFragment
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.ConverterNumpadFragment

class ConverterCalculatorFragment(private val mediator: ConverterMediator): Fragment(),
    ConverterNumpadControllerListener {
    private val binding by lazy { FragmentConverterCalculatorBinding.inflate(layoutInflater) }

    var navigationListener: ConverterCalculatorNavigationListener? = null

    private val converterNumpadController = ConverterNumpadController(this@ConverterCalculatorFragment)

    private val converterNumpadFragment = ConverterNumpadFragment(converterNumpadController)
    private val converterDisplayFragment = ConverterDisplayFragment()

    init {
        mediator.calculatorFragmentInstance = this@ConverterCalculatorFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            parentFragmentManager.commit {
                replace(converterNumpadFrame.id, converterNumpadFragment, ConverterNumpadFragment.FRAG_TAG)
                replace(converterDisplayFrame.id, converterDisplayFragment, ConverterDisplayFragment.FRAG_TAG)
            }

        }
    }

    override fun receiveUserEntry(userEntry: String) {
        converterDisplayFragment.receiveUserEntry(userEntry)
        mediator.receiveUserEntry(userEntry)
    }

    override fun confirmEntry() {
        mediator.confirmEntry()
        navigationListener?.onConfirmUserEntry()
    }

    companion object {
        const val FRAG_TAG = "ConverterCalculatorFragmentTag"
    }

}