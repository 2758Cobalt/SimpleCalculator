package com.example.kotlin_calculator

object Constants {
    // Данный файл представляет собой набор ссылок и массивов для классов программы.
    // Файл созданый с целью отделить содержимое, улучшив читабельность кода

    // Набор данных, нужных для метода dataSetIdGetting() класса ConverterFragment.kt
    val constantsConvertsData: Array<Triple<Int, Int, Int>> = arrayOf(
        Triple(R.string.label_weightText, R.string.tooltip_weightText, R.array.weight_units),
        Triple(R.string.label_lengthText, R.string.tooltip_lengthText, R.array.length_units),
        Triple(R.string.label_speedText, R.string.tooltip_speedText, R.array.speed_units),
        Triple(R.string.label_dataText, R.string.tooltip_dataText, R.array.data_units),
        Triple(R.string.label_temperatureText, R.string.tooltip_temperatureText, R.array.temperature_units),
        Triple(R.string.label_volumeText, R.string.tooltip_volumeText, R.array.volume_units),
        Triple(R.string.label_frequencyText, R.string.tooltip_frequencyText, R.array.frequency_units),
        Triple(R.string.label_fuelConsumptionText, R.string.tooltip_fuelConsumptionText, R.array.fuelConsumption_units),
        Triple(R.string.label_pressureText, R.string.tooltip_fuelConsumptionText, R.array.pressure_units),
        Triple(R.string.label_powerText, R.string.tooltip_powerText, R.array.power_units),
        Triple(R.string.label_energyText, R.string.tooltip_energyText, R.array.energy_units))

    val constantsAlgebraData: Array<Array<Int>> = arrayOf(
        arrayOf(R.string.label_parallelepiped, R.string.tooltip_parallelepipedText),
        arrayOf(R.string.label_dataText, R.string.tooltip_dataText)
    )


