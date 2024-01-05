package com.example.kotlin_calculator.references

import android.util.Log
import android.widget.EditText
import java.util.Locale
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.tan

object GeometricSolidsFormulas {

    /**
     * Функция calculateParallelepipedProperties принимает список EditText, представляющий три стороны
     * параллелепипеда, и вычисляет их переводом в тип double. Затем функция вычисляет площадь полной
     * поверхности, боковую площадь и объем параллелепипеда.
     *
     * @param dataFields Список EditText, представляющий три стороны параллелепипеда.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади полной поверхности, боковой
     * поверхности и объема.
     */

    fun calculateParallelepipedProperties(dataFields: List<EditText>, log: Boolean, debugTag: String) : Array<Double> {
        val sideA = dataFields[0].text.toString().toDouble()
        val sideB = dataFields[1].text.toString().toDouble()
        val sideC = dataFields[2].text.toString().toDouble()

        val s = 2 * (sideA * sideB + sideA * sideC + sideB * sideC)     // Площадь полной поверхности
        val sl = 2 * sideA * sideB + 2 * sideA * sideC                  // Площадь боковой поверхности
        val v = sideA * sideB * sideC                                   // Объём

        if(log){ // LOG
            Log.i(debugTag, "Текущий массив с полями: $dataFields")
            Log.i(
                debugTag, "Стороны параллелепипеда:\n A: ${dataFields[0].text}\n" +
                        "B: ${dataFields[1].text}\n C: ${dataFields[2].text}")
            Log.i(
                debugTag, "Результаты расчётов:\n" +
                        "Площадь: $s\n" +
                        "Бок. Площадь: $sl\n" +
                        "Объём $v")
        }

        return arrayOf(
            String.format(Locale.US, "%.${3}f", s).toDouble(),
            String.format(Locale.US, "%.${3}f", sl).toDouble(),
            String.format(Locale.US, "%.${3}f", v).toDouble())// Взвращает все расчёты массивом типа double
    }

    /**
     * Функция calculatePyramidProperties принимает EditText'ы для основания и высоты пирамиды,
     * вычисляет их перевод в тип double, а затем вычисляет площадь поверхности,
     * площадь боковой поверхности и объем пирамиды.
     *
     * @param dataFields Список EditText, представляющий сторону основы и высоту пирамиды.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     * @param debugTag Тег для отладочного вывода в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади поверхности,
     * площади боковой поверхности и объема пирамиды.
     */

    fun calculatePyramidProperties(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val base = dataFields[0].text.toString().toDouble()
        val height = dataFields[1].text.toString().toDouble()

        val s = base * (base + sqrt(base * base + 4 * height * height))  // Площадь поверхности
        val sl = base * sqrt(height * height + (base * base) / 4.0)      // Площадь боковой поверхности
        val v = (base * base * height) / 3.0                                // Объем

        if (log) {
            Log.i(debugTag, "Текущая база пирамиды: ${dataFields[0].text}, Высота пирамиды: ${dataFields[1].text}")
            Log.i(debugTag, "Результаты расчётов:\n" +
                    "Площадь поверхности: $s\n" +
                    "Площадь боковой поверхности: $sl\n" +
                    "Объём: $v")
        }
        return arrayOf(
            String.format(Locale.US, "%.${3}f", s).toDouble(),
            String.format(Locale.US, "%.${3}f", sl).toDouble(),
            String.format(Locale.US, "%.${3}f", v).toDouble())
    }

    /**
     * Функция calculateConeProperties принимает EditText'ы для радиуса основания и высоты конуса,
     * вычисляет их перевод в тип double, а затем вычисляет площадь поверхности,
     * площадь боковой поверхности и объем конуса.
     *
     * @param dataFields Список EditText, представляющий радиус и высоту конуса.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     * @param debugTag Тег для отладочного вывода в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади поверхности,
     * площади боковой поверхности и объема конуса.
     */

