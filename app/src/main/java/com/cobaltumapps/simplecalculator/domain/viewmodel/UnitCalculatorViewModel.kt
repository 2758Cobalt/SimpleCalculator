package com.cobaltumapps.simplecalculator.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UnitCalculatorViewModel: ViewModel() {
    private val _selectedItemPos = MutableLiveData<Int>(-1)
    val selectedItemPos: LiveData<Int> = _selectedItemPos

    private val _onDialogCall = MutableLiveData<Event<Int>>()
    val onDialogCall: LiveData<Event<Int>> = _onDialogCall

    fun onSelectedItem(itemPos: Int) {
        if (_selectedItemPos.value != itemPos) {
            _selectedItemPos.postValue(itemPos)
        } else _onDialogCall.value = Event(itemPos)
    }

    fun onEnteredUnitValue(value: Float, onUpdateValue: (selectedItemPosition: Int, enteredValue: Float) -> Unit) {
        _selectedItemPos.value?.let {
            onUpdateValue.invoke(it, value)
        }
    }

}