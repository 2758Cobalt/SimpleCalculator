package com.example.kotlin_calculator

import android.util.Log
import android.widget.EditText
import java.util.Locale
import kotlin.math.sqrt

object MathFormulas {

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
     * @param dataFields Список EditText, представляющий три стороны параллелепипеда.
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
     * @param dataFields Список EditText, представляющий три стороны параллелепипеда.
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
     * @param dataFields Список EditText, представляющий три стороны параллелепипеда.
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
     * @param dataFields Список EditText, представляющий три стороны параллелепипеда.
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


}
