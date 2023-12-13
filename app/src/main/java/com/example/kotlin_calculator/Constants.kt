package com.example.kotlin_calculator

object Constants {
    // Данный файл представляет собой набор ссылок и массивов для классов программы.
    // Файл созданый с целью отделить содержимое, улучшив читабельность кода

    // Набор данных, нужных для метода dataSetIdGetting() класса ConvertorFragment.kt
    val constantsData: Array<Triple<Int, Int, Int>> = arrayOf(
        Triple(R.string.label_weightText, R.string.tooltip_weightText, R.array.weight_values),
        Triple(R.string.label_lengthText, R.string.tooltip_lengthText, R.array.length_values),
        Triple(R.string.label_speedText, R.string.tooltip_speedText, R.array.speed_values),
        Triple(R.string.label_dataText, R.string.tooltip_dataText, R.array.data_values),
        Triple(R.string.label_temperatureText, R.string.tooltip_temperatureText, R.array.temperature_values),
        Triple(R.string.label_volumeText, R.string.tooltip_volumeText, R.array.volume_values),
        Triple(R.string.label_frequencyText, R.string.tooltip_frequencyText, R.array.frequency_values),
        Triple(R.string.label_fuelConsumptionText, R.string.tooltip_fuelConsumptionText, R.array.fuelConsumption_values))

