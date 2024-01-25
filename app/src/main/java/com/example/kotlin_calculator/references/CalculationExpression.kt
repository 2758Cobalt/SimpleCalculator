package com.example.kotlin_calculator.references

import android.util.Log
import java.math.BigInteger
import java.util.Stack
import kotlin.math.pow
import kotlin.math.sqrt



object CalculatorCore {
    private val dublicateConstants = setOf('+', '-', '*', '/','.')

    private const val LOG_TAG = "Expression"

    // Функция для выполнения основных математических операций на основе оператора
    private fun algorithmParsingExpression(stackOperand: Stack<Double>, stackOperator: Stack<Char>) {
        val a: Double
        val b: Double
        val c: Double

        if (stackOperator.isNotEmpty()) // Проверка, что стек операторов не пуст
        {
            // Выполнение операций на основе верхнего(последнего) оператора в стеке
            /*  Принцип работаты
            * 1. Из стека операций проверяется операция
            * 2. После берутся два операнда(или один, в зависимости от операции), причём стоит учесть,
            * что в логике используется стек, поэтому первый полученый из стека (stack.pop()) операнд - является вторым, а второй - является первым,
            * поэтому там где важен порядок операндов (вычетание, умножение), вид будет как b - a
            * 3. Результат пушится обратно в стек с операндами, как результат, а операция удаляется из стека с операциями.
            * */
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
                    // Берутся два операна - b/a, так как делим первый операнд на второй
                    a = stackOperand.pop()
                    b = stackOperand.pop()
                    c = b / a
                    stackOperand.push(c)
                }
                '^' ->{
                    // Берутся два операна - b^a, так как в значение 'a' будет записана степень
                    a = stackOperand.pop()
                    b = stackOperand.pop()
                    c = b.pow(a)
                    stackOperand.push(c)
                }
                '√' -> {
                    // Стандартный  сценарий, когда в записи нет операнда до вычисления корня (√25)
                    a = stackOperand.pop()
                    c = sqrt(a)
                    b = stackOperand.pop()
                    if (b != 0.0)
                        stackOperand.push(b * c)
                    else
                        stackOperand.push(c)
                }
                '%' ->{
                    a = stackOperand.pop()
                    c = a / 100.0
                    stackOperand.push(c)
                }
                '!' -> {
                    a = stackOperand.pop()
                    c = factorial(a.toLong()).toDouble()
                    stackOperand.push(c)
                }
            }
            stackOperator.pop() // Убираем обработанный оператор из стека

        }
    }

    private fun factorial(n: Long): BigInteger {
        return if (n == 0L || n == 1L) { BigInteger.ONE } else { BigInteger.valueOf(n) * (factorial(n - 1L)) }
    }

    private fun debug(value: Any){
        Log.d(LOG_TAG,"Log var ${value}")
    }

    // Суть метода в том что он всё выражение закрывает в скобках
    fun closeExpressionString(input: String) : StringBuilder{
        val stringBuilder = StringBuilder(dynamicAnalyzeExpression(input))
        Log.d(LOG_TAG,"yes")
        stringBuilder.insert(0,"(")
        stringBuilder.insert(stringBuilder.lastIndex + 1,")")
        return stringBuilder
    }

    // Функция для определения приоритета оператора
    private fun getPrecedence(operator: Char): Int {
        return when (operator) {
            '+', '-'-> 1
            '*', '/', '√' -> 2
            '!','%', '^' -> 3 // Наивысший приоритет
            else -> 0  // Для '('
        }
    }

    // Функция для вычисления значения математического выражения
    fun calculateExpression(expression: String) : Double{
            val stackOperands = Stack<Double>() // Локальный стек с операндами(числами,аргументом операции) 0..9
            val stackOperators = Stack<Char>() // Локальный стек с операторами +-*/

            val currentNumber = StringBuilder() // Строка с числом, которая помещается в стек с переводом в double(нужно для работы с числами)

            // Итерация по каждому символу в выражении (тут for во избежании бесконечного цикла)
            for (x in expression) {
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
                        while (stackOperators.isNotEmpty() && getPrecedence(x) <= getPrecedence(stackOperators.peek())) { // Если приоритет пред. оператора меньше или равна приоритету следующего
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

    // Данная функция предоставляет набор проверок и правок для строки с выражением (По-сути некий набор правил, к которым функция подготавливает)
    fun dynamicAnalyzeExpression(input: String): String {

        val stringBuilder = StringBuilder(input)

        // Находит скобку, и если отсувствует знак * перед скобкой, он его добавляет
        for (i in 1 until stringBuilder.length) {
            if (stringBuilder[i] == '(' && stringBuilder[i - 1] != '*') {
                stringBuilder.insert(i, '*')
            }
        }
        // Устанавливает знак * если он не был введён после операции. Или приводит записи: 2%2, 2+5!25 -> 2% * 2, 2 + 5! * 25
        for (i in 1 until stringBuilder.length - 1) {
            if (stringBuilder[i] in setOf('!','%') && stringBuilder[i + 1] !in setOf('+','-','*','/')) {
                stringBuilder.insert(i + 1, '*')
            }

        }
        // Удаляет из строки дубликаты
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