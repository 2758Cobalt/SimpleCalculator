package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sin

class RhombusActivity : AppCompatActivity() {
    private lateinit var constants : Array<String>

    private lateinit var rhombus_sideA : EditText
    private lateinit var rhombus_angle : EditText
    private lateinit var rhombus_height : EditText
    private lateinit var rhombus_diagonalFirst : EditText
    private lateinit var rhombus_diagonalSecond : EditText

    private lateinit var dropDown : Spinner



    private lateinit var rhombusResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rhombus)

        constants = resources.getStringArray(R.array.rhombus_formulas) // Получение строкового массива

        rhombusResult = findViewById(R.id.areaRhombusResult)
        dropDown = findViewById(R.id.dropDownFormula)

        // Треугольник
        rhombus_sideA = findViewById(R.id.inputRhombusSideA)
        rhombus_angle = findViewById(R.id.inputRhombusAngle)
        rhombus_height = findViewById(R.id.inputRhombusHeight)
        
        rhombus_diagonalFirst = findViewById(R.id.inputRhombusDiagonalFirst)
        rhombus_diagonalSecond = findViewById(R.id.inputRhombusDiagonalSecond)

        ArrayAdapter.createFromResource(
            this,
            R.array.rhombus_formulas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Установка строкового массива
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Применение адаптера к спинеру
            dropDown.adapter = adapter
        }
    }

    fun switchToSelector(view : View){
        startActivity(Intent(this,SelectorActivity::class.java))
        finishAfterTransition()
    }
    fun resetFieldsRhombus(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        rhombus_sideA.text.clear()
        rhombus_height.text.clear()
        rhombus_angle.text.clear()

        rhombus_diagonalFirst.text.clear()
        rhombus_diagonalSecond.text.clear()

        rhombusResult.text = "Your text"//getString(R.string.tooltip_triangleText)
    }

    fun calculateRhombus(view : View){
        var result = 0.0



        when (dropDown.selectedItem.toString()) {
            constants[0] ->
                if(rhombus_sideA.text.isNotEmpty() && rhombus_height.text.isNotEmpty()) {
                    val sideA = rhombus_sideA.text.toString().toDouble()
                    val height = rhombus_height.text.toString().toDouble()

                    result = sideA * height
            }
            constants[1] -> if(rhombus_sideA.text.isNotEmpty() && rhombus_angle.text.isNotEmpty()) {
                val sideA = rhombus_sideA.text.toString().toDouble()
                val sinus = rhombus_angle.text.toString().toDouble()

                result = sideA.pow(2.0) * sin(sinus)
            }

            constants[2] ->
                if(rhombus_diagonalFirst.text.isNotEmpty() && rhombus_diagonalSecond.text.isNotEmpty()) {
                    val diagonalFirst = rhombus_diagonalFirst.text.toString().toDouble()
                    val diagonalSecond = rhombus_diagonalSecond.text.toString().toDouble()

                    result = (diagonalFirst * diagonalSecond) / 2
                }
        }

        rhombusResult.text = " S = ${result}"


    }
}