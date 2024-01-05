package com.example.kotlin_calculator.references

import java.util.Stack
import kotlin.math.pow
import kotlin.math.sqrt

object CalculatorCore {
    private val dublicateConstants = setOf('+', '-', '*', '/','.')

    // Функция для выполнения основных математических операций на основе оператора
    private fun algorithmParsingExpression(stackOperand: Stack<Double>, stackOperator: Stack<Char>) {
        val a: Double
        val b: Double
        val c: Double

        // Проверка, что стек операторов не пуст
        if (stackOperator.isNotEmpty()) {
            // Выполнение операций на основе верхнего оператора в стеке
            when (stackOperator.peek()) {
                '+' -> {
                    a = stackOperand.pop()
                    b = stackOperand.pop()
                    c = a + b
                    stackOperand.push(c)
                }
                '-' -> {
                    a = stackOperand.pop()
                    b = stackOperand.pop()
                    c = b - a
                    stackOperand.push(c)
                }
                '*' -> {
                    a = stackOperand.pop()
                    b = stackOperand.pop()
                    c = a * b
                    stackOperand.push(c)
                }
                '/' -> {
                    a = stackOperand.pop()
                    b = stackOperand.pop()
                    c = b / a
                    stackOperand.push(c)
                }
                '^' ->{
                    a = stackOperand.pop()
                    b = stackOperand.pop()
                    c = b.pow(a)
                    stackOperand.push(c)
                }
                '√' -> {
                    b = stackOperand.pop()
                    c = sqrt(b)
                    stackOperand.push(c)
                }
            }
            stackOperator.pop() // Убираем обработанный оператор из стека
        }
    }

    // Функция для вычисления значения математического выражения
    fun calculateExpression(expression: String) : Double{
            val stackOperands = Stack<Double>() // Локальный стек с операндами(числами,аргументом операции) 0..9
            val stackOperators = Stack<Char>() // Локальный стек с операторами +-*/

            val currentNumber = StringBuilder() // Строка с числом, которая помещается в стек с переводом в double(нужно для работы с числами)
            val newExpression = analyzeExpression(expression)

            // Итерация по каждому символу в выражении (тут for во избежании бесконечного цикла)
            for (x in newExpression) {
                when {
                    x.isDigit() || x == '.' -> currentNumber.append(x) // Добавляем цифры и точки, чтобы сформировать число
                    x == '(' -> stackOperators.push(x) // Помещаем открывающую скобку в стек операторов
                    x == ')' -> {
                        if (currentNumber.isNotEmpty()) {
                            stackOperands.push(currentNumber.toString().toDouble())
                            currentNumber.clear()
                        }
                        // Перебирает операторы до открывающей скобки
                        while (stackOperators.isNotEmpty() && stackOperators.peek() != '(') {
                            algorithmParsingExpression(stackOperands, stackOperators)
                        }
                        if (stackOperators.isNotEmpty() && stackOperators.peek() == '(') {
                            stackOperators.pop() // Удаляет '(' из стека
                        }
                    }
                    else -> {
                        if (currentNumber.isNotEmpty()) {
                            stackOperands.push(currentNumber.toString().toDouble())
                            currentNumber.clear()
                        }
                        // Перебирает операторы в зависимости от приоритета
                        while (stackOperators.isNotEmpty() && getPrecedence(x) <= getPrecedence(stackOperators.peek())) {
                            algorithmParsingExpression(stackOperands, stackOperators)
                        }
                        stackOperators.push(x) // Добавление оператора в стек
                    }
                }
            }

            if (currentNumber.isNotEmpty()) {
                stackOperands.push(currentNumber.toString().toDouble())
            }

            // Перебирает оставшиеся операторы
            while (stackOperators.isNotEmpty()) {
                algorithmParsingExpression(stackOperands, stackOperators)
            }

            return stackOperands.peek()

    }

    // Функция для определения приоритета оператора
    private fun getPrecedence(operator: Char): Int {
        return when (operator) {
            '+', '-' -> 1
            '*', '/' -> 2
            '^' -> 3
            '√' -> 4
            else -> 0  // Для '('
        }
    }

    private fun analyzeExpression(input: String): String {
        // Данная функция предоставляет набор проверок и правок для строки с выражением
        val stringBuilder = StringBuilder(input)


        // Находит скобку, и если отсувствует знак * перед скобкой, он его добавляет
        for (i in 1 until stringBuilder.length) {
            if (stringBuilder[i] == '(' && stringBuilder[i - 1] != '*') {
                stringBuilder.insert(i, '*')
            }
            if (stringBuilder[i] == '√' && stringBuilder[i - 1] !in setOf('*','(') && stringBuilder[i - 1] != '^') {
                stringBuilder.insert(i, '*')
            }
        }
        val indicesToRemove = mutableListOf<Int>()

        for (i in stringBuilder.length - 1 downTo 1) {
            if (stringBuilder[i] in dublicateConstants && stringBuilder[i - 1] in dublicateConstants) {
                indicesToRemove.add(i)
            }
        }
        // Удаление символов из StringBuilder по индексам
        indicesToRemove.forEach { stringBuilder.deleteCharAt(it) }
        return stringBuilder.toString()  // Возврат обработаной строки
    }
}