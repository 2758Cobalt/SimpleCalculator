package com.cobaltumapps.simplecalculator.fragments.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.references.Services

class DisplayFragment: Fragment() {
    private val LOG_TAG = "Display"
    private var previousLineCount = 1

    private lateinit var calculatorFragment: CalculatorFragment

    private lateinit var inputTextField: TextView
    private lateinit var resultTextField: TextView
    private lateinit var lastExpressionTextField: TextView
    private lateinit var memoryTextField: TextView
    private lateinit var angleTypeTextField: TextView

    var textFields: Array<TextView> = arrayOf()

    fun setParentFragment(parent: CalculatorFragment){
        calculatorFragment = parent
    }

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
        angleTypeTextField = view.findViewById(R.id.angleTypeField)

        // Стандартные значения для свойств
        textFields = arrayOf(inputTextField,resultTextField,memoryTextField)

        lastExpressionTextField.setOnClickListener {
            Animations.animatePropertyChange(
                lastExpressionTextField,
                "textSize",
                24f,
                20f,
                200,
                Animations.overshootInterpolator
            )
            if (StringBuilder(lastExpressionTextField.text)[0] != '0') // Если поле с последним вводом равно нулю или пустое
            {
                inputTextField.text = lastExpressionTextField.text
                resultTextField.text = "0"
                lastExpressionTextField.text = "0"
                calculatorFragment.calculateExpression()
            }
        }

        lastExpressionTextField.setOnLongClickListener {
            clearLastExpression()
            true }

        resultTextField.setOnClickListener {
            Services.copyToClipboard(requireContext(), resultTextField.text.toString())
        }

        // Наблюдатель за изменениях текста
        inputTextField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(p0: Editable?) {

                // Проверка количества строк
                val lineCount = inputTextField.lineCount

                // Если количество строк изменилось, вызов requestLayout() - перерасчёта макета
                if (lineCount != previousLineCount) {
                    inputTextField.requestLayout()
                    previousLineCount = lineCount
                }
            }

        })
    }

    fun clearLastExpression(){
        Animations.animatePropertyChange(
            lastExpressionTextField,
            "textSize",
            24f,
            20f,
            200,
            Animations.overshootInterpolator
        )
        lastExpressionTextField.text = "0"
    }


    fun updateAngleType(isDegree: Boolean){
        angleTypeTextField.setText( if(isDegree) R.string.text_degrees else R.string.text_radians)
        Animations.animatePropertyChange(
            angleTypeTextField,
            "textSize",
            16f,
            12f,
            400,
            Animations.overshootInterpolator
        )
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
                getString(R.string.symbolAdd),
                getString(R.string.symbolMul),  getString(R.string.symbolDiv),
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


    // Добавляет символ к содержимому текстового поля
    fun enterToLastExpression() {
        lastExpressionTextField.text = inputTextField.text
        Animations.animatePropertyChange(
            lastExpressionTextField,
            "textSize",
            24f,
            20f,
            400,
            Animations.overshootInterpolator
        )
    }

    fun setInputField(newInput: String) {
        inputTextField.text = newInput
        checkTextFields()
    }

    // Принудительно назначает результат
    fun setResultField(newResult: String) {
        resultTextField.text = newResult
        checkTextFields()
    }
    fun setLastField(newLast: String) {
        lastExpressionTextField.text = newLast
        checkTextFields()
    }

    fun getResultField(): String{
        return this.resultTextField.text.toString()
    }

    fun getInputField():String{
        return this.inputTextField.text.toString()
    }

    fun getLastField(): String{
        return this.lastExpressionTextField.text.toString()
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

    fun getInputStringBuilder(): StringBuilder {
        return StringBuilder(inputTextField.text.toString())
    }

}