package com.cobaltumapps.simplecalculator.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UnitCalculatorViewModel: ViewModel() {
    private val _selectedItemPos = MutableLiveData<Int>(-1)
    val selectedItemPos: LiveData<Int> = _selectedItemPos

    private val _onDialogCall = MutableLiveData<Event<Int>>()
    val onDialogCall: LiveData<Event<Int>> = _onDialogCall

    private val _onEnteredValue = MutableLiveData<String>("0.0")
    val onEnteredValue: LiveData<String> = _onEnteredValue

    fun onSelectedItem(itemPos: Int) {
        if (_selectedItemPos.value != itemPos) {
            _selectedItemPos.value = itemPos
        } else _onDialogCall.value = Event(itemPos)
    }

    fun onEnteredUnitValue(value: String, onUpdateValue: (selectedItemPosition: Int, enteredValue: String) -> Unit) {
        _selectedItemPos.value?.let {
            _onEnteredValue.value = value
            onUpdateValue.invoke(it, value)
        }
    }

}