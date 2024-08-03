package com.cobaltumapps.simplecalculator.fragments.converter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.abstractClasses.Converter
import com.cobaltumapps.simplecalculator.adapters.ConverterRecyclerAdapter
import com.cobaltumapps.simplecalculator.enums.ConverterTypes
import com.cobaltumapps.simplecalculator.interfaces.OnAdapterItemClickListener
import com.cobaltumapps.simplecalculator.models.ConverterDataModel
import com.cobaltumapps.simplecalculator.models.UnitRecyclerModel
import java.math.BigDecimal
import java.math.MathContext

/*
* Предоставляет пустышку-макет для отображения конвертеров */
open class UnitConverterFragment: Converter(), OnAdapterItemClickListener {
    var fragmentIsInflated = false
    private var selectedItem = 0
    private var userInput = 0.0

    // Предварительная модель данных конвертера
    private var converterModel = ConverterDataModel()

    private lateinit var converterName: TextView
    private lateinit var iconView: ImageView

    // Адаптер для списка с конвертерами
    private lateinit var converterAdapter: ConverterRecyclerAdapter

    private lateinit var recylcer: RecyclerView

    // Набор величин для конвертации
    protected var unitsModelList: ArrayList<UnitRecyclerModel> = arrayListOf() // Нужен для передачи модели в адаптер
    protected var specialSymbolsList: List<String> = listOf() // Нужен для указания спец. символов в адаптере
    protected var unitsNamesList: MutableList<String> = mutableListOf() // Нужен для указания данных в спинере

    protected var valuesToConvert : Array<Array<Double>> = arrayOf(arrayOf())

    override fun onAttach(context: Context) {
        super.onAttach(context)

        title = "Nonverter"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_converter, container, false)

        converterName = view.findViewById(R.id.converterUnitTitle)
        iconView = view.findViewById(R.id.converterUnitIcon)

        recylcer = view.findViewById(R.id.recyclerConverter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создание адаптера для recycler view
        converterAdapter = ConverterRecyclerAdapter(requireContext(), unitsModelList, specialSymbolsList)

        // Настройка recycler view
        recylcer.apply {
            adapter = converterAdapter
            layoutManager = LinearLayoutManager(context)
        }

        converterAdapter.listener = this

        updateConverterData()
        fragmentIsInflated = true
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        converterAdapter = ConverterRecyclerAdapter(requireContext(), unitsModelList, specialSymbolsList)
        recylcer.adapter = converterAdapter

        converterAdapter.listener = this
    }

    // Устанавливает модель данных конвертера
    fun setNewConverterModel(newConverterDataModel: ConverterDataModel){
        this.converterModel = newConverterDataModel
        if (fragmentIsInflated) updateConverterData()
    }

    private fun updateConverterData(){
        unitsModelList.clear()

        // Обновляем заголовок
        this.title = converterModel.title
        setTitleConverter()

        // Обновляем иконку
        this.iconResource = converterModel.thumbnailResourceId
        setIconConverter()

        unitsNamesList = converterModel.converterUnitsNamesList
        specialSymbolsList = converterModel.convertersSpecialSymbolsList

        for (name in unitsNamesList){
            unitsModelList.add(UnitRecyclerModel(name, userInput.toString(), false))
        }

        // Назначаем набор данных для конвертации
        valuesToConvert = converterModel.convertersValuesToConvert

        // Обновляем списки моделей и спец. символов в адаптере recycler
        converterAdapter.unitsList = unitsModelList
        converterAdapter.specialSymbols = converterModel.convertersSpecialSymbolsList

        converterAdapter.notifyDataSetChanged()

    }

    // Назначаем залоговок из модели
    private fun setTitleConverter(newTitle: String? = null){
        if (newTitle == null)
            converterName.text = title
        else
            converterName.text = newTitle
    }

