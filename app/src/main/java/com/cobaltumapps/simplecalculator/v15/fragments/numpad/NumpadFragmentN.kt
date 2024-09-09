package com.cobaltumapps.simplecalculator.v15.fragments.numpad

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.cobaltumapps.simplecalculator.databinding.FragmentNumpadNBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.NumpadKeyboard
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation

// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class NumpadFragmentN: NumpadKeyboard() {
    private val binding by lazy { FragmentNumpadNBinding.inflate(layoutInflater) }
    private var isInputOpenBracket = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numpadNum0.setOnClickListener { onClickNumber(0) }
        binding.numpadNum1.setOnClickListener { onClickNumber(1) }
        binding.numpadNum2.setOnClickListener { onClickNumber(2) }
        binding.numpadNum3.setOnClickListener { onClickNumber(3) }
        binding.numpadNum4.setOnClickListener { onClickNumber(4) }
        binding.numpadNum5.setOnClickListener { onClickNumber(5) }
        binding.numpadNum6.setOnClickListener { onClickNumber(6) }
        binding.numpadNum7.setOnClickListener { onClickNumber(7) }
        binding.numpadNum8.setOnClickListener { onClickNumber(8) }
        binding.numpadNum9.setOnClickListener { onClickNumber(9) }

        binding.numpadAdd.setOnClickListener { onClickMathOperation(MathOperation.Add) }
        binding.numpadSub.setOnClickListener { onClickMathOperation(MathOperation.Subtract) }
        binding.numpadMul.setOnClickListener { onClickMathOperation(MathOperation.Multiply) }
        binding.numpadDivide.setOnClickListener { onClickMathOperation(MathOperation.Divide) }

        binding.numpadPoint.setOnClickListener { onClickMathOperation(MathOperation.Point) }

        binding.numpadEqual.setOnClickListener {
            onClickSpecialFunction(KeyboardSpecialFunction.Equal)
            isInputOpenBracket = false
        }

        binding.numpadOpenBracket.setOnClickListener { onClickMathOperation(MathOperation.OpenBracket) }
        binding.numpadCloseBracket.setOnClickListener { onClickMathOperation(MathOperation.CloseBracket) }

        binding.numpadAllClear.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.AllClear) }

        val delay = 500
        val handler = Handler()
        val deleteAction = object : Runnable {
            override fun run() {
                onClickSpecialFunction(KeyboardSpecialFunction.GroupDigitsCleaning)
                handler.postDelayed(this, 100L)
            }
        }

        binding.numpadBackspace.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // При нажатии
                    onClickSpecialFunction(KeyboardSpecialFunction.Backspace)
                    handler.postDelayed(deleteAction, delay.toLong())
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
                    }
                }
                MotionEvent.ACTION_UP -> {
                    // При отпускании
                    handler.removeCallbacks(deleteAction)
                }
            }
            false
        }
    }

}