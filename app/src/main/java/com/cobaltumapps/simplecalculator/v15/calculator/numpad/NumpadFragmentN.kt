package com.cobaltumapps.simplecalculator.v15.calculator.numpad

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentNumpadNBinding
import com.cobaltumapps.simplecalculator.databinding.PopupLayoutBracketsBinding
import com.cobaltumapps.simplecalculator.v15.calculator.enums.ActionOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation
import com.cobaltumapps.simplecalculator.v15.calculator.host.interfaces.NumpadListener
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.models.NumpadBooleanModel

// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class NumpadFragmentN: Fragment(), NumpadListener {
    private lateinit var binding: FragmentNumpadNBinding
    private var numpadListener: NumpadListener? = null

    var canEnterNumbers = NumpadBooleanModel.NumberEnter.isEnabled
    var canEnterOperations = NumpadBooleanModel.OperationEnter.isEnabled
    var canEnterPoint = NumpadBooleanModel.PointEnter.isEnabled

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNumpadNBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создание popup(всплывающего) окна для доп. функций
        val popupBinding = PopupLayoutBracketsBinding.inflate(layoutInflater)
        val popupView = popupBinding.root

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        // Настройка всплывающего окна
        popupWindow.elevation = 8f

        binding.numpadNum0.setOnClickListener { if (canEnterNumbers) onClickNumber(0) }
        binding.numpadNum1.setOnClickListener { if (canEnterNumbers) onClickNumber(1) }
        binding.numpadNum2.setOnClickListener { if (canEnterNumbers) onClickNumber(2) }
        binding.numpadNum3.setOnClickListener { if (canEnterNumbers) onClickNumber(3) }
        binding.numpadNum4.setOnClickListener { if (canEnterNumbers) onClickNumber(4) }
        binding.numpadNum5.setOnClickListener { if (canEnterNumbers) onClickNumber(5) }
        binding.numpadNum6.setOnClickListener { if (canEnterNumbers) onClickNumber(6) }
        binding.numpadNum7.setOnClickListener { if (canEnterNumbers) onClickNumber(7) }
        binding.numpadNum8.setOnClickListener { if (canEnterNumbers) onClickNumber(8) }
        binding.numpadNum9.setOnClickListener { if (canEnterNumbers) onClickNumber(9) }


        binding.numpadAdd.setOnClickListener { if (canEnterOperations) onClickOperation(MathOperation.Add) }
        binding.numpadSub.setOnClickListener { if (canEnterOperations) onClickOperation(MathOperation.Subtract) }
        binding.numpadMul.setOnClickListener { if (canEnterOperations) onClickOperation(MathOperation.Multiply) }
        binding.numpadDivide.setOnClickListener { if (canEnterOperations) onClickOperation(MathOperation.Divide) }

        binding.numpadPoint.setOnClickListener { if (canEnterPoint) onClickOperation(MathOperation.Point) }

        binding.numpadPercent.setOnClickListener { if (canEnterOperations) onClickOperation(MathOperation.Percent) }
        binding.numpadEqual.setOnClickListener { onActionEqual() }

        binding.numpadBrackets.setOnClickListener { popupWindow.isFocusable = true

            popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val popupHeight = popupView.measuredHeight + 32

            popupWindow.showAsDropDown(binding.numpadBrackets, -binding.numpadBrackets.width / 2, -binding.numpadBrackets.height - popupHeight) }


        popupBinding.popupOpenBracket.setOnClickListener { onClickOperation(MathOperation.OpenBracket) }
        popupBinding.popupCloseBracket.setOnClickListener { onClickOperation(MathOperation.CloseBracket) }

        binding.numpadAllClear.setOnClickListener { onActionAllClear() }

        val delay = 500
        val handler = Handler()
        val deleteAction = object : Runnable {
            override fun run() {
                onActionGroupClean()
                handler.postDelayed(this, 100L)
            }
        }

        binding.numpadBackspace.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // При нажатии
                    onActionBackspace()
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

    fun setNumpadListener(newListener: NumpadListener) {
        this.numpadListener = newListener
    }

    override fun onClickNumber(number: Number) {
        numpadListener?.onClickNumber(number)
    }

    override fun onClickOperation(operation: MathOperation) {
        numpadListener?.onClickOperation(operation)
    }

    override fun onClickAction(action: ActionOperation) {
        numpadListener?.onActionEqual()
    }

    override fun onActionAllClear() {
        numpadListener?.onActionAllClear()
    }

    override fun onActionEqual() {
        numpadListener?.onActionEqual()
    }

    override fun onActionBackspace() {
        numpadListener?.onActionBackspace()
    }

    override fun onActionGroupClean() {
        numpadListener?.onActionGroupClean()
    }
}