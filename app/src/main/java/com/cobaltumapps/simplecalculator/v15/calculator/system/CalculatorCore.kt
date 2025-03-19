package com.cobaltumapps.simplecalculator.v15.calculator.system

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import java.math.BigInteger
import java.util.Locale
import java.util.Stack
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class CalculatorCore: Calculator() {
    private fun trimExpression(range: Int, result: Double): Double{
        val trimResult = String.format(Locale.US,"%.${range}f",result)
        return trimResult.toDouble()
    }

    // Расчитывает выражение и записывает в ответ
    override fun calculate(canTrimExpression: Boolean) {
        try {
            // Расчёт выражения если выражение(строка) не пустое
            if (userExpression.getExpression().isNotEmpty()) {
                val resultParseTrigonometry = parseTrigonometryFunctions(userExpression.getExpression()) // Расчитывает тригонометрические функции
                val resultCalculation = calculateExpression(resultParseTrigonometry) // Производит операцию над числами, и расчитывает выражение
                val resultTrim = if(canTrimExpression) trimExpression(trimRange, resultCalculation) else resultCalculation // Сокращает числа после точки
                this.result = resultTrim
            }
            else {
                Log.w(LOG_TAG, "Expression is empty")
            }
        } catch(ex: Throwable) {
            Log.e(LOG_TAG, "Calculator error: ${ex.cause}")
        }
    }

    // Парсер выражения (парсит строку, раскидывает элементы по стекам, вычисляет выражение и находит ответ)
    private fun parseExpression(stackOperand: Stack<Double>, stackOperator: Stack<Char>) {
        val a: Double
        val b: Double
        val c: Double

        if (stackOperator.isNotEmpty() && stackOperand.isNotEmpty()) // Проверка, что стек операторов не пуст
        {
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
                    else {
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
                    else {
                        a = stackOperand.pop()
                        c = -1 * a
                        stackOperand.push(c)
                    }
                }
                // mul (×)
                symbolMul -> {
                    // Если операндов больше одного
                    if (stackOperand.size > 1) {
                        a = stackOperand.pop()
                        b = stackOperand.pop()
                        c = a * b
                        stackOperand.push(c)
                    }
                    else {
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
                    else {
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
                    else {
                        a = stackOperand.pop()
                        c = sqrt(a)
                        stackOperand.push(1 * c) // 1√x
                    }
                }
                // percent (%)
                symbolPercent -> {
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
            stackOperator.pop()
        }
        else {
            stackOperand.push(0.0)
        }
    }

    private fun calculateExpression(expression: String): Double {
        val stackOperands = Stack<Double>() // Локальный стек с операндами(числами,аргументом операции)
        val stackOperators = Stack<Char>() // Локальный стек с операторами

        val currentNumber = StringBuilder() // Строка с числом, которая помещается в стек с переводом в double(нужно для работы с числами)
        try {

            // Итерация по каждому символу в выражении (тут for во избежании бесконечного цикла)
            for (i in expression.indices) {
                val x = expression[i]

                when {
                    // Обработка пробела
                    x == ' ' -> continue

                    // В случае, если текущий символ цифра или точка
                    x.isDigit() || x == symbolPoint -> {
                        // Добавляем цифры и точки, чтобы сформировать число
                        currentNumber.append(x)
                    }

                    // В случае, если текущий символ - открывающиеся скобка '('
                    x == symbolOpenBracket -> {
                        // Помещаем открывающую скобку в стек операторов
                        if (i > 0 && (expression[i - 1].isDigit() || expression[i - 1] == symbolPoint)) {

                            // Добавляет оператор умножение перед скобкой при его отсутствии
                            stackOperators.push(symbolMul)
                            stackOperands.push(currentNumber.toString().toDouble())
                            currentNumber.clear()

                            // Если перед скобкой стоит точка, добавляем 0 после неё
                            if (expression[i - 1] == symbolPoint) {
                                currentNumber.append('0')
                            }
                        }

                        stackOperators.push(x)

                        // Проверка на унарный минус после открывающей скобки
                        if (i + 1 < expression.length && expression[i + 1] == symbolSub) {
                            stackOperands.push(0.0) // Добавляем ноль после открывающей скобки перед унарным минусом
                        }
                    }

                    x == symbolCloseBracket -> {
                        if (i + 1 < expression.length && expression[i + 1].isDigit())  {

                            // Добавляет оператор умножение перед скобкой при его отсутствии
                            stackOperators.push(symbolMul)
                            stackOperands.push(currentNumber.toString().toDouble())
                            currentNumber.clear()
                        }

                        if (currentNumber.isNotEmpty()) {
                            stackOperands.push(currentNumber.toString().toDouble())
                            currentNumber.clear()
                        }
                        // Перебирает операторы до открывающей скобки
                        while (stackOperators.isNotEmpty() && stackOperators.peek() != symbolOpenBracket) {
                            parseExpression(stackOperands, stackOperators)
                        }
                        if (stackOperators.isNotEmpty() && stackOperators.peek() == symbolCloseBracket) {
                            stackOperators.pop() // Удаляет '(' из стека
                        }

                    }

                    x == symbolPi -> {
                        stackOperands.push(Math.PI)
                    }

                    x == symbolExp -> {
                        stackOperands.push(Math.E)
                    }

                    else -> {
                        // Если элемент является оператором или неизвестным символом
                        if (currentNumber.isNotEmpty()) {
                            stackOperands.push(currentNumber.toString().toDouble())
                            currentNumber.clear()
                        }
                        // Перебирает операторы в зависимости от приоритета
                        while (stackOperators.isNotEmpty() && getPrecedence(x) <= getPrecedence(stackOperators.peek())) {
                            parseExpression(stackOperands, stackOperators)
                        }
                        stackOperators.push(x) // Добавление оператора в стек
                    }

                }
            }

            if (currentNumber.isNotEmpty()) {
                if (currentNumber.toString().toDoubleOrNull() != null)
                    stackOperands.push(currentNumber.toString().toDouble())
                else
                    throw NumberFormatException("Невозможно перевести число в Double и добавить в стек с операндами")
            }

            // Перебирает оставшиеся операторы
            while (stackOperators.isNotEmpty()) {
                parseExpression(stackOperands, stackOperators)
            }
            return try {
                stackOperands.peek()
            }catch (ex: java.util.EmptyStackException) {
                Log.e("DebugTag", "EmptyStackException: ${javaClass.simpleName} ERROR calculation")
                0.0
            }


        } catch (ex: java.lang.NumberFormatException) {
            Log.e("DebugTag","Fatal error")
            return 0.0
        }

    }

    // Функция для определения приоритета оператора
    private fun getPrecedence(operator: Char): Int {
        return when (operator) {
            symbolAdd, symbolSub -> 1
            symbolMul, symbolFactorial, symbolDiv, symbolSqrt -> 2
            symbolPower -> 3
            symbolPercent -> 4 // Наивысший приоритет
            else -> 0  // Для '('
        }
    }

    // Вычисляет факториал
    private fun factorial(n: Long): BigInteger {
        var result = BigInteger.ONE
        for (i in 2..n) {
            result *= BigInteger.valueOf(i)
        }
        return result
    }

    // Берёт всё выражение в скобки
    fun closeExpressionString(input: String) : String{
        val stringBuilder = StringBuilder(input)
        stringBuilder.insert(0,"1${'/'}${'('}")
        stringBuilder.insert(stringBuilder.lastIndex + 1,"${')'}")
        return stringBuilder.toString()
    }

    private fun parseTrigonometryFunctions(expression: String): String {
        val regex = Regex("($sinSymbol|$cosSymbol|$tanSymbol|$cotSymbol)\\(([^)]+)\\)")
        val logRegex = Regex("($logSymbol|$lnSymbol)\\(([^)]+)\\)")
        var output = expression

        regex.findAll(expression).forEach { match ->
            try {
                val functionName = match.groupValues[1]
                val argument = match.groupValues[2].toDouble()
                val value =
                    when(angleMode) {
                        AngleMode.RAD -> {
                            when (functionName) {
                                sinSymbol -> sin(argument)
                                cosSymbol -> cos(argument)
                                tanSymbol -> tan(argument)
                                cotSymbol -> 1.0 / tan(result)
                                else -> Log.e(LOG_TAG, "Неподдерживаемая функция: $functionName при переводе в радианы")
                            }
                        }

                        AngleMode.DEG -> {
                            when (functionName) {
                                sinSymbol -> sin(Math.toRadians(argument))
                                cosSymbol -> cos(Math.toRadians(argument))
                                tanSymbol -> tan(Math.toRadians(argument))
                                cotSymbol -> 1.0 / tan(Math.toRadians(result))
                                else -> Log.e(LOG_TAG, "Неподдерживаемая функция: $functionName при переводе в градусы")
                            }
                        }
                    }

                output = output.replace(match.value, "($value)")
            } catch (ex: NumberFormatException) {
                output = "Error Trigonometry"
            }
        }

        // Обработка логарифмов
        logRegex.findAll(expression).forEach { match ->
            val functionName = match.groupValues[1]
            val argument = match.groupValues[2].toDouble()
            val value = when (functionName) {
                logSymbol -> log10(argument)
                lnSymbol -> ln(argument)
                else -> throw IllegalArgumentException("Неподдерживаемая функция: $functionName")
            }
            output = output.replace(match.value, "($value)")
        }

        return output
    }

}