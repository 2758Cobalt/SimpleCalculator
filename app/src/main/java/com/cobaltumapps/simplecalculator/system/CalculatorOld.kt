package com.cobaltumapps.simplecalculator.system

import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
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

private const val sinSymbol = "sin"
private const val cosSymbol = "cos"
private const val tanSymbol = "tan"
private const val cotSymbol = "cot"

private const val logSymbol = "log"
private const val lnSymbol = "ln"

open class CalculatorOld(private val expression: String = "", private var trimRange: Int = 9, private var toRadians: Boolean = true) {

    private var result: Double = 0.0 // Результат подсчёта

    private val symbolAdd = ConstantsCalculator.symbolAdd
    private val symbolSub = ConstantsCalculator.symbolSub
    private val symbolMul = ConstantsCalculator.symbolMul
    private val symbolDiv = ConstantsCalculator.symbolDiv

    private val symbolPower = ConstantsCalculator.symbolPower
    private val symbolSqrt = ConstantsCalculator.symbolSqrt
    private val symbolPercent = ConstantsCalculator.symbolPercent
    private val symbolFactorial = ConstantsCalculator.symbolFactorial

    private val symbolOpenBracket = ConstantsCalculator.symbolOpenBracket
    private val symbolCloseBracket = ConstantsCalculator.symbolCloseBracket
    private val symbolPoint = ConstantsCalculator.symbolPoint

    private val sinSymbol = ConstantsCalculator.sinSymbol
    private val cosSymbol = ConstantsCalculator.cosSymbol
    private val tanSymbol = ConstantsCalculator.tanSymbol
    private val cotSymbol = ConstantsCalculator.cotSymbol

    private val logSymbol = ConstantsCalculator.logSymbol
    private val lnSymbol = ConstantsCalculator.lnSymbol

    // Возращает результат
    fun getResult(): Double {
        return this.result
    }

    // Возвращает количество цифр после точки
    fun getTrimRange(): Int {
        return this.trimRange
    }

    fun getRadiansState(): Boolean{
        return this.toRadians
    }
    fun setRadiansState(newState: Boolean){
        this.toRadians = newState
    }

    // Принудительно устанавливает результат
    fun setResult(newResult: Double){
        this.result = newResult
    }
    fun setResult(newResult: Int){
        this.result = newResult.toDouble()
    }
    fun setResult(newResult: Float){
        this.result = newResult.toDouble()
    }

    fun setTrimRange(newTrimRange: Int){
        this.trimRange = newTrimRange
    }
    fun setTrimRange(newTrimRange: Float){
        this.trimRange = newTrimRange.toInt()
    }
    fun setTrimRange(newTrimRange: Double){
        this.trimRange = newTrimRange.toInt()
    }

    // Расчитывает выражение и записывает в ответ
    fun calculate(canTrimExpression: Boolean = false) {
        if (expression.isNotEmpty())             // Расчёт выражения если выражение(строка) не пустое
        {
            val resultParseTrigonometry = parseTrigonometryFunctions(expression) // Расчитывает тригонометрические функции
            val resultCalculation = calculateExpression(resultParseTrigonometry) // Производит операцию над числами, и расчитывает выражение
            val resultTrim = if(canTrimExpression) trimExpression(trimRange,resultCalculation) else resultCalculation // Сокращает числа после точки
            this.result = resultTrim
        }
        else{
            // Вызов исключения, если происходит попытка расчитать выражение пустой строки
            throw NumberFormatException("Empty expression string - calculate is impossible\nПустая строка выражения - расчёт невозможен")
        }
    }

    private fun trimExpression(range: Int, result: Double): Double{
        val trimResult = String.format(Locale.US,"%.${range}f",result)
        println("Обрезаный ответ:$trimResult")
        return trimResult.toDouble()
    }

    // Парсер выражения (парсит строку, раскидывает элементы по стекам, вычисляет выражение и находит ответ)
    private fun parseExpression(stackOperand: Stack<Double>, stackOperator: Stack<Char>) {
        val a: Double
        val b: Double
        val c: Double

        if (stackOperator.isNotEmpty()) // Проверка, что стек операторов не пуст
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
                // mul (×)
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
            stackOperator.pop()
        }
    }

    private fun calculateExpression(expression: String): Double {
        val stackOperands =
            Stack<Double>() // Локальный стек с операндами(числами,аргументом операции)
        val stackOperators = Stack<Char>() // Локальный стек с операторами


        val currentNumber = StringBuilder() // Строка с числом, которая помещается в стек с переводом в double(нужно для работы с числами)

        // Итерация по каждому символу в выражении (тут for во избежании бесконечного цикла)
        for (i in expression.indices) {
            val x = expression[i]

            when {
                // Обработка пробела
                x == ' ' -> continue

                x.isDigit() || x == symbolPoint -> {
                    // Добавляем цифры и точки, чтобы сформировать число
                    currentNumber.append(x)
                }

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
                        println(expression[i + 1])

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
            else throw NumberFormatException("Невозможно перевести число в Double и добавить в стек с операндами")
        }

        // Перебирает оставшиеся операторы
        while (stackOperators.isNotEmpty()) {
            parseExpression(stackOperands, stackOperators)
        }

        return stackOperands.peek()
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
            val functionName = match.groupValues[1]
            val argument = match.groupValues[2].toDouble()
            val value =
                if (toRadians)
                    when (functionName) {
                        sinSymbol -> sin(argument)
                        cosSymbol -> cos(argument)
                        tanSymbol -> tan(argument)
                        "cot" -> 1.0 / tan(result)
                        else -> throw IllegalArgumentException("Неподдерживаемая функция: $functionName при переводе в радианы")
                    }
                else
                    when (functionName) {
                        sinSymbol -> sin(Math.toRadians(argument))
                        cosSymbol -> cos(Math.toRadians(argument))
                        tanSymbol -> tan(Math.toRadians(argument))
                        "cot" -> 1.0 / tan(Math.toRadians(result))
                        else -> throw IllegalArgumentException("Неподдерживаемая функция: $functionName при переводе в градусы")
                    }
            output = output.replace(match.value, "($value)")
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