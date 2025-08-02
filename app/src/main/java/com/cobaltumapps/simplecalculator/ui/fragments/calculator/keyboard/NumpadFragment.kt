package com.cobaltumapps.simplecalculator.ui.fragments.calculator.keyboard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardArifmeticOperation
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.databinding.FragmentNumpadBinding
import com.cobaltumapps.simplecalculator.databinding.LayoutNumpadBinding
import com.cobaltumapps.simplecalculator.ui.fragments.calculator.keyboard.interfaces.NumpadBottomBehaviorListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.NumpadKeyboard
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.google.android.material.bottomsheet.BottomSheetBehavior

class NumpadFragment(
    private var numpadController: NumpadController? = null,
    private var numpadBottomBehaviorListener: NumpadBottomBehaviorListener? = null
): NumpadKeyboard() {
    // Binding
    private val binding by lazy { FragmentNumpadBinding.inflate(layoutInflater) }
    private val bindingNumpadContent by lazy { LayoutNumpadBinding.bind(binding.root) }

    private var isInputOpenBracket = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (numpadController == null) {
            numpadController = NumpadController()
            setNewKeyboardController(numpadController!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View = binding.root

    // Bottom sheet
    private val numpadBottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.numpadLayout) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numpadBottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    numpadBottomBehaviorListener?.onStateNumpadChanged(bottomSheet, newState)
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) { }
            })
        }

        binding.apply {

            bindingNumpadContent.apply {
                numpadNum0.setOnClickListener { onClickNumber(0) }
                numpadNum1.setOnClickListener { onClickNumber(1) }
                numpadNum2.setOnClickListener { onClickNumber(2) }
                numpadNum3.setOnClickListener { onClickNumber(3) }
                numpadNum4.setOnClickListener { onClickNumber(4) }
                numpadNum5.setOnClickListener { onClickNumber(5) }
                numpadNum6.setOnClickListener { onClickNumber(6) }
                numpadNum7.setOnClickListener { onClickNumber(7) }
                numpadNum8.setOnClickListener { onClickNumber(8) }
                numpadNum9.setOnClickListener { onClickNumber(9) }

                numpadAdd.setOnClickListener { onClickMathOperation(KeyboardArifmeticOperation.Add) }
                numpadSub.setOnClickListener { onClickMathOperation(KeyboardArifmeticOperation.Subtract) }
                numpadMul.setOnClickListener { onClickMathOperation(KeyboardArifmeticOperation.Multiply) }
                numpadDivide.setOnClickListener { onClickMathOperation(KeyboardArifmeticOperation.Divide) }

                numpadPoint.setOnClickListener { onClickMathOperation(KeyboardArifmeticOperation.Point) }

                numpadEqual.setOnClickListener {
                    onClickSpecialFunction(KeyboardSpecialFunction.Equal)
                    isInputOpenBracket = false
                }

                numpadOpenBracket.setOnClickListener { onClickMathOperation(
                    KeyboardArifmeticOperation.OpenBracket) }
                numpadCloseBracket.setOnClickListener { onClickMathOperation(
                    KeyboardArifmeticOperation.CloseBracket) }
                numpadAllClear.setOnClickListener { onClickSpecialFunction(KeyboardSpecialFunction.AllClear) }

                val delay = 500
                val handler = Handler()
                val deleteAction = object : Runnable {
                    override fun run() {
                        onClickSpecialFunction(KeyboardSpecialFunction.GroupDigitsCleaning)
                        handler.postDelayed(this, 100L)
                    }
                }

                numpadBackspace.setOnTouchListener { _, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            // При нажатии
                            onClickSpecialFunction(KeyboardSpecialFunction.Backspace)
                            numpadBottomSheetBehavior.isDraggable = false
                            handler.postDelayed(deleteAction, delay.toLong())
                        }

                        // При передвижении пальца
                        MotionEvent.ACTION_MOVE -> {
                            // Получаем координаты кнопки
                            val buttonLocation = IntArray(2)
                            view.getLocationOnScreen(buttonLocation)
                            val buttonX = buttonLocation[0]
                            val buttonY = buttonLocation[1]

                            // Получаем размеры кнопки
                            val buttonWidth = view.width
                            val buttonHeight = view.height

                            // Получаем координаты нажатия
                            val touchX = event.rawX.toInt()
                            val touchY = event.rawY.toInt()

                            // Проверяем, находится ли касание в пределах кнопки
                            if (touchX < buttonX || touchX > buttonX + buttonWidth ||
                                touchY < buttonY || touchY > buttonY + buttonHeight
                            ) {
                                // Если палец вышел за пределы кнопки, удаляем задачу
                                handler.removeCallbacks(deleteAction)
                            }
                        }

                        MotionEvent.ACTION_UP -> {
                            // При отпускании
                            handler.removeCallbacks(deleteAction)
                            numpadBottomSheetBehavior.isDraggable = true
                        }
                    }
                    false
                }
            }
        }
    }

    companion object {
        const val TAG = "SC_NumpadFragmentTag"
    }

}