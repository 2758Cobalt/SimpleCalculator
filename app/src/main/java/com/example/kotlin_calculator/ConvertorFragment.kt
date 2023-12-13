package com.example.kotlin_calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

class ConvertorFragment: Fragment() {
    private lateinit var constants : Array<String>

    private val constantsData = Constants.constantsData

    private lateinit var labelConvertor : TextView
    private lateinit var tooltipConvertor : TextView
    private lateinit var resultConvert : TextView

    private lateinit var dropDownFrom : Spinner
    private lateinit var dropDownTo : Spinner

    private lateinit var valueForConvert: EditText

    private lateinit var buttonReset : ImageButton

    private var dataId = 0 // Набор данных

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_conventor, container, false) // View фрагмента

        labelConvertor = view.findViewById(R.id.labelConvertor)
        tooltipConvertor = view.findViewById(R.id.tooltipConvertor)

        valueForConvert = view.findViewById(R.id.inputValue)

        resultConvert = view.findViewById(R.id.resultConvertation)

        dropDownFrom = view.findViewById(R.id.dropDownFrom)
        dropDownTo = view.findViewById(R.id.dropDownTo)

        buttonReset = view.findViewById(R.id.buttonReset)

        buttonReset.setOnClickListener { resetFieldsAction() }

        val spinnerDataSet = dataSetIdGetting(dataId)

        ArrayAdapter.createFromResource(
            requireContext(),
            spinnerDataSet,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Установка строкового массива
            // Применение адаптера к спинеру
            dropDownFrom.adapter = adapter
            dropDownTo.adapter = adapter
        }
        dropDownFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) { convertAction() }
            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        dropDownTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) { convertAction() }
            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {} // Вызывается до изменения текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { convertAction() } // Вызывается во время изменения текста
            override fun afterTextChanged(p0: Editable?) {} // Вызывается после изменения текста
        }

        valueForConvert.addTextChangedListener(textWatcher)

        return view
    }

    private fun resetFieldsAction(){
        resultConvert.text = ""
        valueForConvert.text.clear()
    }

    private fun convertAction(){
        try{
            val dataMatrixCoefficient = dataToConvert(dataId)

            val inputValue = valueForConvert.text.toString().toDouble()
            var result : Double

            // Получаем выбранные единицы измерения из выпадающих списков
            val fromUnit = dropDownFrom.selectedItem.toString()
            val toUnit = dropDownTo.selectedItem.toString()

            // Получаем индексы выбранных единиц измерения в массиве constants
            val fromIndex = constants.indexOf(fromUnit)
            val toIndex = constants.indexOf(toUnit)

            // Расчёт результата конвертации
            result = inputValue / dataMatrixCoefficient[fromIndex][toIndex]
            Log.i("DebugTag",dataMatrixCoefficient[fromIndex][toIndex].toString())

            // Вывод результата
            resultConvert.text = result.toString()

        }
        catch (_: NumberFormatException){
        }


    }
    private fun dataSetIdGetting(dataSetIndex: Int): Int {
        // Назначает нужный датасет и подготавливает подсказки конвертора под выбранный датасет
        val dataSet = constantsData[dataSetIndex]
        resultConvert.hint = resources.getText(dataSet.first)
        labelConvertor.text = resources.getText(dataSet.first)
        tooltipConvertor.text = resources.getText(dataSet.second)
        constants = resources.getStringArray(dataSet.third)
        return dataSet.third
    }

    private fun dataToConvert(dataResId: Int): Array<Array<Double>> // Принцип работы представлен в Constains.kt
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
            else -> emptyArray()
        }
    }

    fun dataIdGet() : Int{
        return this.dataId
    }

    fun dataIdSet(newDataSetId : Int){
        this.dataId = newDataSetId
    }

}
