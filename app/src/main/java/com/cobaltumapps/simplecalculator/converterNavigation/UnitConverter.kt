package com.cobaltumapps.simplecalculator.converterNavigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.adapters.ConverterRecyclerAdapter
import com.cobaltumapps.simplecalculator.interfaces.NumpadListener
import com.cobaltumapps.simplecalculator.references.LogTags
import com.google.android.material.textfield.TextInputEditText

/*
* Предоставляет пустышку-макет для отображения конвертеров */
open class UnitConverter: Converter(), NumpadListener {
    private lateinit var titleConverterField: TextView

    private lateinit var thumbnailView: ImageView

    private lateinit var unitTypeSpinner: Spinner

    // Адаптер для списка с конвертерами
    private lateinit var converterAdapter: ConverterRecyclerAdapter

    // Поле ввода
    private lateinit var unitInputField: TextInputEditText

    private lateinit var recylcer: RecyclerView

    // Набор величин для конвертации
    protected lateinit var unitsModelList: ArrayList<UnitModel> // Нужен для передачи модели в адаптер
    protected lateinit var specialSymbols: List<String> // Нужен для указания спец. символов в адаптере
    protected lateinit var unitsParams: List<String> // Нужен для указания данных в спинере

    protected var valuesToConvert : Array<Array<Double>> = arrayOf(arrayOf())

    // Определяет выбраную величину
    protected var currentSelectedTextInput: TextInputEditText? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        title = "Nonverter"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_converter, container, false)

        titleConverterField = view.findViewById(R.id.converterUnitTitle)

        thumbnailView = view.findViewById(R.id.converterThumbnail)

        recylcer = view.findViewById(R.id.recyclerConverter)

        unitInputField = view.findViewById(R.id.unitInputField)
        unitTypeSpinner = view.findViewById(R.id.unitTypeSpinner)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создание адаптера для recycler view
        converterAdapter = ConverterRecyclerAdapter(requireContext(), unitsModelList, specialSymbols)

        // Настройка recycler view
        recylcer.apply {
            adapter = converterAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // Если изображение есть - устанавливаем его, нет - Скрываем рамку
        if (thumbnailResource != null) thumbnailView.setImageDrawable(ResourcesCompat.getDrawable(resources,thumbnailResource!!,requireContext().theme))
        else thumbnailView.visibility = View.GONE


        unitTypeSpinner.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                calculate()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        unitInputField.requestFocus()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("converter_unitInput_key",unitInputField.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null){
            unitInputField.setText(savedInstanceState.getString("converter_unitInput_key","0"))
            calculate()
        }
    }

    fun setupData(dataId: Int){

        // <- Настройка спинера ->

        // 1. Создаём и настраиваем адаптер
        val unitSpinnerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, listOf<String>())
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 2. Присваиваем адаптер спинеру
        unitTypeSpinner.adapter = unitSpinnerAdapter

    }



    fun calculate(){
        for (index in valuesToConvert.indices){
            val result = currentSelectedTextInput!!.text.toString().toDouble() * valuesToConvert[unitTypeSpinner.selectedItemPosition][index]
            converterAdapter.updateItem(index,result,
                index == unitTypeSpinner.selectedItemPosition
            )

        }

    }

    override fun onClickNumberButton(number: String) {
        if (currentSelectedTextInput != null){
            if (currentSelectedTextInput!!.text!!.toString() == "0"){
                currentSelectedTextInput!!.setText(number)
            }
            else
                currentSelectedTextInput!!.append(number)


            calculate()
        }
        else{
            Log.e(LogTags.LOG_CONVERTER,"ERROR: TextInput is NULL")
        }
    }

    override fun onClickPointButton() {
        if (currentSelectedTextInput != null){
            if (!currentSelectedTextInput!!.text!!.toString().contains('.')){
                currentSelectedTextInput!!.text!!.append('.')
                calculate()
            }

        }
    }

    override fun onClickAllClearButton() {
        currentSelectedTextInput!!.setText("0")
        calculate()
    }

    override fun onClickBackSpaceButton() {
        if (currentSelectedTextInput!!.text!!.isNotEmpty()){
            val currentText = currentSelectedTextInput!!.text.toString()
            val newText = currentText.substring(0, currentText.length - 1)

            currentSelectedTextInput!!.setText(newText)

            // Устанавка курсора в конец текста
            currentSelectedTextInput!!.setSelection(newText.length)

            // Проверяем пусто ли поле ввода
            if (currentSelectedTextInput!!.text!!.isEmpty()){

                currentSelectedTextInput!!.setText("0")
            }
            calculate()
        }

    }

}

