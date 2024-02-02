package com.cobaltumapps.simplecalculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator


class NumpadFragment: Fragment() {
    private var calculatorFragment: CalculatorFragment? = null
    private lateinit var rootContainer : androidx.gridlayout.widget.GridLayout

    private var numberButtons: Array<Button> = arrayOf()                            // Массив с ссылками кнопок цифр
    private var operatorButtons: Array<Button> = arrayOf()                          // Массив с ссылками кнопок операторов
    private var functionalButtons: Array<Button> = arrayOf()                            // Массив с допольнительными кнопками для панели быстрого доступа

    private lateinit var clearGroupButton: Button
    private lateinit var powerButton: Button
    private lateinit var equalButton: Button
    private lateinit var pointButton: Button
    private lateinit var backspaceButton: Button
    private lateinit var openBracketButton: Button
    private lateinit var closeBracketButton: Button
    private lateinit var percentButton: Button

    fun setParentFragment(parent: CalculatorFragment){
        calculatorFragment = parent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (calculatorFragment?.leftHandMode == false){
            inflater.inflate(R.layout.fragment_numpad, container, false) // View фрагмента
        } else{
            inflater.inflate(R.layout.fragment_numpad_leftmode, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootContainer = view.findViewById(R.id.rootContainer)

        clearGroupButton = view.findViewById(R.id.buttonClearGroupAction)
        powerButton = view.findViewById(R.id.buttonPowerAction)
        equalButton = view.findViewById(R.id.buttonEqualAction)
        pointButton = view.findViewById(R.id.buttonPointAction)
        backspaceButton = view.findViewById(R.id.buttonBackSpaceAction)
        openBracketButton = view.findViewById(R.id.buttonRoundBracketOpen)
        closeBracketButton = view.findViewById(R.id.buttonRoundBracketClose)
        percentButton = view.findViewById(R.id.buttonPercentAction)

        // Создание и запись ссылок в массив
        numberButtons = ConstantsCalculator.numberButtonIds.map { view.findViewById<Button>(it) }.toTypedArray()
        operatorButtons =  ConstantsCalculator.operatorButtonIds.map { view.findViewById<Button>(it) }.toTypedArray()

        functionalButtons = arrayOf(clearGroupButton,powerButton,equalButton,pointButton,backspaceButton,
            openBracketButton,closeBracketButton,percentButton)

        // Присвоение слушателя нажатий (OnClickListener) кнопкам с операндами
        for ((index,numberButton) in numberButtons.withIndex()) {
            numberButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operandConstants[index]); calculatorFragment?.playVibration(5) } // Слушатель нажатий для всего нампада(numpad)

        }

        // Присвоение слушателя нажатий (OnClickListener) кнопкам с операцией
        for ((index,operatorButton) in operatorButtons.withIndex()) {
            operatorButton.setOnClickListener { operationEnterAction(ConstantsCalculator.operatorConstants[index]); calculatorFragment?.playVibration(5) } // Слушатель нажатий кнопок операторов
        }


        // Слушатель нажатий кнопок функций
        clearGroupButton.setOnClickListener { calculatorFragment?.clearGroupAction(); calculatorFragment?.playVibration(5) }
        powerButton.setOnClickListener { calculatorFragment?.powerAction();                 calculatorFragment?.playVibration(5) }
        equalButton.setOnClickListener { calculatorFragment?.equalAction(); calculatorFragment?.pushExpressionToLast(); calculatorFragment?.playVibration(20)}
        pointButton.setOnClickListener { calculatorFragment?.enterPointAction(); calculatorFragment?.playVibration(5)}

        backspaceButton.setOnClickListener { calculatorFragment?.backSpaceAction(); calculatorFragment?.playVibration(5) }
        backspaceButton.setOnLongClickListener { calculatorFragment?.clearAllAction(); calculatorFragment?.playVibration(20); true } // Обработчик длинного нажатия (удерживая нажатие 1 сек)

        openBracketButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operatorConstants[6]); calculatorFragment?.playVibration(5) }     // Открывает скобку
        closeBracketButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operatorConstants[7]); calculatorFragment?.playVibration(5) }     // Закрывает скобку

        percentButton.setOnClickListener { calculatorFragment?.percentAction(); calculatorFragment?.playVibration(5) }
    }


    private fun numberEnterAction(numberSymbol: String) // Обработчик нажатия - цифра (0-9) и скобки
    {
        calculatorFragment?.numberEnterAction(numberSymbol)
    }

    private fun operationEnterAction(operator: String) // Обработчик нажатия - оператора (+ - * /)
    {
        calculatorFragment?.operationEnterAction(operator)
    }

}