    // Нужны для метода dataToConvert() класса ConverterFragment.kt
    val weightConvert : Array<Array<Double>> = arrayOf(
        //      Микрограммы     Миллиграммы     Граммы  Килограммы  Тонны   Килотонны   Мегатонны
        arrayOf(1.0,            1e3,            1e6,    1e9,        1e12,   1e15,       1e18),     //Микрограммы
        arrayOf(0.001,          1.0,            1000.0, 1e6,        1e9,    1e12,       1e15),     //Миллиграммы
        arrayOf(1e-6,           1e-3,           1.0,    1e3,        1e6,    1e9,        1e12),     // Граммы
        arrayOf(1e-9,           1e-6,           1e-3,   1.0,        1e3,    1e6,        1e9 ),     // Килограммы
        arrayOf(1e-12,          1e-9,           1e-6,   1e-3,       1.0,    1e3,        1e6 ),     // Тонны
        arrayOf(1e-15,          1e-12,          1e-9,   1e-6,       1e-3,   1.0,        1e3 ),     // Килотонны
        arrayOf(1e-18,          1e-15,          1e-12,  1e-9,       1e-6,   1e-3,       1.0)       // Мегатонны
    )
    val lengthConvert: Array<Array<Double>> = arrayOf(
        //      Нанометры   Микрометры   Миллиметры  Сантиметры  Дециметры Метры  Километры
        arrayOf(1.0,        1000.0,      1e6,        1e7,        1e8,      1e9,   1e12),  // Нанометры
        arrayOf(0.001,      1.0,         1000.0,     1e4,        1e5,      1e6,   1e9),   // Микрометры
        arrayOf(1e-6,       1e-3,        1.0,        1e-1,       1e-2,     1e-3,  1e-6),  // Миллиметры
        arrayOf(1e-7,       1e-4,        1e-1,       1.0,        1e-1,     1e-2,  1e-5),  // Сантиметры
        arrayOf(1e-8,       1e-5,        1e-2,       1e-1,       1.0,      1e-1,  1e-4),  // Дециметры
        arrayOf(1e-9,       1e-6,        1e-3,       1e-2,       1e-1,     1.0,   1e-4),  // Метры
        arrayOf(1e-12,      1e-9,        1e-6,       1e-5,       1e-4,     1e-3,  1.0)    // Километры
    )
    val speedConvert: Array<Array<Double>> = arrayOf(
        //      Скорость света      М/с             Км/ч             Км/с              М/ч              Мл/с                 Уз
        arrayOf(1.0,                299792458.0,    1079252848.8,    299792.458,       670616629.3844,  186282.39705122,     582749918.36357),   // Скорость света (c=299 792 458)
        arrayOf(3.3356e-9,          1.0,            3.6,             1000.0,           2.23694,         1.94384,             3.28084),         // М/с (метры в секунду)
        arrayOf(9.2656693110598e-10, 0.277778,      1.0,             0.000277778,      0.621371,        0.539957,            0.911344),        // Км/ч (километры в час)
        arrayOf(3.3356409519815e-9,  1000.0,        3600.0,          1.0,              2236.9362920544, 1943.8444924574,     3280.84),          // Км/с (километры в секунду)
        arrayOf(1.4911649311738e-9,  0.44704,       1.609344,        0.00044704,       1.0,             0.00027,             0.868976),        // М/ч (миля в час)
        arrayOf(5.3681937522257e-10, 83333.0,       5793.6384,       1.609344,         3600.0,          1.0,                 3128.31447087),   // Мл/с (миля в секунду)
        arrayOf(1.7160019563934e-10, 0.5144,        1.851999999984,  0.000514,         1.1507794480136, 0.00031966095778156, 1.0)             // Уз (узлы)
    )
    val dataConvert: Array<Array<Double>> = arrayOf(
        //      Байт        КилоБайт    МегаБайт   ГигаБайт   ТераБайт   ПетаБайт   ЭксаБайт
        arrayOf(1e0,        1e-3,       1e-6,      1e-9,      1e-12,     1e-15,     1e-18),        // Байт
        arrayOf(1e3,        1e0,        1e-3,      1e-6,      1e-9,      1e-12,     1e-15),        // КилоБайт
        arrayOf(1e6,        1e3,        1e0,       1e-3,      1e-6,      1e-9,      1e-12),        // МегаБайт
        arrayOf(1e9,        1e6,        1e3,       1e0,       1e-3,      1e-6,      1e-9),         // ГигаБайт
        arrayOf(1e12,       1e9,        1e6,       1e3,       1e0,       1e-3,      1e-6),         // ТераБайт
        arrayOf(1e15,       1e12,       1e9,       1e6,       1e3,       1e0,       1e-3),         // ПетаБайт
        arrayOf(1e18,       1e15,       1e12,      1e9,       1e6,       1e3,       1e0)           // ЭксаБайт
    )
    val temperatureConvert: Array<Array<Double>> = arrayOf(
        //      Цельсий     Фаренгейт      Кельвин      Ранкин       Реомюр
        arrayOf(1.0,        (9.0 / 5.0),   273.15,      273.15 * 1.8, (4.0 / 5.0)),          // Цельсий
        arrayOf((5.0 / 9.0), 1.0,           459.67 * (5.0 / 9.0), 459.67,       (4.0 / 9.0)),  // Фаренгейт
        arrayOf(1.0,        9.0 / 5.0,      1.0,         9.0 / 5.0,    4.0 / 5.0),            // Кельвин
        arrayOf((5.0 / 9.0), 1.0 - 459.67,  5.0 / 9.0,    1.0,          4.0 / 9.0),            // Ранкин
        arrayOf(5.0 / 4.0,   (9.0 / 4.0),   5.0 / 4.0 + 273.15, 9.0 / 4.0 + 491.67, 1.0)    // Реомюр
    )
    val volumeConvert: Array<Array<Double>> = arrayOf(
        //      Миллилитры  Литры         Кубические метры  Кубические футы   Кубические дюймы  Американские галлоны
        arrayOf(1.0,        1e3,          1e6,              28320.0,           16.387,            3785.4),              // Миллилитры
        arrayOf(1e-3,       1.0,          1e3,              28.317,            (1 / 61.024),        3.7854),              // Литры
        arrayOf(1e-6,       1e-3,         1.0,              (1 / 35.315),       (1 / 61020.0),      (1 /  264.2)),         // Кубические метры
        arrayOf((1 / 28320.0), (1 / 28.317), 35.315,        1.0,               (1 / 1728.0),       (1 / 7.481)),          // Кубические футы
        arrayOf((1 /16.3871), 61.024,       61020.0,         1728.0,            1.0,               231.0),               // Кубические дюймы
        arrayOf((1 / 3785.0),  (1 / 3.785),  264.2,          7.48,              (1 / 231.0),        1.0)                 // Американские галлоны
    )
    val frequencyConvert: Array<Array<Double>> = arrayOf(
        //      Герц         Килогерц     Мегагерц    Гигагерц
        arrayOf(1.0,         1e3,         1e6,         1e9),        // Герц
        arrayOf(1e-3,        1.0,         1e3,         1e6),        // Килогерц
        arrayOf(1e-6,        1e-3,        1.0,         1e3),        // Мегагерц
        arrayOf(1e-9,        1e-6,        1e-3,        1.0)         // Гигагерц
    )
    val fuelConsumptionConvert: Array<Array<Double>> = arrayOf(
        //      Километры на литр   Литры на 100 км         Мили на 100 галлон США
        arrayOf(1.0,                0.01,                   0.425144),            // Километры на литр
        arrayOf(0.01,               1.0,                    0.00425144),          // Литры на 100 километров
        arrayOf(2.352,              0.00425144,             1.0)                  // Мили на 100 галлон США
    )
    val pressureConvert: Array<Array<Double>> = arrayOf(
        //    Атмосфер      Бар           Паскаль
        arrayOf(1.0,        (1 / 1.013),   (1 / 101325.0)), // Атмосфер
        arrayOf(1.013,      1.0,           (1 / 1e5)),      // Бар
        arrayOf(101325.0,   1e5,           1.0)             // Паскаль
    )
    val powerConvert: Array<Array<Double>> = arrayOf(
        //     Ватт          Киловатт   Мегаватт   Гигаватт    Лошадиная сила
        arrayOf(1.0,         1000.0,    1e6,       1e9,        735.5),              // Ватт
        arrayOf(0.001,       1.0,       1000.0,    1e6,       (1 / 1.341)),         // Киловатт
        arrayOf(1e-6,        0.001,     1.0,        1000.0,      (1 / 1341.0)),     // Мегаватт
        arrayOf(1e-9,        1e-6,      0.001,    1.0,        (1 / 1.341e6)),       // Гигаватт
        arrayOf((1 / 745.7), 1.341,     1341.0,     1.341e6,  1.0)                  // Лошадиная сила
    )
    val energyConvert: Array<Array<Double>> = arrayOf(
        //     Джоуль       Калория     Килокалория   Ватт-час  Квт-час     Электронвольт   БТЕ         Терм        Фут-фунт    Эрг          Час  лошадь. силы
        arrayOf(1.0,        4.184,      4184.0,       3600.0,   3.6e6,      (1 / 6.42e18),  1055.0,     105587000.0,1.356,      1e7,         (1 / 3.776726714733e-7)),     // Джоуль
        arrayOf(4.184,      1.0,        1e-3,         1.16e-6,  3.27e-10,   2.62e+18,       3.965,      1e-5,       308.9,      4.184e7,     1e-24),        // Калория
        arrayOf(4.184e3,    1e3,        1.0,          1.16e-3,  3.27e-7,    2.62e+21,       3.965e3,    1e-2,       308900.0,   4.184e10,    1e-21),        // Килокалория
        arrayOf(3.6e6,      8.6e5,      860.0,        1.0,      2.78e-4,    2.24e+22,       3.412e6,    8.6e-3,     2.655e6,    3.6e13,      8.6e-20),      // Ватт-час
        arrayOf(3.6e9,      8.6e8,      8.6e5,        1e3,      1.0,        2.24e+25,       3.412e9,    8.6,        2.655e9,    3.6e16,      8.6e-17),      // Киловатт-час
        arrayOf(1.602e-19,  3.827e-20,  3.827e-23,    4.45e-26, 1.24e-29,   1.0,            1.518e-22,  3.827e-31,  1.181e-23,  1.602e-12,   3.827e-38),    // Электронвольт
        arrayOf(1.055e3,    2.52e2,     2.52e-1,      2.93e-4,  8.21e-8,    6.59e+21,       1.0,        2.52e-5,    778.2,      1.055e10,    2.52e-22),     // БТЕ
        arrayOf(1.055e11,   2.52e10,    2.52e7,       2.93e4,   8.21e-1,    6.59e+24,       1.0e4,      2.52e-1,    7.782e7,    1.055e14,    2.52),         // Терм
        arrayOf(1.3558,     3.24e-1,    3.24e-4,      3.77e-7,  1.05e-10,   8.42e+17,       1.2851e-3,  3.24e-8,    1.0,        1.3558e6,    3.24e-14),     // Фут-фунт
        arrayOf(1e-7,       2.39e-8,    2.39e-11,     2.78e-14, 7.78e-18,   6.24e+11,       9.48e-8,    2.39e-15,   7.38e-5,    1e-3,        2.39e-21),     // Эрг
        arrayOf(1.3558e6,   3.24e5,     3.24e2,       3.77e-1,  1.05e-4,    8.42e+23,       1.2851e3,   3.24e-2,    1.0e3,      1.3558,      3.24e-11)      // Час лошадиной силы
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