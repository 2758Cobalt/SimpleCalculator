package com.cobaltumapps.simplecalculator.v15.calculator.system

import com.cobaltumapps.simplecalculator.v15.calculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.managers.CalculatorManager

open class Calculator {
    // Входные данные
    protected var userExpression: Expression = Expression()

    protected var trimRange: Int = 9
    protected var angleMode: AngleMode = AngleMode.RAD

    // Экземпляры
    var calculatorManagerInstance: CalculatorManager? = null

    // Обищие данные
    var result: Double = 0.0

    // Символы
    protected val symbolAdd = ConstantsCalculator.symbolAdd
    protected val symbolSub = ConstantsCalculator.symbolSub
    protected val symbolMul = ConstantsCalculator.symbolMul
    protected val symbolDiv = ConstantsCalculator.symbolDiv

    protected val symbolPower = ConstantsCalculator.symbolPower
    protected val symbolSqrt = ConstantsCalculator.symbolSqrt
    protected val symbolPercent = ConstantsCalculator.symbolPercent
    protected val symbolFactorial = ConstantsCalculator.symbolFactorial
    protected val symbolOpenBracket = ConstantsCalculator.symbolOpenBracket
    protected val symbolCloseBracket = ConstantsCalculator.symbolCloseBracket
    protected val symbolPoint = ConstantsCalculator.symbolPoint
    protected val symbolExp = ConstantsCalculator.symbolExp
    protected val symbolPi = ConstantsCalculator.symbolPI

    protected val sinSymbol = ConstantsCalculator.sinSymbol
    protected val cosSymbol = ConstantsCalculator.cosSymbol
    protected val tanSymbol = ConstantsCalculator.tanSymbol
    protected val cotSymbol = ConstantsCalculator.cotSymbol

    protected val logSymbol = ConstantsCalculator.logSymbol
    protected val lnSymbol = ConstantsCalculator.lnSymbol


    open fun setNewAngleMode(newAngleMode: AngleMode = AngleMode.RAD) {
        this.angleMode = newAngleMode
    }
    // Устанавливает новое выражение
    open fun setNewExpression(expression: Expression) {
        this.userExpression = expression
    }

    // Расчитывает выражение
    open fun calculate(canTrimExpression: Boolean = false){
        result = 0.05
    }
}

