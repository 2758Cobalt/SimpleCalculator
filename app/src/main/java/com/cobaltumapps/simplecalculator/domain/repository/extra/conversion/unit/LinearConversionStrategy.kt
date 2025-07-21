package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit

import com.cobaltumapps.simplecalculator.data.extra.constants.UnitsFactors
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.FactorBasedConversion
import com.cobaltumapps.simplecalculator.data.extra.ExtraCalculatorsKeys as ECK

class LinearConversionStrategy: ConversionStrategy {
    private val factorBasedConversion = FactorBasedConversion()

    override fun calculate(
        calculatorId: String,
        userEntry: Float,
        selectedItemIndex: Int
    ): List<Number> {
        return if (FACTOR_BASED_UNITS.keys.contains(calculatorId)) {
            val factors = FACTOR_BASED_UNITS.entries.find { it.key == calculatorId }?.value ?: listOf()
            calculateFactorBased(factors, userEntry, selectedItemIndex)
        }
        else emptyList()
    }

    private fun calculateFactorBased(factors: List<Double>, userEntry: Number, index: Int): List<Double> {
        return factorBasedConversion.convertUnits(userEntry.toDouble(), index, factors)
    }

    companion object {
        val FACTOR_BASED_UNITS = mapOf(
            ECK.CALC_UNIT_WEIGHT_ID to UnitsFactors.weightFactors,
            ECK.CALC_UNIT_LENGTH_ID to UnitsFactors.lengthFactors,
            ECK.CALC_UNIT_TIME_ID to UnitsFactors.timeFactors,
            ECK.CALC_UNIT_VOLUME_ID to UnitsFactors.volumeFactors)
    }
}