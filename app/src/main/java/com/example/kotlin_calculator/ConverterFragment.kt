package com.example.kotlin_calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class ConverterFragment: Fragment() {
    private lateinit var constants : Array<String>
    private val constantsData = Constants.constantsConvertsData

    private lateinit var labelConvertor : TextView
    private lateinit var tooltipConvertor : TextView

    private lateinit var dropDownFirst : Spinner
    private lateinit var dropDownSecond : Spinner

    private lateinit var firstInputValue: EditText
    private lateinit var secondInputValue: EditText
    private var focusedField: EditText? = null

    private lateinit var buttonReset : ImageButton
    private lateinit var buttonNextPage : ImageButton
    private lateinit var buttonPreviousPage : ImageButton

    private var dataId = 0 // Набор данных

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.converter_test, container, false) // View фрагмента

        labelConvertor = view.findViewById(R.id.labelConvertor)
        tooltipConvertor = view.findViewById(R.id.tooltipConvertor)

        firstInputValue = view.findViewById(R.id.firstInputField)
        secondInputValue = view.findViewById(R.id.secondInputField)

        dropDownFirst = view.findViewById(R.id.dropDownFirst)
        dropDownSecond = view.findViewById(R.id.dropDownSecond)

        buttonReset = view.findViewById(R.id.buttonReset)
        buttonNextPage = view.findViewById(R.id.buttonNextConverter)
        buttonPreviousPage = view.findViewById(R.id.buttonPreviousConverter)

        val btnBack = view.findViewById(R.id.backToMenu) as ImageButton
        btnBack.setOnClickListener {parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, SelectorFragment()).commit()  }

        buttonReset.setOnClickListener { resetFieldsAction() }
        buttonNextPage.setOnClickListener { dataId += 1; resetFragmentData() }
        buttonPreviousPage.setOnClickListener { dataId -= 1; resetFragmentData() }

        updateSpinnerAdapter(dataId)

        dropDownFirst.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) { if(focusedField != null) convertAction() }
            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        val textWatcherFirst = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {} // Вызывается до изменения текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { if(focusedField != null) convertAction() } // Вызывается во время изменения текста
            override fun afterTextChanged(p0: Editable?) {} // Вызывается после изменения текста
        }

        dropDownSecond.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) { if(focusedField != null) convertAction() }
            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        val textWatcherSecond = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {} // Вызывается до изменения текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { if(focusedField != null) convertAction() } // Вызывается во время изменения текста
            override fun afterTextChanged(p0: Editable?) {} // Вызывается после изменения текста
        }

        firstInputValue.addTextChangedListener(textWatcherFirst)
        secondInputValue.addTextChangedListener(textWatcherSecond)

        firstInputValue.setOnFocusChangeListener { _, _ ->
            focusedField = firstInputValue
        }
        secondInputValue.setOnFocusChangeListener { _, _ ->
            focusedField = secondInputValue
        }

        return view
    }

    private fun resetFieldsAction(){
        focusedField?.text?.clear()
    }

    private fun convertAction(){
        try{
            val dataMatrixCoefficient = constantsSelection(dataId)
            var inputValue = 0.0
            var result : Double

            val newText = focusedField?.text.toString()
            if (newText == focusedField?.tag) { // Текст не изменился
                return
            }

            // Сохранение нового текста в качестве тега
            focusedField?.tag = newText
            if(focusedField!!.text.isNotEmpty()) inputValue = newText.toDouble() else return


            // Получаем выбранные единицы измерения из выпадающих списков
            val fromUnit = dropDownFirst.selectedItem.toString()
            val toUnit = dropDownSecond.selectedItem.toString()

            // Получаем индексы выбранных единиц измерения в массиве constants
            val fromIndex = constants.indexOf(fromUnit)
            val toIndex = constants.indexOf(toUnit)

            // Вывод результата
            result = inputValue / dataMatrixCoefficient[fromIndex][toIndex]

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
    private fun resetFragmentData(){

            val selection = constantsSelection(dataId)
            if(selection.isEmpty()) dataId = 0

            dataSetIdGetting(dataId)
            convertAction()
            updateSpinnerAdapter(dataId)
    }
    private fun updateSpinnerAdapter(dataSetId: Int){
        ArrayAdapter.createFromResource(
            requireContext(),
            dataSetIdGetting(dataSetId),
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Установка строкового массива
            // Применение адаптера к спинеру
            dropDownFirst.adapter = adapter
            dropDownSecond.adapter = adapter
        }
    }

    fun dataIdGet() : Int{
        return this.dataId
    }

    fun dataIdSet(newDataSetId : Int){
        this.dataId = newDataSetId
    }

}