    fun calculateConeProperties(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val radius = dataFields[0].text.toString().toDouble()
        val height = dataFields[1].text.toString().toDouble()

        val s = Math.PI * radius * (radius + sqrt(radius * radius + height * height))  // Площадь поверхности
        val sl = Math.PI * radius * sqrt(radius * radius + height * height)            // Площадь боковой поверхности
        val v = (Math.PI * radius * radius * height) / 3.0                                // Объем

        if (log) {
            Log.i(debugTag, "Текущий радиус конуса: ${dataFields[0].text}, Высота конуса: ${dataFields[1].text}")
            Log.i(debugTag, "Результаты расчётов:\n" +
                    "Площадь поверхности: $s\n" +
                    "Площадь боковой поверхности: $sl\n" +
                    "Объём: $v"
            )
        }

        return arrayOf(
            String.format(Locale.US, "%.${3}f", s).toDouble(),
            String.format(Locale.US, "%.${3}f", sl).toDouble(),
            String.format(Locale.US, "%.${3}f", v).toDouble())
    }

    /**
     * Функция calculateCylinderProperties принимает EditText'ы для радиуса основания и высоты цилиндра,
     * вычисляет их перевод в тип double, а затем вычисляет площадь поверхности,
     * площадь боковой поверхности и объем цилиндра.
     *
     * @param dataFields Список EditText, представляющий радиус и высоту цилиндра.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     * @param debugTag Тег для отладочного вывода в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади поверхности,
     * площади боковой поверхности и объема цилиндра.
     */

    fun calculateCylinderProperties(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val radius = dataFields[0].text.toString().toDouble()
        val height = dataFields[1].text.toString().toDouble()

        val s = 2 * Math.PI * radius * (radius + height)      // Площадь поверхности
        val sl = 2 * Math.PI * radius * height                // Площадь боковой поверхности
        val v = Math.PI * radius * radius * height            // Объем

        if (log) {
            Log.i(debugTag, "Текущий радиус цилиндра: ${dataFields[0].text}, Высота цилиндра: ${dataFields[1].text}")
            Log.i(debugTag, "Результаты расчётов цилиндра:\n" +
                    "Площадь поверхности: $s\n" +
                    "Площадь боковой поверхности: $sl\n" +
                    "Объём: $v"
            )
        }

        return arrayOf(
            String.format(Locale.US, "%.${3}f", s).toDouble(),
            String.format(Locale.US, "%.${3}f", sl).toDouble(),
            String.format(Locale.US, "%.${3}f", v).toDouble())
    }

    /**
     * Функция calculateSphereProperties принимает EditText для радиуса сферы,
     * вычисляет его перевод в тип double, а затем вычисляет площадь поверхности и объем сферы.
     *
     * @param dataFields Список EditText, представляющий радиус сферы.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     * @param debugTag Тег для отладочного вывода в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади поверхности и объема сферы.
     */

    fun calculateSphereProperties(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val radius = dataFields[0].text.toString().toDouble()

        val s = 4 * Math.PI * radius * radius     // Площадь поверхности
        val v = (4.0 / 3.0) * Math.PI * radius * radius * radius  // Объем

        if (log) {
            Log.i(debugTag, "Текущий радиус сферы: ${dataFields[0].text}")
            Log.i(debugTag, "Результаты расчётов сферы:\n" +
                    "Площадь поверхности: $s\n" +
                    "Объём: $v")
        }
        return arrayOf(
            String.format(Locale.US, "%.${3}f", s).toDouble(),
            String.format(Locale.US, "%.${3}f", v).toDouble())
    }

    /**
     * Функция calculatePrismProperties принимает EditText'ы для длины стороны основания, высоты призмы
     * и количество боковых граней призмы (включая основание), вычисляет их перевод в тип double,
     * а затем вычисляет площадь поверхности и объем призмы.
     *
     * @param dataFields Список EditText, представляющий сторону, высоту и количество сторон призмы.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     * @param debugTag Тег для отладочного вывода в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади поверхности и объема призмы.
     */

    fun calculatePrismProperties(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val sideLength = dataFields[0].text.toString().toDouble()
        val height = dataFields[1].text.toString().toDouble()
        val numSides = dataFields[2].text.toString().toDouble()

        val perimeter = numSides * sideLength  // Периметр основания
        val apothem = sideLength / (2 * tan(Math.PI / numSides))  // Апофема (расстояние от центра основания до середины стороны)

        val s = numSides * (sideLength * apothem) / 2 + perimeter * height  // Площадь поверхности
        val v = (perimeter * apothem * height) / 2  // Объем

        if (log) {
            Log.i(debugTag, "Текущая длина стороны призмы: ${dataFields[0].text}, " +
                    "Высота призмы: ${dataFields[1].text}, Количество боковых граней: ${dataFields[2].text}")
            Log.i(debugTag, "Результаты расчётов призмы:\n" +
                    "Площадь поверхности: $s\n" +
                    "Объём: $v") }
        return arrayOf(
            String.format(Locale.US, "%.${3}f", s).toDouble(),
            String.format(Locale.US, "%.${3}f", v).toDouble())
    }
}

