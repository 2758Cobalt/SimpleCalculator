package com.example.kotlin_calculator.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class ButtonTile: AppCompatButton {
    constructor(context: Context) : super(context) {
        // Код для программного создания кнопки
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // Код для создания кнопки с использованием XML-макета
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // Код для создания кнопки с использованием XML-макета с атрибутами стиля
        initialize()
    }

    private fun initialize() {
        // Дополнительные настройки кнопки, если необходимо
        // Например, установка фонового цвета, размера текста и т.д.
    }
}