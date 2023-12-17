package com.example.kotlin_calculator

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment


class ConverterFragment: Fragment() {
    private lateinit var constants : Array<String>
    private val constantsData = Constants.constantsConvertsData

    private lateinit var labelConvertor : TextView
    private lateinit var tooltipConvertor : TextView

    private lateinit var testPicker : NumberPicker
    private lateinit var testPickerSecond : NumberPicker

    private lateinit var firstInputValue: EditText
    private lateinit var secondInputValue: EditText
    private var focusedField: EditText? = null
    private var dataArrayPicker: Array<String> = arrayOf()

    private lateinit var buttonReset : ImageButton
    private lateinit var buttonNextPage : ImageButton
    private lateinit var buttonPreviousPage : ImageButton


    private var dataId = 0 // Набор данных

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_converter, container, false) // View фрагмента

        // Ссылки на picker
        testPicker = view.findViewById(R.id.timePicker1)
        testPickerSecond = view.findViewById(R.id.timePicker2)


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

        buttonReset.setOnClickListener { resetFieldsAction() }
        buttonNextPage.setOnClickListener { dataId += 1; resetFragmentData() }
        buttonPreviousPage.setOnClickListener { dataId -= 1; resetFragmentData() }

        // Получение набора данных
        dataArrayPicker = resources.getStringArray(dataSetIdGetting(dataId))
        configureNumberPicker(testPicker,dataArrayPicker)
        configureNumberPicker(testPickerSecond,dataArrayPicker)

        testPicker.setOnValueChangedListener { _, _, newVal ->
            convertAction(true)
        }
        testPickerSecond.setOnValueChangedListener { _, _, newVal ->
            convertAction(true)
        }

        // Назначение слушателя выбора

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

        firstInputValue.addTextChangedListener(textWatcherFirst)
        secondInputValue.addTextChangedListener(textWatcherSecond)

        firstInputValue.setOnFocusChangeListener { _, _ ->
            focusedField!!.background = resources.getDrawable(R.drawable.linearlayout_bg_sheet)
            focusedField = firstInputValue
            focusedField!!.background = resources.getDrawable(R.drawable.style_button_operator)
        }
        secondInputValue.setOnFocusChangeListener { _, _ ->
            focusedField!!.background = resources.getDrawable(R.drawable.linearlayout_bg_sheet)
            focusedField = secondInputValue
            focusedField!!.background = resources.getDrawable(R.drawable.style_button_operator)
        }

        return view
    }

    private fun resetFieldsAction() // Сбрасывает поле в фокусе
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
            val fromUnit = dataArrayPicker[testPicker.value] // Перевод из чего
            val toUnit = dataArrayPicker[testPickerSecond.value] // Перевод во что
            Log.i("DebugTag","Данные FromUnit: ${dataArrayPicker[testPicker.value]}\n ToUnit:${dataArrayPicker[testPickerSecond.value]}")

            // Получаем индексы выбранных единиц измерения в массиве constants
            val fromIndex = constants.indexOf(fromUnit)
            var toIndex = constants.indexOf(toUnit)

            // Вывод результата
            Log.i("DebugTag","Первый спинер $fromIndex, Второй спинер $toIndex")

            result = if (fromIndex < toIndex) {
                Log.i("DebugTag", "От меньшего $fromIndex, к большему $toIndex")
                inputValue / dataMatrixCoefficient[fromIndex][toIndex]
            } else if (fromIndex > toIndex) {
                Log.i("DebugTag", "От большего $fromIndex, к меньшему $toIndex")
                dataMatrixCoefficient[toIndex][fromIndex] / inputValue
            } else {
                inputValue
            }

            if(focusedField == firstInputValue){
                secondInputValue.setText(result.toString())
            }
            if(focusedField == secondInputValue){
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

    private fun constantsSelection(dataResId: Int): Array<Array<Double>> // Принцип работы представлен в Constains.kt
    {
        return when (dataResId) {
            0 -> Constants.weightConvert                // Вес
            1 -> Constants.lengthConvert                // Длина
            2 -> Constants.speedConvert                 // Скорость
            3 -> Constants.dataConvert                  // Объем информации
            4 -> Constants.temperatureConvert           // Температура
            5 -> Constants.volumeConvert                // Объём
            6 -> Constants.frequencyConvert             // Частота
            7 -> Constants.fuelConsumptionConvert       // Расход топлива
            8 -> Constants.pressureConvert              // Давление
            9 -> Constants.powerConvert                 // Мощность
            10 -> Constants.energyConvert               // Енергия
            else -> emptyArray()
        }
    }
    private fun resetFragmentData() // Заново назначает все данные из массивов
    {
        focusedField = firstInputValue
        firstInputValue.text.clear()
        secondInputValue.text.clear()
        val selection = constantsSelection(dataId)
        if(selection.isEmpty()) dataId = 0
        dataSetIdGetting(dataId)
        dataArrayPicker = constants
        configureNumberPicker(testPicker,dataArrayPicker)
        configureNumberPicker(testPickerSecond,dataArrayPicker)
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

    fun dataIdGet() : Int{
        return this.dataId
    }

    fun dataIdSet(newDataSetId : Int){
        this.dataId = newDataSetId
    }

}
