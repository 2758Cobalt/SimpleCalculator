package com.example.kotlin_calculator.references

class Memory {
    private lateinit var memoryStorage: String
    var memoryAutoSave: Boolean = false

    fun save(expression: String){
        memoryStorage = expression
    }
    fun read() : String{
        return memoryStorage
    }

    fun clear(){
        memoryStorage = ""
    }

    private fun checkStorage() : Boolean{
        if (memoryStorage.isEmpty()) return false
        if (memoryStorage.isNotEmpty()) return true
        return false
    }

    private fun addToResult(currentResult: Double): Double {
        return currentResult + memoryStorage.toDouble()
    }

    private fun subToResult(currentResult: Double): Double {
        return currentResult - memoryStorage.toDouble()
    }

    private fun mulToResult(currentResult: Double): Double {
        return currentResult * memoryStorage.toDouble()
    }

    private fun divToResult(currentResult: Double): Double {
        return currentResult / memoryStorage.toDouble()
    }
}