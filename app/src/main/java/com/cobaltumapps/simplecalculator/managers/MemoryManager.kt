package com.cobaltumapps.simplecalculator.managers

class MemoryManager {
    private var memoryStorage: Double = 0.0
    var isDouble = true
    private val LOG_TAG = "MemoryManager"

    fun save(newValue: Double){
        memoryStorage = newValue
        isDouble = true
    }
    fun save(newValue: Int){
        memoryStorage = newValue.toDouble()
        isDouble = false
    }

    fun read() : String{
        return if (isDouble){
            memoryStorage.toString()
        }
        else{
            memoryStorage.toInt().toString()
        }
    }

    fun clear(){
        memoryStorage = 0.0
    }


    fun addToResult(currentResult: Double): Double {
        val result = memoryStorage + currentResult
        save(result)
        return result
    }

    fun subToResult(currentResult: Double): Double {
        val result = memoryStorage - currentResult
        save(result)
        return result
    }

    fun mulToResult(currentResult: Double): Double {
        val result = memoryStorage * currentResult
        save(result)
        return result
    }

    fun divToResult(currentResult: Double): Double {
        val result = memoryStorage / currentResult
        save(result)
        return result
    }
}