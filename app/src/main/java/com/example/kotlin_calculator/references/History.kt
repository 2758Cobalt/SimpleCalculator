package com.example.kotlin_calculator.references

class History {
    private var historyResultStorage: Array<String> = arrayOf()
    private var historyExpressionStorage: Array<String> = arrayOf()

    fun saveToHistory(expression: String, result: String){
        historyResultStorage += result
        historyExpressionStorage += expression
    }

    fun getResult(index: Int): String{
        return historyResultStorage[index]
    }
    fun getExpressionFromHistory(index: Int): String{
        return historyExpressionStorage[index]
    }

    fun getAllResult():  Array<String>{
        return historyResultStorage
    }
    fun getAllExpressionns(): Array<String> {
        return historyExpressionStorage
    }
}