    // Нужны для метода dataToConvert() класса ConvertorFragment.kt
    val weightConvert : Array<Array<Double>> = arrayOf(
        arrayOf(1.0, 1e3, 1e6, 1e9, 1e12, 1e15, 1e18),              // Микрограммы
        arrayOf(0.001, 1.0, 1000.0, 1e6, 1e9, 1e12, 1e15, 1e18),    // Миллиграммы
        arrayOf(1e-6, 1e-3, 1.0, 1e-1, 1e-2, 1e-3, 1e-6),           // Граммы
        arrayOf(1e-9, 1e-6, 1e-3, 1.0, 1e3, 1e6, 1e9, 1e12),        // Килограммы
        arrayOf(1e-12, 1e-9, 1e-6, 1e-3, 1.0, 1e3, 1e6, 1e9),       // Тонны
        arrayOf(1e-15, 1e-12, 1e-9, 1e-6, 1e-3, 1.0, 1e3, 1e6),     // Килотонны
        arrayOf(1e-18, 1e-15, 1e-12, 1e-9, 1e-6, 1e-3, 1.0)         // Мегатонны
    )
    val lengthConvert : Array<Array<Double>> = arrayOf(
        arrayOf(1.0, 1000.0, 1e6, 1e7, 1e8, 1e9, 1e12),             // Нанометры
        arrayOf(0.001, 1.0, 1000.0, 1e4, 1e5, 1e6, 1e9),            // Микрометры
        arrayOf(1e-6, 1e-3, 1.0, 1e-1, 1e-2, 1e-3, 1e-6),           // Миллиметры
        arrayOf(1e-7, 1e-4, 1e-1, 1.0, 1e-1, 1e-2, 1e-5),           // Сантиметры
        arrayOf(1e-8, 1e-5, 1e-2, 1e-1, 1.0, 1e-1, 1e-4),           // Дециметры
        arrayOf(1e-9, 1e-6, 1e-3, 1e-2, 1e-1, 1.0, 1e-4),           // Метры
        arrayOf(1e-12, 1e-9, 1e-6, 1e-5, 1e-4, 1e-3, 1.0)           // Километры
    )
    val speedConvert : Array<Array<Double>> = arrayOf(
        arrayOf(1.0, 299792458.0, 1079252848.8, 299792.458, 670616629.3844, 186282.39705122, 582749918.36357),   // Скорость света (c=299 792 458)
        arrayOf(3.3356e-9, 1.0, 3.6, 1000.0, 2.23694, 1.94384, 3.28084),                                         // М/с (метры в секунду)
        arrayOf(9.2656693110598e-10, 0.277778, 1.0, 0.000277778, 0.621371, 0.539957, 0.911344),                  // Км/ч (километры в час)
        arrayOf(3.3356409519815e-9, 1000.0, 3600.0, 1.0, 2236.9362920544, 1943.8444924574, 3280.84),             // Км/с (километры в секунду)
        arrayOf(1.4911649311738e-9, 0.44704, 1.609344, 0.00044704, 1.0, 0.00027, 0.868976),                      // М/ч (миля в час)
        arrayOf(5.3681937522257e-10, 83333.0, 5793.6384, 1.609344, 3600.0, 1.0, 3128.31447087),                  // Мл/с (миля в секунду)
        arrayOf(1.7160019563934e-10, 0.5144, 1.851999999984, 0.000514, 1.1507794480136, 0.00031966095778156, 1.0)// Уз (узлы)
    )
    val dataConvert : Array<Array<Double>> = arrayOf(
        arrayOf(1e0, 1e-3, 1e-6, 1e-9, 1e-12, 1e-15, 1e-18),        // Байт
        arrayOf(1e3, 1e0, 1e-3, 1e-6, 1e-9, 1e-12, 1e-15),          // КилоБайт
        arrayOf(1e6, 1e3, 1e0, 1e-3, 1e-6, 1e-9, 1e-12),            // МегаБайт
        arrayOf(1e9, 1e6, 1e3, 1e0, 1e-3, 1e-6, 1e-9),              // ГигаБайт
        arrayOf(1e12, 1e9, 1e6, 1e3, 1e0, 1e-3, 1e-6),              // ТераБайт
        arrayOf(1e15, 1e12, 1e9, 1e6, 1e3, 1e0, 1e-3),              // ПетаБайт
        arrayOf(1e18, 1e15, 1e12, 1e9, 1e6, 1e3, 1e0)               // ЭксаБайт
    )
    val temperatureConvert : Array<Array<Double>> = arrayOf(
        arrayOf(1.0, (9.0 / 5.0), 273.15, 273.15 * 1.8, (4.0 / 5.0)),                   // Цельсий
        arrayOf((5.0 / 9.0), 1.0, 459.67 * (5.0 / 9.0), 459.67, (4.0 / 9.0)),           // Фаренгейт
        arrayOf(1.0, 9.0 / 5.0, 1.0, 9.0 / 5.0, 4.0 / 5.0),                             // Кельвин
        arrayOf((5.0 / 9.0), 1.0 - 459.67, 5.0 / 9.0, 1.0, 4.0 / 9.0),                  // Ранкин
        arrayOf(5.0 / 4.0, (9.0 / 4.0), 5.0 / 4.0 + 273.15, 9.0 / 4.0 + 491.67, 1.0)    // Реомюр
    )
    val volumeConvert : Array<Array<Double>> = arrayOf(
        arrayOf(1.0, 1e3, 1e6, 28320.0, 16.387, 3785.4),                                // Миллилитры
        arrayOf(1e-3, 1.0, 1e3, 28.317, (1 / 61.024), 3.7854),                          // Литры
        arrayOf(1e-6, 1e-3, 1.0, (1 / 35.315), (1 / 61020.0), (1 /  264.2)),            // Кубические метры
        arrayOf((1 / 28320.0), (1 / 28.317), 35.315, 1.0, (1 / 1728.0), (1 / 7.481)),   // Кубические футы
        arrayOf( (1 /16.3871), 61.024, 61020.0, 1728.0, 1.0, 231.0),                    // Кубические дюймы
        arrayOf((1 / 3785.0), (1 / 3.785), 264.2, 7.48, (1 / 231.0), 1.0)               // Американские галлоны
    )
    val frequencyConvert : Array<Array<Double>> = arrayOf(
        arrayOf(1.0, 1e3, 1e6, 1e9),           // Герц
        arrayOf(1e-3, 1.0, 1e3, 1e6),          // Килогерц
        arrayOf(1e-6, 1e-3, 1.0, 1e3),         // Мегагерц
        arrayOf(1e-9, 1e-6, 1e-3, 1.0)         // Гигагерц
    )
    val fuelConsumptionConvert : Array<Array<Double>> = arrayOf(
        arrayOf(1.0, 0.01, 0.425144),          // Километры на литр
        arrayOf(0.01, 1.0, 0.00425144),        // Литры на 100 километров
        arrayOf(2.352, .00425144, 1.0)         // Мили на 100 галлон США
    )

/* Принцип работы с массивами метода dataToConvert():
   Предположим, что входные данные представлены в единицах "сантиметр", и их необходимо конвертировать в единицы "километр".
   В данном контексте, "сантиметр" - это величина, подлежащая конвертации, а "километр" - целевая единица измерения.
   Массив задает соотношение между первым аргументом и каждым последующим:
       arrayOf(1, 0.01, 1e-5) для конвертации в сантиметры,
       arrayOf(100, 1, 0.001) для конвертации в метры,
       arrayOf(1e5, 1000, 1) для конвертации в километры.
   Эта матрица имеет структуру, схожую с диагональной матрицей:
           [1 0 0 0]
           [0 1 0 0]
           [0 0 1 0]
           [0 0 0 1]    и тд.
*/

}