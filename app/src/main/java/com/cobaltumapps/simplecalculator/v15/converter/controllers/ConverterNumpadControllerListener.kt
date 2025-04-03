package com.cobaltumapps.simplecalculator.v15.converter.controllers

interface ConverterNumpadControllerListener {
    fun receiveUserEntry(userEntry: String)
    fun confirmEntry()
}