object GeometricFiguresFormulas {
    fun calculateRectangle(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val sideA = dataFields[0].text.toString().toDouble()
        val sideB = dataFields[1].text.toString().toDouble()

        val s = sideA * sideB
        val perimeter = (sideA + sideB) * 2
        val diagonal = sqrt(sideA.pow(2) + sideB.pow(2))

        if (log) {
            Log.i(debugTag, "Текущая длина стороны квадрата: ${dataFields[0].text}")

            // Результаты расчётов квадрата
            Log.i(debugTag, "Результаты расчётов квадрата:\n" +
                    "Площадь: $s\n" +
                    "Диагональ: $diagonal\n" +
                    "Периметр: $perimeter"
            )
        }

        return arrayOf(
            String.format(Locale.US, "%.${3}f", perimeter).toDouble(),
            String.format(Locale.US, "%.${3}f", diagonal).toDouble(),
            String.format(Locale.US, "%.${3}f", s).toDouble())
    }


    /**
     * Функция calculateRhombusProperties принимает EditText'ы для длины стороны ромба и высоты ромба,
     * вычисляет их перевод в тип double, а затем вычисляет площадь, периметр и диагонали ромба.
     *
     * @param dataFields Список EditText, представляющий сторону и высоту ромба.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     * @param debugTag Тег для отладочного вывода в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади, периметра и диагоналей ромба.
     */
    fun calculateRhombusProperties(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val sideLength = dataFields[0].text.toString().toDouble()
        val height = dataFields[1].text.toString().toDouble()

        val area = sideLength * height     // Площадь ромба
        val perimeter = 4 * sideLength     // Периметр ромба
        val diagonal1 = sqrt((sideLength / 2.0) * (sideLength / 2.0) + height * height) * 2  // Первая диагональ ромба
        val diagonal2 = sideLength * 2     // Вторая диагональ ромба (так как ромб имеет равные диагонали)

        if (log) {
            Log.i(debugTag, "Текущая длина стороны ромба: ${dataFields[0].text}, Высота ромба: ${dataFields[1].text}")
            Log.i(debugTag, "Результаты расчётов ромба:\n" +
                    "Площадь: $area\n" +
                    "Периметр: $perimeter\n" +
                    "Диагональ 1: $diagonal1\n" +
                    "Диагональ 2: $diagonal2"
            )
        }

        return arrayOf(
            String.format(Locale.US, "%.${3}f", area).toDouble(),
            String.format(Locale.US, "%.${3}f", perimeter).toDouble(),
            String.format(Locale.US, "%.${3}f", diagonal1).toDouble(),
            String.format(Locale.US, "%.${3}f", diagonal2).toDouble())
    }

    /**
     * Функция calculateTriangleProperties принимает EditText'ы для длин сторон треугольника,
     * вычисляет их перевод в тип double, а затем вычисляет площадь, периметр, высоту,
     * длины медиан и биссектрис треугольника.
     *
     * @param dataFields Список EditText, представляющий три стороны треугольника.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     * @param debugTag Тег для отладочного вывода в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади, периметра, высоты,
     * длины медиан и биссектрис треугольника.
     */

