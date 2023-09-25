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

class TemperatureActivity : AppCompatActivity() {

    private lateinit var constants : Array<String>

    private lateinit var valueForConvert : EditText

    private lateinit var resultConvert : TextView

    private lateinit var dropdownFrom : Spinner
    private lateinit var dropdownIn : Spinner

    private lateinit var bottomNavigationico : BottomNavigationView // Панель "меню"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)

        constants = resources.getStringArray(R.array.temperature_values)

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
            R.array.temperature_values,
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
        resultConvert.text ="0"
        valueForConvert.setText("0")
    }
    fun calculateConvertation(view : View){
        // Расчёт данных
        if(valueForConvert.text.isNotEmpty()) // Проверяет не пустое ли поле ввода
        {
            val value = valueForConvert.text.toString().toDouble()
            var result = 0.0

            when(dropdownFrom.selectedItem.toString()) {

                // Цельсий
                constants[0] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = value // C
                    constants[1] -> result = ((value * 9 / 5) + 32) // F
                    constants[2] -> result = (value + 273.15) // K
                    constants[3] -> result = (value + 273.15) * 1.8 // R
                    constants[4] -> result = (value * 4 / 5) // Re

                }

                // Фаренгейт
                constants[1] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = ((value - 32) * 5 / 9) // C
                    constants[1] -> result = value // F
                    constants[2] -> result = (value + 459.67) * 5 / 9 // K
                    constants[3] -> result = (value + 459.67) // R
                    constants[4] -> result = (value - 32) * 4 / 9 // Re

                }

                // Кельвин
                constants[2] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value - 273.15) // C
                    constants[1] -> result = (value - 273.15) * 9 / 5 + 32 // F
                    constants[2] -> result = value // K
                    constants[3] -> result = (value * 9 / 5) // R
                    constants[4] -> result = (value - 273.15) * 4 / 5 // Re

                }

                // Ранкин
                constants[3] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = ((value - 491.67) * 5 / 9) // C
                    constants[1] -> result = (value - 459.67) // F
                    constants[2] -> result = (value *  5 / 9) // K
                    constants[3] -> result = value // R
                    constants[4] -> result = (value - 491.67) * 4 / 9 // Re

                }

                // Реомюр
                constants[4] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 5 / 4) // C
                    constants[1] -> result = (value * 9 / 4) + 32 // F
                    constants[2] -> result = (value * 5 / 4) + 273.15 // K
                    constants[3] -> result = (value * 9 / 4 ) + 491.67 // R
                    constants[4] -> result = value // Re

                }

            }

            resultConvert.text = "≈" + String.format("%.2f",result)
        }

    }


}