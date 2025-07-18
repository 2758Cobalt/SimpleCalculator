package com.cobaltumapps.simplecalculator.domain.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.loader.ExtraUnitInfoLoader

class ExtraUnitReviewerViewModel(application: Application): AndroidViewModel(application) {
    private val extraUnitInfoLoader by lazy { ExtraUnitInfoLoader(application) }

    private val _selectedCalcId = MutableLiveData<List<ExtraUnitInfo>>()
    val selectedCalcId: LiveData<List<ExtraUnitInfo>> = _selectedCalcId

    fun onSelectedCalculator(calcId: String) {
        _selectedCalcId.postValue(extraUnitInfoLoader.getUnitInfo(calcId))
    }
}