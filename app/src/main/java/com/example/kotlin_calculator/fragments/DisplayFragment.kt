package com.example.kotlin_calculator.fragments

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kotlin_calculator.R
import com.example.kotlin_calculator.references.Animations

class DisplayFragment: Fragment() {
    private val LOG_TAG = "Display"
    private lateinit var inputTextField: TextView
    private lateinit var outputTextField: TextView
    private lateinit var lastExpressionTextField: TextView
    private lateinit var memoryTextField: TextView
    private val interpolator = OvershootInterpolator()

    var textFields: Array<TextView> = arrayOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calculator_display, container, false) // View фрагмента
         
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Текстовые поля
        inputTextField = view.findViewById(R.id.inputField)
        outputTextField= view.findViewById(R.id.outputField)
        lastExpressionTextField = view.findViewById(R.id.lastExpressionField)
        memoryTextField = view.findViewById(R.id.memoryField)

        // Настройка текстового поля
        outputTextField.setTextSize(TypedValue.COMPLEX_UNIT_SP,(inputTextField.textSize / resources.displayMetrics.scaledDensity) / 2)

        // Стандартные значения для свойств
        textFields = arrayOf(inputTextField,outputTextField,memoryTextField)

        lastExpressionTextField.setOnClickListener {
            Animations.animatePropertyChange(lastExpressionTextField,"textSize", 24f, 26f, 200, interpolator)
            if (inputTextField.text.isEmpty() || StringBuilder(inputTextField.text)[0] == '0') // Если поле с последним вводом равно нулю или пустое
            {
                inputTextField.text = lastExpressionTextField.text
                outputTextField.text = "0"
            }
        }
    }

    // Добавляет символ к содержимому текстового поля с введёнными данными
    fun enterToExpression(symbol: String) {
        val inputData = StringBuilder(inputTextField.text)

        // Проверка первго символа
        if (inputData.isNotEmpty() && inputData[0] == '0') {
            // Если первый символ - 0, замена всего текста на новый символ
            inputTextField.text = symbol
        } else {
            // Иначе, добавление к тексту нового символа
            inputTextField.append(symbol)
        }
        checkTextFields()
    }

    // Добавляет символ к содержимому текстового поля
    fun enterToLastExpression() {
        lastExpressionTextField.text = inputTextField.text
        Animations.animatePropertyChange(lastExpressionTextField,"textSize", 24f, 26f, 200, interpolator)
    }

    fun setInputField(result: String)
    {
        inputTextField.text = result
        checkTextFields()
    }

    // Принудительно назначает результат
    fun setResultField(result: String)
    {
        outputTextField.text = result
        checkTextFields()
        Animations.animatePropertyChange(inputTextField,"textSize", 48f, 26f, 200, interpolator)
        Animations.animatePropertyChange(outputTextField,"textSize", 20f, 26f, 200, interpolator)
    }

    // Принудительно назначает память
    fun setMemoryField(symbol: String){
        memoryTextField.text = symbol
        checkTextFields()
    }

    fun clearLastSymbolFromExpression(textView: TextView): Char{
        var deletedChar = ' '
        if (inputTextField.text.isNotEmpty()){
            val stringBuilder = StringBuilder(textView.text.toString())
            deletedChar = stringBuilder[stringBuilder.lastIndex]
            stringBuilder.deleteCharAt(stringBuilder.lastIndex)
            textView.text = stringBuilder
        }
        return deletedChar
    }

    fun checkTextFields(){
        // Функция, которая имеет ряд проверок, по результату которых переделывает поле ввода
        if (inputTextField.text.isEmpty()){
            inputTextField.text = "0" // назначает принудительно в поле ввода - символ '0'
        }
        if (outputTextField.text.isEmpty()){
            outputTextField.text = "0" // назначает принудительно в поле ввода - символ '0'
        }
        if (memoryTextField.text.isEmpty()){
            memoryTextField.text = "0" // назначает принудительно в поле ввода - символ '0'
        }

        Log.i(LOG_TAG,"Input: ${inputTextField.text}\nOutput${outputTextField.text}\nMemory:${memoryTextField.text}")
    }

    // Метод, отвечающий за очитку поля или полей
    fun clearTextField(inputField: Boolean = false, resultField: Boolean = false, memoryField: Boolean = false){
        if (inputField)
            inputTextField.text = "0"; lastExpressionTextField.text = ""
        if (resultField)
            outputTextField.text = "0"
        if (memoryField)
            memoryTextField.text = "0"

        checkTextFields()
    }

}