    // Функция, устанавливающая иконку конвертера
    private fun setIconConverter(){
        // Если изображение есть - устанавливаем его, нет - Скрываем рамку
        if (iconResource != null) {
            iconView.setImageDrawable(ResourcesCompat.getDrawable(resources, iconResource!!, requireContext().theme))
            iconView.visibility = View.VISIBLE
        }
        else iconView.visibility = View.GONE
    }

    // Устанавливаем пользовательский ввод
    fun setUserInput(newUserInput: Double){
        this.userInput = newUserInput
    }

    // Расчитываем результат преобразования
    fun calculate(){
        var result = BigDecimal(userInput.toString())
        if (valuesToConvert.isNotEmpty()){
            for (column in valuesToConvert.indices){
                try {
                    val valueToConvert = valuesToConvert[selectedItem][column]
                    when(converterModel.converterType){
                        ConverterTypes.Any -> {
                            result = BigDecimal(userInput.toString()).multiply(valueToConvert.toBigDecimal(), MathContext.DECIMAL128)
                        }
                        // Особые правила перевода для температуры (мега костыль, придумать способ реализации!!)
                        ConverterTypes.Temperature -> {
                            when(selectedItem){
                                // Цельсий
                                0 -> when(column) {
                                    0 -> userInput.toBigDecimal() // Цельсий
                                    1 -> result = ((userInput * 9/5) + 32).toBigDecimal()// Фаренгейт
                                    2 -> result = (userInput + 273.15).toBigDecimal()// Кельвин
                                    3 -> result = (userInput * 9/5 + 491.67).toBigDecimal() // Ранкин
                                    4 -> result = (userInput * 0.8).toBigDecimal() // Реомюр

                                    else -> result = BigDecimal(userInput.toString()).multiply(valueToConvert.toBigDecimal(), MathContext.DECIMAL128)
                                }
                                // Фаренгейт
                                1 -> {
                                    when(column){
                                        0 -> result = ((userInput - 32) * 5/9).toBigDecimal()
                                        1 -> result = userInput.toBigDecimal()
                                        2 -> result = ((userInput - 32) * 5/9 + 273.15).toBigDecimal()
                                        3 -> result = (userInput + 459.67).toBigDecimal()
                                        4 -> result = ((userInput - 32) * 0.44444).toBigDecimal()
                                    }
                                }
                                // Кельвин
                                2 -> {
                                    when(column){
                                        0 -> result = (userInput - 273.15).toBigDecimal()
                                        1 -> result = ((userInput - 273.15) * 9/5 + 32).toBigDecimal()
                                        2 -> result = userInput.toBigDecimal()
                                        3 -> result = (userInput * 1.8).toBigDecimal()
                                        4 -> result = ((userInput - 273.15) * 0.8).toBigDecimal()
                                    }
                                }
                                // Ранкин
                                3 -> {
                                    when(column){
                                        0 -> result = ((userInput - 491.67) * 5/9).toBigDecimal()
                                        1 -> result = (userInput - 459.67).toBigDecimal()
                                        2 -> result = (userInput * 5/9).toBigDecimal()
                                        3 -> result = userInput.toBigDecimal()
                                        4 -> result = ((userInput - 491.67) * 0.44444).toBigDecimal()
                                    }
                                }
                                // Реомюр
                                4 -> {
                                    when(column){
                                        0 -> result = (userInput / 0.8).toBigDecimal()
                                        1 -> result = (userInput * 2.25 + 32).toBigDecimal()
                                        2 -> result = ((userInput / 0.8) + 273.15).toBigDecimal()
                                        3 -> result = (userInput * 2.25 + 491.67).toBigDecimal()
                                        4 -> result = userInput.toBigDecimal()
                                    }
                                }
                            }

                        }

                    }
                    converterAdapter.updateItem(column,result.toDouble(), column == selectedItem)

                }catch (ex: ArrayIndexOutOfBoundsException){
                    converterAdapter.updateItem(column,-57.23, column == selectedItem)
                }


            }

        }

    }

    override fun onItemClick(position: Int) {
        selectedItem = position
        calculate()
    }

}

