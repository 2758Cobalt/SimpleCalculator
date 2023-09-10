package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import kotlin.math.pow

class DataActivity : AppCompatActivity() {

    private lateinit var constants : Array<String>

    private lateinit var valueForConvert : EditText

    private lateinit var resultConvert : TextView

    private lateinit var dropdownFrom : Spinner
    private lateinit var dropdownIn : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        constants = resources.getStringArray(R.array.data_values)

        dropdownFrom = findViewById(R.id.dropDownFrom)
        dropdownIn = findViewById(R.id.dropDownIn)

        valueForConvert = findViewById(R.id.inputValue)

        resultConvert = findViewById(R.id.resultConvertation)

        ArrayAdapter.createFromResource(
            this,
            R.array.data_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Установка строкового массива
            // Применение адаптера к спинеру
            dropdownFrom.adapter = adapter
            dropdownIn.adapter = adapter
        }
    }

    fun switchToSelector(view: View) {
        // Возврат на страницу селектора
        startActivity(Intent(this, SelectorActivity::class.java))
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
            when (dropdownFrom.selectedItem.toString()) {

                // Байт
                constants[0] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1) // b
                    constants[1] -> result = (value * 0.001) // kb
                    constants[2] -> result = (value * 0.000001) // mb
                    constants[3] -> result = (value * 0.000000001) // gb
                    constants[4] -> result = (value * 0.000000000001) // tb
                    constants[5] -> result = (value * 0.000000000000001) // pb
                    constants[6] -> result = (value * 0.000000000000000001) // eb
                }

                // КилоБайт
                constants[1] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1000) // b
                    constants[1] -> result = (value * 1) // kb
                    constants[2] -> result = (value * 0.001) // mb
                    constants[3] -> result = (value * 0.000001) // gb
                    constants[4] -> result = (value * 0.000000001) // tb
                    constants[5] -> result = (value * 0.000000000001) // pb
                    constants[6] -> result = (value * 0.000000000000001) // eb
                }

                // МегаБайт
                constants[2] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1000000) // b
                    constants[1] -> result = (value * 1000) // kb
                    constants[2] -> result = (value * 1) // mb
                    constants[3] -> result = (value * 0.001) // gb
                    constants[4] -> result = (value * 0.000001) // tb
                    constants[5] -> result = (value * 0.000000001) // pb
                    constants[6] -> result = (value * 0.000000000001) // eb
                }

                // ГигаБайт
                constants[3] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1000000000) // b
                    constants[1] -> result = (value * 1000000) // kb
                    constants[2] -> result = (value * 1000) // mb
                    constants[3] -> result = (value * 1) // gb
                    constants[4] -> result = (value * 0.001) // tb
                    constants[5] -> result = (value * 0.000001) // pb
                    constants[6] -> result = (value * 0.000000001) // eb
                }

                // ТераБайт
                constants[4] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1000000000000) // b
                    constants[1] -> result = (value * 1000000000) // kb
                    constants[2] -> result = (value * 1000000) // mb
                    constants[3] -> result = (value * 1000) // gb
                    constants[4] -> result = (value * 1) // tb
                    constants[5] -> result = (value * 0.001) // pb
                    constants[6] -> result = (value * 0.000001) // eb
                }

                // ПетаБайт
                constants[5] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1000000000000000) // b
                    constants[1] -> result = (value * 1000000000000) // kb
                    constants[2] -> result = (value * 1000000000) // mb
                    constants[3] -> result = (value * 1000000) // gb
                    constants[4] -> result = (value * 1000) // tb
                    constants[5] -> result = (value * 1) // pb
                    constants[6] -> result = (value * 0.001) // eb
                }

                // ЭксаБайт
                constants[5] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1000000000000000000) // b
                    constants[1] -> result = (value * 1000000000000000) // kb
                    constants[2] -> result = (value * 1000000000000) // mb
                    constants[3] -> result = (value * 1000000000) // gb
                    constants[4] -> result = (value * 1000000) // tb
                    constants[5] -> result = (value * 1000) // pb
                    constants[6] -> result = (value * 1) // eb
                }

            }
            resultConvert.text = "≈" + result.toString()
        }
    }
}