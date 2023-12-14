package com.example.kotlin_calculator

import android.util.Log
import android.widget.EditText

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

        val s = 2 * (sideA * sideB + sideA * sideC + sideB * sideC)  // Площадь полной поверхности
        val sb = 2 * sideA * sideB + 2 * sideA * sideC                  // Площадь боковой поверхности
        val v = sideA * sideB * sideC                                 // Объём

        if(log){ // LOG
            Log.i(debugTag, "Текущий массив с полями: $dataFields")
            Log.i(
                debugTag, "Стороны параллелепипеда:\n A: ${dataFields[0].text}\n" +
                        " B: ${dataFields[1].text}\n C: ${dataFields[2].text}")
            Log.i(
                debugTag, "Результаты расчётов:\n" +
                        "Площадь: $s\n" +
                        "Бок. Площадь: $sb\n" +
                        "Объём $v")
        }

        return arrayOf(s, sb, v) // Взвращает все расчёты массивом типа double
    }

    /**
     * Функция calculateCubeProperties принимает сторону куба в виде EditText и вычисляет её переводом
     * в тип double. Затем функция вычисляет площадь полной поверхности, площадь одной грани и объем куба.
     *
     * @param sideField EditText, представляющий сторону куба.
     * @param log Флаг для включения/выключения вывода отладочной информации в Log.
     *
     * @return Массив типа Double, содержащий вычисленные значения площади полной поверхности, площади
     * одной грани и объема куба.
     */
    fun calculateCubeProperties(sideField: EditText, log: Boolean, debugTag: String): Array<Double> {

        val side = sideField.text.toString().toDouble()

        val s = 6 * side * side     // Площадь полной поверхности
        val sa = 4 * (side * side)        // Площадь одной грани
        val v = side * side * side  // Объем

        if (log) { // LOG
            Log.i(debugTag, "Текущая сторона куба: ${sideField.text}")
            Log.i(
                debugTag, "Результаты расчётов:\n" +
                        "Площадь: $s\n" +
                        "Площадь одной грани: $sa\n" +
                        "Объём $v"
            )
        }

        return arrayOf(s, sa, v) // Возвращает все расчёты массивом типа double
    }

}
