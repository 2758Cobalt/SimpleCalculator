package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.sqrt

class SquareActivity : AppCompatActivity() {
    private lateinit var constants : Array<String>

    private lateinit var valueForConvert : EditText

    private lateinit var resultConvert : TextView

    private lateinit var dropdownFrom : Spinner
    private lateinit var dropdownIn : Spinner

    private lateinit var bottomNavigationico : BottomNavigationView // Панель "меню"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)

        constants = resources.getStringArray(R.array.square_values)

        dropdownFrom = findViewById(R.id.dropDownFrom)
        dropdownIn = findViewById(R.id.dropDownIn)

        valueForConvert = findViewById(R.id.inputValue)

        resultConvert = findViewById(R.id.resultConvertation)

        bottomNavigationico = findViewById(R.id.navigation) // Панель навигации

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)

        bottomNavigationView.selectedItemId = R.id.bottom_current // Назначение выбраного меню по-умолчанию (калькулятор)

        // Панель навигации | Событие OnItemSelected
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_calculator -> {
                    switchToMain()
                    true
                }
                R.id.bottom_exit -> {
                    switchToSelector()
                    true
                }
                else -> false
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.square_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Установка строкового массива
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Применение адаптера к спинеру
            dropdownFrom.adapter = adapter
            dropdownIn.adapter = adapter
        }

    }
    fun switchToSelector(){
        startActivity(Intent(this,SelectorActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right)
        finishAfterTransition()
    }
    fun switchToMain(){
        startActivity(Intent(this,MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right)
        finishAfterTransition()
    }

    fun resetFields(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        valueForConvert.text.clear()
        resultConvert.text = "0"
    }

    fun calculateConvertation(view : View){
        var result = 0.0
        if(valueForConvert.text.isNotEmpty() ) // Проверяет не пустое ли поле ввода
        {
            val value = valueForConvert.text.toString().toDouble()

            when (dropdownFrom.selectedItem.toString()) {

                // Микроны
                constants[0] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 1) // micron
                    constants[1] -> result = (value / 0.001) // mili
                    constants[2] -> result = (value / 0.0001) // centi
                    constants[3] -> result = (value / 0.00001) // deci
                    constants[4] -> result = (value / 0.000001) // metr
                    constants[5] -> result = (value / 0.000000001) // kilo
                    constants[6] -> result = (value / 0.0000000000000001) // hectare
                }

                // Миллиметры
                constants[1] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 1000) // micron
                    constants[1] -> result = (value / 1) // mili
                    constants[2] -> result = (value / 0.1) // centi
                    constants[3] -> result = (value / 0.01) // deci
                    constants[4] -> result = (value / 0.001) // metr
                    constants[5] -> result = (value / 0.000001) // kilo
                    constants[6] -> result = (value / 0.0000000001) // hectare
                }

                // Сантиметры
                constants[2] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 10000) // micron
                    constants[1] -> result = (value / 10) // mili
                    constants[2] -> result = (value / 1) // centi
                    constants[3] -> result = (value / 0.1) // deci
                    constants[4] -> result = (value / 0.01) // metr
                    constants[5] -> result = (value / 0.00001) // kilo
                    constants[6] -> result = (value / 0.00000001) // hectare
                }

                // Дециметры
                constants[3] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 100000) // micron
                    constants[1] -> result = (value / 100) // mili
                    constants[2] -> result = (value / 10) // centi
                    constants[3] -> result = (value / 1) // deci
                    constants[4] -> result = (value / 0.1) // metr
                    constants[5] -> result = (value / 0.0001) // kilo
                    constants[6] -> result = (value / 0.000001) // hectare
                }

                // Метры
                constants[4] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 1000000) // micron
                    constants[1] -> result = (value / 1000) // mili
                    constants[2] -> result = (value / 100) // centi
                    constants[3] -> result = (value / 10) // deci
                    constants[4] -> result = (value / 1) // metr
                    constants[5] -> result = (value / 0.001) // kilo
                    constants[6] -> result = (value / 0.0001) // hectare
                }

                // Километры
                constants[5] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 1000000000) // micron
                    constants[1] -> result = (value / 1000000) // mili
                    constants[2] -> result = (value / 100000) // centi
                    constants[3] -> result = (value / 10000) // deci
                    constants[4] -> result = (value / 1000) // metr
                    constants[5] -> result = (value / 1) // kilo
                    constants[6] -> result = (value / 100) // hectare
                }

                // Гектары
                constants[6] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 10000000000000000) // micron
                    constants[1] -> result = (value / 10000000000) // mili
                    constants[2] -> result = (value / 100000000) // centi
                    constants[3] -> result = (value / 1000000) // deci
                    constants[4] -> result = (value / 10000) // metr
                    constants[5] -> result = (value / 0.01) // kilo
                    constants[6] -> result = (value / 1) // hectare
                }

            }
            resultConvert.text = "≈" + String.format("%.2f",result)
        }
    }
}