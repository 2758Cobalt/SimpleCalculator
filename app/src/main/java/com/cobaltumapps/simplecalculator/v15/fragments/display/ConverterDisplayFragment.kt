package com.cobaltumapps.simplecalculator.v15.fragments.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterDisplayBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayComponent
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.formatter.DisplayFormatter
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryStorageControllerSingleton
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterUserInputHandlerListener
import com.cobaltumapps.simplecalculator.v15.fragments.display.interfaces.ConverterDisplayViewer

class ConverterDisplayFragment: DisplayComponent(),
    ConverterDisplayViewer, ConverterUserInputHandlerListener {
    private val binding by lazy { FragmentConverterDisplayBinding.inflate(layoutInflater) }

    private val displayFormatter = DisplayFormatter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null)
            binding.converterUserInputField.text = savedInstanceState.getString(KEY_USER_INPUT_FIELD)

        setMemoryViewField(MemoryStorageControllerSingleton.getInstance().readMemory())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_USER_INPUT_FIELD, binding.converterUserInputField.text.toString())
    }

    override fun setMemoryViewField(memoryValue: Number) {
        binding.converterMemoryField.text = getString(R.string.display_memory).format(memoryValue.toString())
    }

    override fun setUserEntryField(userEntry: String) {
        binding.converterUserInputField.text = displayFormatter.formatResult(userEntry)
    }

    override fun receiveUserEntry(receivedEntry: String) {
        setUserEntryField(receivedEntry)
    }

    companion object {
        const val KEY_USER_INPUT_FIELD = "SC_SavedStateUserInputField"
        const val FRAG_TAG = "DisplayFragmentTag"
    }

}