package com.cobaltumapps.simplecalculator.references

import com.cobaltumapps.simplecalculator.R

object ConstantsCalculator {
    const val symbolAdd = '+'
    const val symbolSub = '-'
    const val symbolMul = '×'
    const val symbolDiv = '/'
    const val symbolPower = '^'
    const val symbolPercent = '%'
    const val symbolFactorial = '!'
    const val symbolSqrt = '√'

    const val symbolOpenBracket = '('
    const val symbolCloseBracket = ')'
    const val symbolPoint = '.'
    const val symbolEqual = '='

    const val privacyPolicyLink = "https://sites.google.com/view/cobaltumappsgroup/privacy-policy"
    const val telegramLink = "https://t.me/CobaltBo1"

    // Массивы хранящие символы
    val operatorConstants: List<String> by lazy { listOf("$symbolAdd", "$symbolSub", "$symbolMul", "$symbolDiv", "$symbolEqual","$symbolPoint","$symbolOpenBracket","$symbolCloseBracket") }  // Символы для операций
    val operandConstants: List<String> by lazy { listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9") }  // Символы для кнопок
    //val functionalConstants: Array<String> by lazy { arrayOf("x&#178;","√x","x!","±") }  // Символы для кнопок

    val keysPreferences = listOf("key_memoryAutoSave","key_leftHandMode","key_miniKeyboardMode","key_vibration")
    const val vault = "preferences"

    // Создание ссылок на кнопки numpad
    val numberButtonIds by lazy { listOf(
        R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5,
        R.id.num6, R.id.num7, R.id.num8, R.id.num9)
    }

    // Создание ссылок на кнопки операций
    val operatorButtonIds by lazy { listOf(R.id.addBtn, R.id.subBtn,
                                    R.id.mulBtn, R.id.divBtn)}

}