    fun calculateTriangleProperties(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val sideA = dataFields[0].text.toString().toDouble()
        val sideB = dataFields[1].text.toString().toDouble()
        val sideC = dataFields[2].text.toString().toDouble()

        val perimeter = sideA + sideB + sideC                               // Периметр треугольника
        val s = sqrt(perimeter / 2 * (perimeter / 2 - sideA) *
                (perimeter / 2 - sideB) * (perimeter / 2 - sideC))          // Площадь треугольника
        val height = 2 * s / sideA                                          // Высота треугольника из стороны A

        // Длины медиан треугольника
//        val medianA = 0.5 * sqrt(2 * sideB * sideB + 2 * sideC * sideC - sideA * sideA)
//        val medianB = 0.5 * sqrt(2 * sideA * sideA + 2 * sideC * sideC - sideB * sideB)
//        val medianC = 0.5 * sqrt(2 * sideA * sideA + 2 * sideB * sideB - sideC * sideC)

        // Длины биссектрис треугольника
//        val bisectorA = 2 * sqrt(sideB * sideC * perimeter * (perimeter - sideA)) /
//                (sideB + sideC)
//        val bisectorB = 2 * sqrt(sideA * sideC * perimeter * (perimeter - sideB)) /
//                (sideA + sideC)
//        val bisectorC = 2 * sqrt(sideA * sideB * perimeter * (perimeter - sideC)) /
//                (sideA + sideB)

        if (log) {
            Log.i(debugTag, "Текущие длины сторон треугольника: ${dataFields[0].text}, " +
                    "${dataFields[1].text}, ${dataFields[2].text}")
            Log.i(debugTag, "Результаты расчётов треугольника:\n" +
                    "Площадь: $s\n" +
                    "Периметр: $perimeter\n" +
                    "Высота: $height\n"
//                    "Длина медианы A: $medianA\n" +
//                    "Длина медианы B: $medianB\n" +
//                    "Длина медианы C: $medianC\n" +
//                    "Длина биссектрисы A: $bisectorA\n" +
//                    "Длина биссектрисы B: $bisectorB\n" +
//                    "Длина биссектрисы C: $bisectorC"
            )
        }

        return arrayOf(
            String.format(Locale.US, "%.${3}f", perimeter).toDouble(),
            String.format(Locale.US, "%.${3}f", height).toDouble(),
            String.format(Locale.US, "%.${3}f", s).toDouble())
    }

    /**
     * Функция calculateCircleProperties принимает EditText для радиуса круга,
     * вычисляет его перевод в тип double, а затем вычисляет площадь и длину окружности.
     *
     * @param radiusField EditText, представляющий радиус круга.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     * @param debugTag Тег для отладочного вывода в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади и длины окружности.
     */

    fun calculateCircleProperties(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val radius = dataFields[0].text.toString().toDouble()

        val area = Math.PI * radius * radius         // Площадь круга
        val circumference = 2 * Math.PI * radius     // Длина окружности

        if (log) {
            Log.i(debugTag, "Текущий радиус круга: ${dataFields[0].text}")
            Log.i(debugTag, "Результаты расчётов круга:\n" +
                    "Площадь: $area\n" +
                    "Длина окружности: $circumference")
        }

        return arrayOf(
            String.format(Locale.US, "%.${3}f", area).toDouble(),
            String.format(Locale.US, "%.${3}f", circumference).toDouble())
    }

    /**
     * Функция calculateTrapezoidProperties принимает EditText'ы для длин оснований трапеции,
     * длины боковых сторон и высоты трапеции, вычисляет их перевод в тип double,
     * а затем вычисляет площадь, периметр и высоту трапеции.
     *
     * @param baseAField EditText, представляющий длину основания A трапеции.
     * @param baseBField EditText, представляющий длину основания B трапеции.
     * @param side1Field EditText, представляющий длину боковой стороны трапеции.
     * @param side2Field EditText, представляющий длину боковой стороны трапеции.
     * @param heightField EditText, представляющий высоту трапеции.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     * @param debugTag Тег для отладочного вывода в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади, периметра и высоты трапеции.
     */

    fun calculateTrapezoidProperties(dataFields: List<EditText>, log: Boolean, debugTag: String): Array<Double> {
        val baseA = dataFields[0].text.toString().toDouble()
        val baseB = dataFields[1].text.toString().toDouble()
        val side1 = dataFields[2].text.toString().toDouble()
        val side2 = dataFields[3].text.toString().toDouble()
        val height = dataFields[4].text.toString().toDouble()

        val area = 0.5 * (baseA + baseB) * height      // Площадь трапеции
        val perimeter = baseA + baseB + side1 + side2   // Периметр трапеции

        if (log) {
            Log.i(debugTag, "Текущие длины оснований трапеции: ${dataFields[0].text}, ${dataFields[1].text}\n" +
                    "Длины боковых сторон: ${dataFields[2].text}, ${dataFields[3].text}\n" +
                    "Высота трапеции: ${dataFields[4].text}")
            Log.i(debugTag, "Результаты расчётов трапеции:\n" +
                    "Площадь: $area\n" +
                    "Периметр: $perimeter\n" +
                    "Высота: $height")
        }

        return arrayOf(
            String.format(Locale.US, "%.${3}f", area).toDouble(),
            String.format(Locale.US, "%.${3}f", perimeter).toDouble(),
            String.format(Locale.US, "%.${3}f", height).toDouble())
    }
}
