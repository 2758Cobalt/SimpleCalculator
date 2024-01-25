package com.example.kotlin_calculator.references

import android.util.Log

class Memory {
    private var memoryStorage: Double = 0.0
    private val LOG_TAG = "Memory"

    fun save(newValue: Double){
        memoryStorage = newValue
    }

    fun read() : Double{
        return memoryStorage
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