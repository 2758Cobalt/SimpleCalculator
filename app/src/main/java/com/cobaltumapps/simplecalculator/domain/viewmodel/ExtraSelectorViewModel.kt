package com.cobaltumapps.simplecalculator.domain.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.ExtraSelectorCalculatorFabric

class ExtraSelectorViewModel(application: Application): AndroidViewModel(application) {
    private val extraCalculatorFabric = ExtraSelectorCalculatorFabric()

    fun createCalculators(): List<ExtraSelectableCalculatorInfo> {
        return extraCalculatorFabric.createCalculators(application)
    }

}