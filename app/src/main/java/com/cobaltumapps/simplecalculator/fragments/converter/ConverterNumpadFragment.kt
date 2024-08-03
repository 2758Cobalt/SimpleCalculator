package com.cobaltumapps.simplecalculator.fragments.converter

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
import com.cobaltumapps.simplecalculator.interfaces.AdvancedNumpadListener
import com.cobaltumapps.simplecalculator.services.VibratorService


class ConverterNumpadFragment: Fragment(), AdvancedNumpadListener {
    // Сервисы
    var vibrator = VibratorService()

    private var numpadListener: AdvancedNumpadListener? = null

    private var numberButtons: Array<Button> = arrayOf()                            // Массив с ссылками кнопок цифр

    private lateinit var clearAllButton: Button
    private lateinit var pointButton: Button
    private lateinit var backspaceButton: Button
    private lateinit var hundredButton: Button
    private lateinit var enterButton: Button

    private lateinit var memorySaveButton: Button
    private lateinit var memoryReadButton: Button
    private lateinit var memoryClearButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
          return inflater.inflate(R.layout.fragment_converter_numpad, container, false) // View фрагмента
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Назначаем вибратору конекст
        vibrator.context = requireContext()

        clearAllButton = view.findViewById(R.id.converterButtonAllClear)
        backspaceButton = view.findViewById(R.id.converterButtonBackSpace)
        pointButton = view.findViewById(R.id.converterButtonPoint)
        hundredButton = view.findViewById(R.id.converterButtonHundred)
        enterButton = view.findViewById(R.id.converterButtonEnter)

        memorySaveButton = view.findViewById(R.id.memorySave)
        memoryReadButton = view.findViewById(R.id.memoryRead)
        memoryClearButton = view.findViewById(R.id.memoryClear)

        val numberButtonIds by lazy { listOf(
            R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5,
            R.id.num6, R.id.num7, R.id.num8, R.id.num9)
        }
        // Создание и запись ссылок в массив
        numberButtons = numberButtonIds.map { view.findViewById<Button>(it) }.toTypedArray()


        // Присвоение слушателя нажатий (OnClickListener) кнопкам с операндами
        for ((index,numberButton) in numberButtons.withIndex()) {
            numberButton.setOnClickListener { onClickNumberButton(numberButtons[index].text.toString()) } // Слушатель нажатий для всего нампада(numpad)
        }


        // Слушатель нажатий кнопок функций
        enterButton.setOnClickListener { onClickEnterButton() }
        clearAllButton.setOnClickListener { onClickAllClearButton() }
        pointButton.setOnClickListener { onClickPointButton() }
        hundredButton.setOnClickListener {
            onClickNumberButton("0")
            onClickNumberButton("0") }

        backspaceButton.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                // Удаляем слушатель, чтобы он больше не вызывался
                backspaceButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        memorySaveButton.setOnClickListener { onClickMemorySave() }
        memoryReadButton.setOnClickListener { onClickMemoryRead() }
        memoryClearButton.setOnClickListener { onClickMemoryClear() }


        val delay = 500
        val handler = Handler()
        val deleteAction = object : Runnable {
            override fun run() {
                onClickBackSpaceButton()
                handler.postDelayed(this, 100L)
            }
        }

        backspaceButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // При нажатии
                    onClickBackSpaceButton()
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

    fun setNumpadListener(listener: AdvancedNumpadListener){
        numpadListener = listener
    }

    fun removeNumpadListener(){
        numpadListener = null
    }

    override fun onClickMemorySave() {
        numpadListener?.onClickMemorySave()
        playVibration(5)
    }

    override fun onClickMemoryRead() {
        numpadListener?.onClickMemoryRead()
        playVibration(5)
    }

    override fun onClickMemoryClear() {
        numpadListener?.onClickMemoryClear()
        playVibration(5)
    }

    override fun onClickNumberButton(number: String) {
        numpadListener?.onClickNumberButton(number)
        playVibration(5)
    }

    override fun onClickPointButton() {
        numpadListener?.onClickPointButton()
        playVibration(5)
    }

    override fun onClickAllClearButton() {
        numpadListener?.onClickAllClearButton()
        playVibration(5)
    }

    override fun onClickBackSpaceButton() {
        numpadListener?.onClickBackSpaceButton()
        playVibration(3)
    }

    override fun onClickEnterButton() {
        numpadListener?.onClickEnterButton()
        playVibration(5)
    }

    private fun playVibration(duration: Long){
        vibrator.playVibration(duration)
    }


}

