package com.cobaltumapps.simplecalculator.fragments.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
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

    @SuppressLint("ClickableViewAccessibility")
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
            numberButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operandConstants[index]) } // Слушатель нажатий для всего нампада(numpad)

        }

        // Присвоение слушателя нажатий (OnClickListener) кнопкам с операцией
        for ((index,operatorButton) in operatorButtons.withIndex()) {
            operatorButton.setOnClickListener { operationEnterAction(ConstantsCalculator.operatorConstants[index])} // Слушатель нажатий кнопок операторов
        }


        // Слушатель нажатий кнопок функций
        clearGroupButton.setOnClickListener { calculatorFragment?.clearAllAction() }
        powerButton.setOnClickListener { calculatorFragment?.powerAction() }
        equalButton.setOnClickListener { calculatorFragment?.equalAction(); calculatorFragment?.pushExpressionToLast() }
        pointButton.setOnClickListener { calculatorFragment?.enterPointAction(); }


        backspaceButton.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Получаем размеры кнопки
                val width = backspaceButton.width
                val height = backspaceButton.height

                // Удаляем слушатель, чтобы он больше не вызывался
                backspaceButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        //backspaceButton.setOnLongClickListener { calculatorFragment?.clearGroupAction(); calculatorFragment?.playVibration(5); true } // Обработчик длинного нажатия (удерживая нажатие 1 сек)

        val delay = 500
        val handler = Handler()
        val deleteAction = object : Runnable {
            override fun run() {
                calculatorFragment?.clearGroupAction()
                calculatorFragment?.playVibration(5)
                handler.postDelayed(this, 100L)
            }
        }

        backspaceButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // При нажатии
                    calculatorFragment?.backSpaceAction()
                    calculatorFragment?.playVibration(10)
                    handler.postDelayed(deleteAction, delay.toLong())
                    calculatorFragment?.numpadPager?.isUserInputEnabled = false
                }
                MotionEvent.ACTION_MOVE -> {
                    // Получаем координаты кнопки
                    val buttonLocation = IntArray(2)
                    view.getLocationOnScreen(buttonLocation)
                    val buttonX = buttonLocation[0]
                    val buttonY = buttonLocation[1]

                    // Получаем размеры кнопки
                    val buttonWidth = view.width
                    val buttonHeight = view.height

                    // Получаем координаты пальца
                    val fingerX = event.rawX.toInt()
                    val fingerY = event.rawY.toInt()

                    // Проверяем, находится ли палец в пределах кнопки
                    if (fingerX < buttonX || fingerX > buttonX + buttonWidth ||
                        fingerY < buttonY || fingerY > buttonY + buttonHeight
                    ) {
                        // Если палец вышел за пределы кнопки, удаляем задачу
                        handler.removeCallbacks(deleteAction)
                        calculatorFragment?.numpadPager?.isUserInputEnabled = true
                    }
                }
                MotionEvent.ACTION_UP -> {
                    // При отпускании
                    handler.removeCallbacks(deleteAction)
                    calculatorFragment?.numpadPager?.isUserInputEnabled = true
                }
            }
            false
        }


        openBracketButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operatorConstants[6]) }     // Открывает скобку
        closeBracketButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operatorConstants[7]) }     // Закрывает скобку

        percentButton.setOnClickListener { calculatorFragment?.percentAction() }
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