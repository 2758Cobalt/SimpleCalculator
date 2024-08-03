package com.cobaltumapps.simplecalculator.interfaces

interface NumpadListener{
    fun onClickNumberButton(number: String)
    fun onClickPointButton()
    fun onClickAllClearButton()
    fun onClickBackSpaceButton()
    fun onClickEnterButton()
}

// Расширяет базовый интерфейс
interface AdvancedNumpadListener: NumpadListener{
    fun onClickMemorySave()
    fun onClickMemoryRead()
    fun onClickMemoryClear()
}