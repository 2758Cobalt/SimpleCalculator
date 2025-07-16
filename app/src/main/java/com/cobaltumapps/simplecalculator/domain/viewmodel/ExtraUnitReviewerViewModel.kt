package com.cobaltumapps.simplecalculator.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExtraUnitReviewerViewModel: ViewModel() {
    private val _selectedCalcId = MutableLiveData<String>()
    val selectedCalcId: LiveData<String> = _selectedCalcId

    fun onSelectedCalculator(calcId: String) {
        _selectedCalcId.postValue(calcId)
    }
}