package com.cobaltumapps.simplecalculator.references

import java.math.BigInteger
import java.util.Locale
import java.util.Stack
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan


object CalculatorCore {
    var roundRange: Int = 3
    var toDeg = false

    private const val symbolAdd = ConstantsCalculator.symbolAdd
    private const val symbolSub = ConstantsCalculator.symbolSub
    private const val symbolMul = ConstantsCalculator.symbolMul
    private const val symbolDiv = ConstantsCalculator.symbolDiv

    private const val symbolPower = ConstantsCalculator.symbolPower
    private const val symbolSqrt = ConstantsCalculator.symbolSqrt
    private const val symbolPercent = ConstantsCalculator.symbolPercent
    private const val symbolFactorial = ConstantsCalculator.symbolFactorial

    private const val symbolOpenBracket = ConstantsCalculator.symbolOpenBracket
    private const val symbolCloseBracket = ConstantsCalculator.symbolCloseBracket
    private const val symbolPoint = ConstantsCalculator.symbolPoint

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
                // add (+)
                symbolAdd -> {
                    // Если операндов больше одного
                    if (stackOperand.size > 1) {
                        a = stackOperand.pop()
                        b = stackOperand.pop()
                        c = a + b
                        stackOperand.push(c)
                    }
                    else{
                        // Если нет первого операнда
                        a = stackOperand.pop()
                        c = a
                        stackOperand.push(c)
                    }
                }
                // sub (-)
                symbolSub -> {
                    // Если операндов больше одного
                    if (stackOperand.size > 1) {
                        a = stackOperand.pop()
                        b = stackOperand.pop()
                        c = b - a
                        stackOperand.push(c)
                    }
                    else{
                        a = stackOperand.pop()
                        c = -1 * a
                        stackOperand.push(c)
                    }
                }
                // mul (x)
                symbolMul -> {
                    // Если операндов больше одного
                    if (stackOperand.size > 1) {
                        a = stackOperand.pop()
                        b = stackOperand.pop()
                        c = a * b
                        stackOperand.push(c)
                    }
                    else{
                        a = stackOperand.pop()
                        c = 1 * a
                        stackOperand.push(c)
                    }
                }
                // div (/)
                symbolDiv -> {
                    // Берутся два операна - b/a, так как делим первый операнд на второй
                    // Если операндов больше одного
                    if (stackOperand.size > 1) {
                        a = stackOperand.pop()
                        b = stackOperand.pop()
                        c = b / a
                        stackOperand.push(c)
                    }
                    else{
                        a = stackOperand.pop()
                        c = 1 / a
                        stackOperand.push(c)
                    }
                }
                // power (^)
               symbolPower -> {
                    // Берутся два операна - b^a, так как в значение 'a' будет записана степень
                    a = stackOperand.pop()
                    b = stackOperand.pop()
                    c = b.pow(a)
                    stackOperand.push(c)
                }
                // sqrt (√)
                symbolSqrt -> {
                    if (stackOperand.size > 1) {
                        a = stackOperand.pop()
                        c = sqrt(a)
                        b = stackOperand.pop()
                        if (b != 0.0)
                            stackOperand.push(b * c)
                        else
                            stackOperand.push(c)
                    }
                    else{
                        a = stackOperand.pop()
                        c = sqrt(a)
                        stackOperand.push(1 * c) // 1√x
                    }
                }
                // percent (%)
                symbolPercent ->{
                    a = stackOperand.pop()
                    c = a / 100.0
                    stackOperand.push(c)
                }
                // factorial (!)
                symbolFactorial -> {
                    a = stackOperand.pop()
                    c = factorial(a.toLong()).toDouble()
                    stackOperand.push(c)
                }
            }
            stackOperator.pop() // Удаление обработаного оператора из стека

        }
    }

    private fun factorial(n: Long): BigInteger {
        return if (n == 0L || n == 1L) { BigInteger.ONE } else { BigInteger.valueOf(n) * (factorial(n - 1L)) }
    }


    // Суть метода в том что он всё выражение закрывает в скобках
    fun closeExpressionString(input: String) : StringBuilder{
        val stringBuilder = StringBuilder(dynamicAnalyzeExpression(input, toDeg, roundRange))
        stringBuilder.insert(0,"1$symbolDiv$symbolOpenBracket")
        stringBuilder.insert(stringBuilder.lastIndex + 1,"$symbolCloseBracket")
        return stringBuilder
    }

    // Функция для определения приоритета оператора
    private fun getPrecedence(operator: Char): Int {
        return when (operator) {
            // + -
            // * / √
            // '^','%','!'
            symbolAdd, symbolSub -> 1
            symbolMul, symbolDiv, symbolSqrt -> 2
            symbolPower -> 3
            symbolPercent, symbolFactorial -> 4 // Наивысший приоритет
            else -> 0  // Для '('
        }
    }

    fun calculateExpression(expression: String): Double {
        val stackOperands = Stack<Double>() // Локальный стек с операндами(числами,аргументом операции) 0..9
        val stackOperators = Stack<Char>() // Локальный стек с операторами +-*/

        val currentNumber = StringBuilder() // Строка с числом, которая помещается в стек с переводом в double(нужно для работы с числами)

        // Итерация по каждому символу в выражении (тут for во избежании бесконечного цикла)
        for (i in expression.indices) {
            val x = expression[i]

            when {
                x == ' ' -> continue
                x.isDigit() || x == symbolPoint -> currentNumber.append(x) // Добавляем цифры и точки, чтобы сформировать число

                x == symbolOpenBracket -> { // Помещаем открывающую скобку в стек операторов
                    stackOperators.push(x)

                    // Проверка на унарный минус после открывающей скобки
                    if (i + 1 < expression.length && expression[i + 1] == symbolSub) {
                        stackOperands.push(0.0) // Добавляем ноль после открывающей скобки перед унарным минусом
                    }
                }

                x == symbolCloseBracket -> {
                    if (currentNumber.isNotEmpty()) {
                        stackOperands.push(currentNumber.toString().toDouble())
                        currentNumber.clear()
                    }
                    // Перебирает операторы до открывающей скобки
                    while (stackOperators.isNotEmpty() && stackOperators.peek() != symbolOpenBracket) {
                        algorithmParsingExpression(stackOperands, stackOperators)
                    }
                    if (stackOperators.isNotEmpty() && stackOperators.peek() == symbolOpenBracket) {
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


    // Данная функция предоставляет набор проверок и правок для строки с выражением (По-сути некий набор правил, к которым функция подготавливает исходную строку для парсера)
    fun dynamicAnalyzeExpression(input: String, toDeg: Boolean, roundRange: Int): String {
        val inputBuilder = StringBuilder(input)

        val trigFunctions = listOf("sin", "cos", "tan", "cot", "log", "ln")

        for (function in trigFunctions) {
            val regex = Regex("$function\\((\\d+(\\.\\d+)?)\\)")
            val matches = regex.findAll(inputBuilder)

            for (matchResult in matches) {
                val result = matchResult.groupValues[1].toDouble()
                val formattedResult = String.format(Locale.US, "%.${roundRange}f",
                    when (function) {
                        "sin" -> if (toDeg) sin(Math.toRadians(result)) else sin(result)
                        "cos" -> if (toDeg) cos(Math.toRadians(result)) else cos(result)
                        "tan" -> if (toDeg) tan(Math.toRadians(result)) else tan(result)
                        "cot" -> if (toDeg) 1.0 / tan(Math.toRadians(result)) else 1.0 / tan(result)
                        "log" -> kotlin.math.log10(result)
                        "ln"  -> kotlin.math.ln(result)
                        else -> throw IllegalArgumentException("Unsupported function: $function")
                    })

                if (inputBuilder[matchResult.range.first - 1] in setOf(symbolAdd, symbolSub,symbolMul, symbolDiv))
                    inputBuilder.replace(matchResult.range.first, matchResult.range.last + 1,  "$symbolOpenBracket$formattedResult$symbolCloseBracket")
                else
                    inputBuilder.replace(matchResult.range.first, matchResult.range.last + 1,  formattedResult)

            }
        }
        for (i in inputBuilder.indices) {
            if (i > 0
                && inputBuilder[i] == symbolOpenBracket
                && inputBuilder[i - 1] != symbolMul
                && inputBuilder[i - 1].isDigit())
            {
                inputBuilder.insert(i, symbolMul)
            }
        }

        for (i in 0 until inputBuilder.length - 1) {
            if (inputBuilder[i] in setOf(symbolFactorial, symbolPercent)
                && inputBuilder[i + 1] !in setOf(symbolAdd, symbolSub, symbolMul, symbolDiv)
                )
            {
                inputBuilder.insert(i + 1, symbolMul)
            }
        }
        return inputBuilder.toString()
    }

}