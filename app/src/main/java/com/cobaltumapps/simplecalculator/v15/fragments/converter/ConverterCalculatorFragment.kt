package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterCalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryStorageControllerSingleton
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemoryController
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterNumpadController
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterNumpadControllerListener
import com.cobaltumapps.simplecalculator.v15.converter.mediator.ConverterMediator
import com.cobaltumapps.simplecalculator.v15.fragments.display.ConverterDisplayFragment
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.ConverterNumpadFragment

class ConverterCalculatorFragment(private val mediator: ConverterMediator): Fragment(),
    ConverterNumpadControllerListener, MemoryController {
    private val binding by lazy { FragmentConverterCalculatorBinding.inflate(layoutInflater) }

    var navigationListener: ConverterCalculatorNavigationListener? = null

    private val converterNumpadController = ConverterNumpadController(this@ConverterCalculatorFragment)
    private val memoryStorageController = MemoryStorageControllerSingleton.getInstance()

    private val converterNumpadFragment = ConverterNumpadFragment(converterNumpadController)
    private val converterDisplayFragment = ConverterDisplayFragment()

    init {
        mediator.calculatorFragmentInstance = this@ConverterCalculatorFragment
        converterNumpadController.memoryManagerListener = this@ConverterCalculatorFragment
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

    override fun saveMemoryValue(value: Number, onSuccessful: ((result: Double) -> Unit?)?) {
        memoryStorageController.saveMemoryValue(value, onSuccessful)
        converterDisplayFragment.setMemoryViewField(value)
    }

    override fun readMemory(): Double {
        val receivedMemory = memoryStorageController.readMemory()
        converterDisplayFragment.receiveUserEntry(receivedMemory.toString())
        mediator.receiveUserEntry(receivedMemory.toString())
        return receivedMemory
    }

    override fun clearMemory(onSuccessful: ((result: Double) -> Unit?)?) {
        memoryStorageController.clearMemory(onSuccessful)
        converterDisplayFragment.setMemoryViewField(0)
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