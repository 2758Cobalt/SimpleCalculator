package com.example.kotlin_calculator.references

import com.example.kotlin_calculator.R

object ConstantsCalculator {

    // Массивы хранящие символы
    val operatorConstants: Array<String> by lazy { arrayOf("+", "-", "*", "/", "=",".","(",")") }  // Символы для операций
    val operandConstants: Array<String> by lazy { arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9") }  // Символы для кнопок

    // Создание ссылок на кнопки numpad
    val numberButtonIds by lazy { arrayOf(
        R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5,
        R.id.num6, R.id.num7, R.id.num8, R.id.num9)}

    // Создание ссылок на кнопки операций
    val operatorButtonIds by lazy { arrayOf(R.id.addBtn, R.id.subBtn,
                                    R.id.mulBtn, R.id.divBtn)}

    // Ссылка на кнопки функций калькулятора
    val functionalButtonIds by lazy { arrayOf(
        R.id.buttonClearAllAction, R.id.buttonRoundAction, R.id.buttonEqualAction,
        R.id.buttonPointAction, R.id.buttonBackSpaceAction, R.id.buttonRoundBracketOpen,
        R.id.buttonRoundBracketClose, R.id.buttonInvertNumber, R.id.buttonPowerNumber,
        R.id.buttonSqrt)}

    // Создание ссылок на дополнительные кнопки
    val additionalButtonIds by lazy { arrayOf(
        R.id.buttonMemoryAdd, R.id.buttonMemoryRead, R.id.buttonMemoryClear,
        R.id.buttonAutoMemorySave,
        R.id.buttonMemoryResultAdd, R.id.buttonMemoryResultSub,
        R.id.buttonMemoryResultMul, R.id.buttonMemoryResultDiv)}
}