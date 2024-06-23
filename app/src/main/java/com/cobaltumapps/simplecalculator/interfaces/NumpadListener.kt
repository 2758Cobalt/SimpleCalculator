package com.cobaltumapps.simplecalculator.interfaces

interface NumpadListener{
    fun onClickNumberButton(number: String)
    fun onClickPointButton()
    fun onClickAllClearButton()
    fun onClickBackSpaceButton()
}