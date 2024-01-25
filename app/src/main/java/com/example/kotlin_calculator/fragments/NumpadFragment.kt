package com.example.kotlin_calculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.kotlin_calculator.R
import com.example.kotlin_calculator.references.ConstantsCalculator


class NumpadFragment: Fragment() {
    private var calculatorFragment: CalculatorFragment? = null
    private lateinit var rootContainer : androidx.gridlayout.widget.GridLayout

    private var numberButtons: Array<Button> = arrayOf()                            // Массив с ссылками кнопок цифр
    private var operatorButtons: Array<Button> = arrayOf()                          // Массив с ссылками кнопок операторов
    private var functionalButtons: Array<Button> = arrayOf()                            // Массив с допольнительными кнопками для панели быстрого доступа

    private lateinit var clearGroupButton: Button
    private lateinit var roundButton: Button
    private lateinit var equalButton: Button
    private lateinit var pointButton: Button
    private lateinit var backspaceButton: Button
    private lateinit var openBracketButton: Button
    private lateinit var closeBracketButton: Button
    private lateinit var percentButton: Button

    // Expand mode
    private lateinit var powerButton: Button
    private lateinit var sqrtButton: Button
    private lateinit var factorialButton: Button
    private lateinit var invertButton: Button

    fun setParentFragment(parent: CalculatorFragment){
        calculatorFragment = parent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = if (!calculatorFragment!!.leftHandMode){
//            // Если выключён режим левши
//            inflater.inflate(R.layout.fragment_numpad, container, false) // View фрагмента
//        }
//        else{
//            // Если включён режим левши
//            inflater.inflate(R.layout.fragment_numpad_leftmode, container, false) // View фрагмента
//        }

        return inflater.inflate(R.layout.fragment_numpad, container, false) // View фрагмента

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootContainer = view.findViewById(R.id.rootContainer)

        clearGroupButton = view.findViewById(R.id.buttonClearGroupAction)
        roundButton = view.findViewById(R.id.buttonRoundAction)
        equalButton = view.findViewById(R.id.buttonEqualAction)
        pointButton = view.findViewById(R.id.buttonPointAction)
        backspaceButton = view.findViewById(R.id.buttonBackSpaceAction)
        openBracketButton = view.findViewById(R.id.buttonRoundBracketOpen)
        closeBracketButton = view.findViewById(R.id.buttonRoundBracketClose)
        percentButton = view.findViewById(R.id.buttonPercentAction)

        powerButton = view.findViewById(R.id.buttonPowerAction)
        sqrtButton = view.findViewById(R.id.buttonSqrtAction)
        factorialButton = view.findViewById(R.id.buttonFactorialAction)
        invertButton = view.findViewById(R.id.buttonInvert)

        // Создание и запись ссылок в массив
        numberButtons = ConstantsCalculator.numberButtonIds.map { view.findViewById<Button>(it) }.toTypedArray()
        operatorButtons =  ConstantsCalculator.operatorButtonIds.map { view.findViewById<Button>(it) }.toTypedArray()


        functionalButtons = arrayOf(clearGroupButton,roundButton,equalButton,pointButton,backspaceButton,
            openBracketButton,closeBracketButton,percentButton)


        // Присвоение слушателя нажатий (OnClickListener) кнопкам с операндами
        for ((index,numberButton) in numberButtons.withIndex()) {
            numberButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operandConstants[index]) } // Слушатель нажатий для всего нампада(numpad)
        }

        // Присвоение слушателя нажатий (OnClickListener) кнопкам с операцией
        for ((index,operatorButton) in operatorButtons.withIndex()) {
            operatorButton.setOnClickListener { operationEnterAction(ConstantsCalculator.operatorConstants[index]) } // Слушатель нажатий кнопок операторов
        }

        // Слушатель нажатий кнопок функций
        clearGroupButton.setOnClickListener { calculatorFragment?.clearGroupAction(); }
        roundButton.setOnClickListener { calculatorFragment?.roundAction(3) }
        equalButton.setOnClickListener { calculatorFragment?.equalAction(); calculatorFragment?.saveExpressionToHistory(); calculatorFragment?.pushExpressionToLast()}
        pointButton.setOnClickListener { calculatorFragment?.enterPointAction() }

        backspaceButton.setOnClickListener { calculatorFragment?.backSpaceAction() }
        backspaceButton.setOnLongClickListener { calculatorFragment?.clearAllAction(); true } // Обработчик длинного нажатия (удерживая нажатие 1 сек)

        openBracketButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operatorConstants[6]) }     // Открывает скобку
        closeBracketButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operatorConstants[7]) }     // Закрывает скобку

        percentButton.setOnClickListener { calculatorFragment?.percentAction() }

        powerButton.setOnClickListener { calculatorFragment?.powerAction() }
        sqrtButton.setOnClickListener { calculatorFragment?.sqrtAction() }
        factorialButton.setOnClickListener { calculatorFragment?.factorialAction() }
        invertButton.setOnClickListener { calculatorFragment?.invertAction() }
    }

    override fun onResume() {
        super.onResume()

        if (calculatorFragment!!.expandMode){
            for (btn in arrayOf(powerButton,sqrtButton,factorialButton,invertButton)){
                btn.visibility = View.VISIBLE
            }
        }
        else{
            for (btn in arrayOf(powerButton,sqrtButton,factorialButton,invertButton)){
                btn.visibility = View.GONE
            }
        }
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