package com.example.kotlin_calculator.references

import android.content.Context
import com.example.kotlin_calculator.R

object ConstantsCalculator {

    // Массивы хранящие символы
    val operatorConstants: Array<String> by lazy { arrayOf("+", "-", "*", "/", "=",".","(",")") }  // Символы для операций
    val operandConstants: Array<String> by lazy { arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9") }  // Символы для кнопок
    //val functionalConstants: Array<String> by lazy { arrayOf("x&#178;","√x","x!","±") }  // Символы для кнопок

    val keysPreferences = arrayOf("key_memoryAutoSave","key_leftHandMode","key_expandKeyboardMode")
    val vault = "preferences"

    // Создание ссылок на кнопки numpad
    val numberButtonIds by lazy { arrayOf(
        R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5,
        R.id.num6, R.id.num7, R.id.num8, R.id.num9)}

    // Создание ссылок на кнопки операций
    val operatorButtonIds by lazy { arrayOf(R.id.addBtn, R.id.subBtn,
                                    R.id.mulBtn, R.id.divBtn)}


    val stringResourcesTrigonometryDesc by lazy { arrayOf(
        R.string.text_EnterSin,
        R.string.text_EnterCos,
        R.string.text_EnterTan,
        R.string.text_EnterCtg,
        R.string.text_EnterExp
    ) }
}