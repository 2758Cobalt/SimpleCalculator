package com.cobaltumapps.simplecalculator.converterNavigation

class UnitModel {
    private var title: String
    private var result: String
    private var isSelected: Boolean

    constructor(title: String, result: String, isSelected: Boolean){
        this.title = title
        this.result = result
        this.isSelected = isSelected
    }

    fun getTitle(): String{
        return this.title
    }

    fun getResult(): String{
        return this.result
    }

    fun setResult(newResult: String){
        this.result = newResult
    }

    fun setSelectedState(newState: Boolean){
        this.isSelected = newState
    }

    fun getSelection(): Boolean{
        return this.isSelected
    }



}