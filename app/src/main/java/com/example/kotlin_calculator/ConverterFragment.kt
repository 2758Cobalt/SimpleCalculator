package com.example.kotlin_calculator

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class ConverterFragment: Fragment() {
    private var dataArrayPicker: Array<String> = arrayOf()
    private lateinit var constants : Array<String>
    private var dataId: Int = 0 // Набор данных
    private val constantsData = Constants.constantsConvertsData

    private lateinit var labelConvertor : TextView
    private lateinit var tooltipConvertor : TextView

    private lateinit var firstPicker : NumberPicker
    private lateinit var secondPicker : NumberPicker

    private lateinit var firstInputValue: EditText
    private lateinit var secondInputValue: EditText
    private var focusedField: EditText? = null

    private lateinit var buttonReset : ImageButton
    private lateinit var buttonNextPage : ImageButton
    private lateinit var buttonPreviousPage : ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_converter, container, false) // View фрагмента

        // Ссылки на picker
        firstPicker = view.findViewById(R.id.firstNumberPicker)
        secondPicker = view.findViewById(R.id.secondNumberPicker)

        // Ссылки на заголовок и описание
        labelConvertor = view.findViewById(R.id.labelConvertor)
        tooltipConvertor = view.findViewById(R.id.tooltipConvertor)

        // Ссылки на поля ввода
        firstInputValue = view.findViewById(R.id.firstInputField)
        secondInputValue = view.findViewById(R.id.secondInputField)
        focusedField = firstInputValue

        // Ссылка на кнопки
        buttonReset = view.findViewById(R.id.buttonReset)
        buttonNextPage = view.findViewById(R.id.buttonNextConverter)
        buttonPreviousPage = view.findViewById(R.id.buttonPreviousConverter)

        // Назначение слушателя нажатий
        val btnBack = view.findViewById(R.id.backToMenu) as ImageButton
        btnBack.setOnClickListener {parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, SelectorFragment()).commit()  }

        // Слушатель нажатий для кнопок сброса и переключения
        buttonReset.setOnClickListener { resetFieldsAction() }                            // Сбрасывает поля
        buttonNextPage.setOnClickListener { replaceDataFragment(dataId + 1) }          // Замена данных на новые
        buttonPreviousPage.setOnClickListener { replaceDataFragment(dataId - 1) }      // Замена данных на новые

        // Получение набора данных
        dataArrayPicker = resources.getStringArray(dataSetIdGetting(dataId))
        configureNumberPicker(firstPicker,dataArrayPicker)
        configureNumberPicker(secondPicker,dataArrayPicker)

        firstPicker.setOnValueChangedListener { _, _, newVal ->
            convertAction(true)
        }
        secondPicker.setOnValueChangedListener { _, _, newVal ->
            convertAction(true)
        }

        // Наблюдатель текста, следит за изменением текста и вызывает соответственные методы
        val textWatcherFirst = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {} // Вызывается до изменения текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { if(focusedField != null) convertAction(false) } // Вызывается во время изменения текста
            override fun afterTextChanged(p0: Editable?) {} // Вызывается после изменения текста
        }
        val textWatcherSecond = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {} // Вызывается до изменения текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { if(focusedField != null) convertAction(false) } // Вызывается во время изменения текста
            override fun afterTextChanged(p0: Editable?) {} // Вызывается после изменения текста
        }

        // Назначениет наблютателя
        firstInputValue.addTextChangedListener(textWatcherFirst)
        secondInputValue.addTextChangedListener(textWatcherSecond)

        // Назначает цвет подчёркивания editText
        val colorStateEnabled = ContextCompat.getColor(requireContext(), R.color.orange)
        val colorStateDisabled = ContextCompat.getColor(requireContext(), R.color.white240)

        // Слушатель нажатий на поля ввода (Смена параметра backgroundTint)
        firstInputValue.setOnFocusChangeListener { _, _ ->
            focusedField = firstInputValue
            firstInputValue.backgroundTintList = ColorStateList.valueOf(colorStateEnabled)
            secondInputValue.backgroundTintList = ColorStateList.valueOf(colorStateDisabled)
        }
        secondInputValue.setOnFocusChangeListener{ _, _ ->
            focusedField = secondInputValue
            secondInputValue.backgroundTintList = ColorStateList.valueOf(colorStateEnabled)
            firstInputValue.backgroundTintList = ColorStateList.valueOf(colorStateDisabled)
        }

        return view
    }

    private fun resetFieldsAction() // Сбрасывает поля
    {
        firstInputValue.text.clear()
        secondInputValue.text.clear()
    }

    private fun convertAction(ignoreTextCheck: Boolean) // Расчитывает результат конвертации
    {
        try{
            val dataMatrixCoefficient = constantsSelection(dataId)
            var inputValue = 0.0
            var result : Double

            val newText = focusedField?.text.toString()
            if (newText == focusedField?.tag && !ignoreTextCheck) { // Текст не изменился
                return
            }

            // Сохранение нового текста в качестве тега
            focusedField?.tag = newText
            if(focusedField!!.text.isNotEmpty()) inputValue = newText.toDouble() else return

            // Получаем выбранные единицы измерения из NumberPicker
            val fromUnit = dataArrayPicker[firstPicker.value] // Перевод из чего
            val toUnit = dataArrayPicker[secondPicker.value] // Перевод во что

            // Получаем индексы выбранных единиц измерения в массиве constants
            val fromIndex = constants.indexOf(fromUnit)
            var toIndex = constants.indexOf(toUnit)

            // Вывод результата
            if(focusedField!! == firstInputValue){// Если первое активное поле
                result = inputValue / dataMatrixCoefficient[toIndex][fromIndex]
                secondInputValue.setText(result.toString())
            }
            if(focusedField!! == secondInputValue){ // Если второе активное поле
                result = inputValue / dataMatrixCoefficient[fromIndex][toIndex]
                firstInputValue.setText(result.toString())
            }


        }
        catch (ex: NumberFormatException){
            Toast.makeText(context,"Перехвачено исключение: ${ex}",Toast.LENGTH_SHORT).show()
        }
        catch (ex: ArrayIndexOutOfBoundsException){
            Toast.makeText(context,"Перехвачено исключение: ${ex}",Toast.LENGTH_SHORT).show()
        }
    }

    private fun dataSetIdGetting(dataSetIndex: Int): Int {
        // Назначает нужный датасет и подготавливает подсказки конвертора под выбранный датасет
        val dataSet = constantsData[dataSetIndex]
        labelConvertor.text = resources.getText(dataSet.first)
        tooltipConvertor.text = resources.getText(dataSet.second)
        constants = resources.getStringArray(dataSet.third)
        return dataSet.third
    }

    private fun constantsSelection(dataResId: Int): Array<Array<Double>> {
        return when (dataResId) {
            in 0 until Constants.CONVERTERS.size -> {
                Constants.CONVERTERS[dataResId]
            }
            else -> emptyArray()
        }
    }

    private fun replaceDataFragment(newDataSet : Int) // Заново назначает все данные из массивов
    {
        dataIdSet(newDataSet)
        focusedField = firstInputValue
        firstInputValue.text.clear()
        secondInputValue.text.clear()
        val selection = constantsSelection(dataId)
        if(selection.isEmpty()) dataId = 0
        dataSetIdGetting(dataId)
        dataArrayPicker = constants
        configureNumberPicker(firstPicker,dataArrayPicker)
        configureNumberPicker(secondPicker,dataArrayPicker)
    }

    private fun configureNumberPicker(numberPicker: NumberPicker, dataArray: Array<String>) {
        numberPicker.minValue = 0
        numberPicker.maxValue = dataArray.size - 1
        numberPicker.displayedValues = dataArray
        numberPicker.wrapSelectorWheel = false
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker.value = 0

        // Назначаем массив данных
        numberPicker.displayedValues = dataArray
    }

    fun dataIdSet(newDataSetId : Int){
        this.dataId = newDataSetId
    }

}
