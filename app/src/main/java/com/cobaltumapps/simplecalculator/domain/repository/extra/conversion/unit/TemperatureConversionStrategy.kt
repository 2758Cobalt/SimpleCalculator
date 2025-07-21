package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit

class TemperatureConversionStrategy: ConversionStrategy {

    override fun calculate(
        calculatorId: String,
        userEntry: Float,
        selectedItemIndex: Int
    ): List<Number> {
        val result = mutableListOf<Double>()

        val temperatureConverters: List<(Double) -> Double> = when (selectedItemIndex) {
            // Celsius
            0 -> listOf(
                { it },                         // Цельсий
                { it * 9 / 5 + 32 },            // Фаренгейт
                { it + 273.15 },                // Кельвин
                { it * 9 / 5 + 491.67 },        // Ранкин
                { it * 0.8 }                    // Реомюр
            )
            // Fahrenheit
            1 -> listOf(
                { (it - 32) * 5 / 9 },          // Цельсий
                { it },                         // Фаренгейт
                { (it - 32) * 5 / 9 + 273.15 }, // Кельвин
                { it + 459.67 },                // Ранкин
                { (it - 32) * 0.44444 }         // Реомюр
            )
            // Kelvin
            2 -> listOf(
                { it - 273.15 },                // Цельсий
                { (it - 273.15) * 9 / 5 + 32 }, // Фаренгейт
                { it },                         // Кельвин
                { it * 1.8 },                   // Ранкин
                { (it - 273.15) * 0.8 }         // Реомюр
            )
            // Rankine
            3 -> listOf(
                { (it - 491.67) * 5 / 9 },      // Цельсий
                { it - 459.67 },                // Фаренгейт
                { it * 5 / 9 },                 // Кельвин
                { it },                         // Ранкин
                { (it - 491.67) * 0.44444 }     // Реомюр
            )
            // Reaumur
            4 -> listOf(
                { it / 0.8 },                   // Цельсий
                { it * 2.25 + 32 },             // Фаренгейт
                { it / 0.8 + 273.15 },          // Кельвин
                { it * 2.25 + 491.67 },         // Ранкин
                { it }                          // Реомюр
            )
            else -> emptyList()
        }

        for (converter in temperatureConverters) {
            result.add(converter(userEntry.toDouble()))
        }

        return result
    }

}