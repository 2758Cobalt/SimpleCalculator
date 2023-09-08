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

class WeightActivity : AppCompatActivity() {
    private lateinit var constants : Array<String>

    private lateinit var valueForConvert : EditText

    private lateinit var resultConvert : TextView

    private lateinit var dropdownFrom : Spinner
    private lateinit var dropdownIn : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)

        constants = resources.getStringArray(R.array.weight_values)

        dropdownFrom = findViewById(R.id.dropDownFrom)
        dropdownIn = findViewById(R.id.dropDownIn)

        valueForConvert = findViewById(R.id.inputValue)

        resultConvert = findViewById(R.id.resultConvertation)



        ArrayAdapter.createFromResource(
            this,
            R.array.weight_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Установка строкового массива
            // Применение адаптера к спинеру
            dropdownFrom.adapter = adapter
            dropdownIn.adapter = adapter
        }
    }
    fun weightToSelector(view : View){
        startActivity(Intent(this,SelectorActivity::class.java))
        finishAfterTransition()
    }
    fun convertValues(view : View){
        val value = valueForConvert.text.toString().toDouble()
        when(dropdownFrom.selectedItem) {
            // Микрограмм
            constants[0] -> when(dropdownIn.selectedItem.toString()){
                    constants[0] -> resultConvert.text = (value / 1).toString() // 1
                    constants[1] -> resultConvert.text = (value / 1000).toString() // 1 : 1000 - ug -> miligram
                    constants[2] -> resultConvert.text = (value / 1000000).toString() // 1 : 1e-6 (0,000 001) -> gram
                    constants[3] -> resultConvert.text = (value / 1000000000).toString() // 1 : 1e-9 (0, 000 000 001) -> kilogram
                    constants[4] -> resultConvert.text = (value / 1000000000000).toString() // 1 : 1e-12 (0, 000 000 000 001) - tonna
                    constants[5] -> resultConvert.text = (value / 1000000000000000).toString() // 1 : 1e-15 (0, 000 000 000 001) - megatonna
                    constants[6] -> resultConvert.text = (value / 1000000000000000000).toString() // 1 : 1e-18 (0, 000 000 000 001) - kilotonna
                }

            // Миллиграмм
            constants[1] ->when(dropdownIn.selectedItem.toString()){
                    constants[0] -> resultConvert.text = (value / 0.001).toString() // 1 : 0.001
                    constants[1] -> resultConvert.text = (value / 1).toString() // 1 : 1
                    constants[2] -> resultConvert.text = (value / 1000).toString() // 1 : 1e-3
                    constants[3] -> resultConvert.text = (value / 1000000).toString() // 1 : 1e-6
                    constants[4] -> resultConvert.text = (value / 1000000000).toString() // 1 : 1e-9
                    constants[5] -> resultConvert.text = (value / 1000000000000).toString() // 1 : 1e-12
                    constants[6] -> resultConvert.text = (value / 1000000000000000).toString() // 1 : 1e-15
                }

            // Грамм
            constants[2] ->when(dropdownIn.selectedItem.toString()){
                    constants[0] -> resultConvert.text = (value / 0.000001).toString() // 1 : 1e+6
                    constants[1] -> resultConvert.text = (value / 0.001).toString() // 1 : 1e+3
                    constants[2] -> resultConvert.text = (value / 1).toString() // 1 : 1
                    constants[3] -> resultConvert.text = (value / 1000).toString() // 1 : 1e-3
                    constants[4] -> resultConvert.text = (value / 1000000).toString() // 1 : 1e-6
                    constants[5] -> resultConvert.text = (value / 1000000000).toString() // 1 : 1e-9
                    constants[6] -> resultConvert.text = (value / 1000000000000).toString() // 1 : 1e-12
            }

            // Килограмм
            constants[3] ->when(dropdownIn.selectedItem.toString()){
                    constants[0] -> resultConvert.text = (value / 0.000000001).toString() // 1 : 1e+9
                    constants[1] -> resultConvert.text = (value / 0.000001).toString() // 1 : 1e+6
                    constants[2] -> resultConvert.text = (value / 0.001).toString() // 1 : 1e+3
                    constants[3] -> resultConvert.text = (value / 1).toString() // 1 : 1
                    constants[4] -> resultConvert.text = (value / 1000).toString() // 1 : 1e-3
                    constants[5] -> resultConvert.text = (value / 1000000).toString() // 1 : 1e-6
                    constants[6] -> resultConvert.text = (value / 1000000000).toString() // 1 : 1e-9
            }
            // Тонна
            constants[4] ->when(dropdownIn.selectedItem.toString()){
                    constants[0] -> resultConvert.text = (value / 0.000000000001).toString() // 1 : 1e+12
                    constants[1] -> resultConvert.text = (value / 0.000000001).toString() // 1 : 1e+9
                    constants[2] -> resultConvert.text = (value / 0.000001).toString() // 1 : 1e+6
                    constants[3] -> resultConvert.text = (value / 0.001).toString() // 1 : 1e+3
                    constants[4] -> resultConvert.text = (value / 1).toString() // 1 : 1
                    constants[5] -> resultConvert.text = (value / 1000).toString() // 1 : 1e-3
                    constants[6] -> resultConvert.text = (value / 1000000).toString() // 1 : 1e-6
            }

            // Мегатонна
            constants[5] ->when(dropdownIn.selectedItem.toString()){
                    constants[0] -> resultConvert.text = (value / 0.000000000000001).toString() // 1 : 1e+15
                    constants[1] -> resultConvert.text = (value / 0.000000000001).toString() // 1 : 1e+12
                    constants[2] -> resultConvert.text = (value / 0.000000001).toString() // 1 : 1e+9
                    constants[3] -> resultConvert.text = (value / 0.000001).toString() // 1 : 1e+6
                    constants[4] -> resultConvert.text = (value / 0.001).toString() // 1 : 1e+3
                    constants[5] -> resultConvert.text = (value / 1).toString() // 1 : 1
                    constants[6] -> resultConvert.text = (value / 1000).toString() // 1 : 1e-3
            }

            // Килотона
            constants[6] ->when(dropdownIn.selectedItem.toString()){
                constants[0] -> resultConvert.text = (value / 0.000000000000000001).toString() // 1 : 1e+18
                constants[1] -> resultConvert.text = (value / 0.000000000000001).toString() // 1 : 1e+15
                constants[2] -> resultConvert.text = (value / 0.000000000001).toString() // 1 : 1e+12
                constants[3] -> resultConvert.text = (value / 0.000000001).toString() // 1 : 1e+9
                constants[4] -> resultConvert.text = (value / 0.000001).toString() // 1 : 1e+6
                constants[5] -> resultConvert.text = (value / 0.001).toString() // 1 : 1e+3
                constants[6] -> resultConvert.text = (value / 1).toString() // 1 : 11
            }

        }

    }
    fun resetFields(view : View){
        resultConvert.text = ""
        valueForConvert.setText("0")
    }
}