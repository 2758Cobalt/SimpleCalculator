package com.cobaltumapps.simplecalculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.references.Animations

class DisplayFragment: Fragment() {
    private val LOG_TAG = "Display"

    private lateinit var inputTextField: TextView
    private lateinit var resultTextField: TextView
    private lateinit var lastExpressionTextField: TextView
    private lateinit var memoryTextField: TextView
    private lateinit var inputTypeTextField: TextView

    var textFields: Array<TextView> = arrayOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_display, container, false) // View фрагмента
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Текстовые поля
        inputTextField = view.findViewById(R.id.inputField)
        resultTextField= view.findViewById(R.id.outputField)
        lastExpressionTextField = view.findViewById(R.id.lastExpressionField)
        memoryTextField = view.findViewById(R.id.memoryField)
        inputTypeTextField = view.findViewById(R.id.typeLabel)

        // Стандартные значения для свойств
        textFields = arrayOf(inputTextField,resultTextField,memoryTextField)

        lastExpressionTextField.setOnClickListener {
            Animations.animatePropertyChange(lastExpressionTextField,"textSize", 28f, 24f, 200, Animations.overshootInterpolator)
            if (StringBuilder(lastExpressionTextField.text)[0] != '0') // Если поле с последним вводом равно нулю или пустое
            {
                inputTextField.text = lastExpressionTextField.text
                resultTextField.text = "0"
                lastExpressionTextField.text = "0"
            }
        }

        lastExpressionTextField.setOnLongClickListener {
            Animations.animatePropertyChange(lastExpressionTextField,"textSize", 28f, 24f, 200, Animations.overshootInterpolator)
            lastExpressionTextField.text = "0"
            true }
    }

    fun calculateForDeg(toDeg: Boolean){
        if (toDeg){
            inputTypeTextField.setText(R.string.symbolDeg)
        }
        else{
            inputTypeTextField.setText(R.string.symbolRad)
        }
        Animations.animatePropertyChange(inputTypeTextField,"textSize", 16f, 12f, 400, Animations.overshootInterpolator)
    }

    // Добавляет символ к содержимому текстового поля с введёнными данными
    fun enterToExpression(symbol: String) {
        val inputData = StringBuilder(inputTextField.text)
        // Проверка первго символа
        if (inputData.isNotEmpty()
            && inputData[0] == '0'
            && inputData.length < 2
            && symbol != "0"
            && symbol !in setOf(
                getString(R.string.symbolAdd), getString(R.string.symbolSub),
                getString(R.string.symbolMul), getString(R.string.symbolDiv),
                getString(R.string.symbolPoint))
            )
        {
            // Если первый символ - 0, замена всего текста на новый символ
            inputTextField.text = symbol
        } else {

            // Иначе, добавление к тексту нового символа
            inputTextField.append(symbol)
        }
        checkTextFields()
    }

    fun getInputStringBuilder(): StringBuilder {
        return StringBuilder(inputTextField.text.toString())
    }

    // Добавляет символ к содержимому текстового поля
    fun enterToLastExpression() {
        lastExpressionTextField.text = inputTextField.text
        Animations.animatePropertyChange(lastExpressionTextField,"textSize", 28f, 24f, 400, Animations.overshootInterpolator)
    }

    fun setInputField(result: String) {
        inputTextField.text = result
        checkTextFields()
    }

    // Принудительно назначает результат
    fun setResultField(result: String) {
        resultTextField.text = result
        checkTextFields()
        Animations.animatePropertyChange(resultTextField,"textSize", 20f, 18f, 100, Animations.overshootInterpolator)
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

        if (inputTextField.text.isEmpty())
            inputTextField.text = "0" // назначает принудительно в поле ввода - символ '0'

        if (resultTextField.text.isEmpty())
            resultTextField.text = "0" // назначает принудительно в поле вывода - символ '0'

        if (memoryTextField.text.isEmpty())
            memoryTextField.text = "0" // назначает принудительно в поле памяти - символ '0'

    }

    // Метод, отвечающий за очитку поля или полей
    fun clearTextField(inputField: Boolean = false, resultField: Boolean = false, memoryField: Boolean = false, lastField: Boolean = false){
        if (inputField)
            inputTextField.text = "0"
        if (lastField)
            lastExpressionTextField.text = "0"
        if (resultField)
            resultTextField.text = "0"
        if (memoryField)
            memoryTextField.text = "0"

        checkTextFields()
    }

}

