package com.cobaltumapps.simplecalculator.domain.repository.extra.fabric.unit

import com.cobaltumapps.simplecalculator.data.extra.constants.UnitsFactors
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.FactorBasedConversion
import com.cobaltumapps.simplecalculator.data.extra.ExtraCalculatorsKeys as ECK

class ExtraUnitConversionFabric {
    private val factorBasedConversion = FactorBasedConversion()

    private val factorBasedUnits = mapOf(
        ECK.CALC_UNIT_WEIGHT_ID to UnitsFactors.weightFactors,
        ECK.CALC_UNIT_LENGTH_ID to UnitsFactors.lengthFactors,
        ECK.CALC_UNIT_TIME_ID to UnitsFactors.timeFactors,
        ECK.CALC_UNIT_VOLUME_ID to UnitsFactors.volumeFactors)

    fun calculateUnitsById(calculatorId: String, userEntry: Float, selectedItemIndex: Int): List<Double> {
        return if (factorBasedUnits.keys.contains(calculatorId)) {
            val calcId = factorBasedUnits.entries.find { it.key == calculatorId }?.value ?: listOf()
            calculateFactorBased(calcId, userEntry, selectedItemIndex)
        }
        else listOf()
    }

    private fun calculateFactorBased(factors: List<Double>, userEntry: Number, index: Int): List<Double> {
        return factorBasedConversion.convertUnits(userEntry.toDouble(), index, factors)
    }
}