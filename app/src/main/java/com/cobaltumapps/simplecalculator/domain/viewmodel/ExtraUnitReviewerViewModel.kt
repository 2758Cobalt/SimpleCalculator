package com.cobaltumapps.simplecalculator.domain.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.loader.ExtraUnitInfoLoader

class ExtraUnitReviewerViewModel(application: Application): AndroidViewModel(application) {
    private val extraUnitInfoLoader by lazy { ExtraUnitInfoLoader(application) }

    private val _selectedCalcId = MutableLiveData<String>()
    val selectedCalcId: LiveData<String> = _selectedCalcId

    private val _loadedCalcInfo = MutableLiveData<List<ExtraUnitInfo>>()
    val loadedCalcInfo: LiveData<List<ExtraUnitInfo>> = _loadedCalcInfo

    fun onSelectedCalculator(calcId: String) {
        _selectedCalcId.value = calcId
        _loadedCalcInfo.value = extraUnitInfoLoader.getUnitInfo(calcId)
    }
}