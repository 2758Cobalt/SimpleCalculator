package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.pow
import kotlin.math.sin

class RhombusActivity : AppCompatActivity() {
    private lateinit var constants : Array<String>

    private lateinit var rhombusSides : EditText
    private lateinit var rhombusAngle : EditText
    private lateinit var rhombusHeight : EditText
    private lateinit var rhombusDiagonalFirst : EditText
    private lateinit var rhombusDiagonalSecond : EditText

    private lateinit var dropDown : Spinner

    private lateinit var rhombusResult : TextView

    private lateinit var bottomNavigationico : BottomNavigationView // Панель "меню"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rhombus)

        constants = resources.getStringArray(R.array.rhombus_formulas) // Получение строкового массива

        rhombusResult = findViewById(R.id.areaRhombusResult) // Поле результат
        dropDown = findViewById(R.id.dropDownFormula) // Формулы

        // Стороны ромба и прочее
        rhombusSides = findViewById(R.id.inputRhombusSideA)
        rhombusAngle = findViewById(R.id.inputRhombusAngle)
        rhombusHeight = findViewById(R.id.inputRhombusHeight)
        
        rhombusDiagonalFirst = findViewById(R.id.inputRhombusDiagonalFirst)
        rhombusDiagonalSecond = findViewById(R.id.inputRhombusDiagonalSecond)

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
            R.array.rhombus_formulas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Установка строкового массива
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Применение адаптера к спинеру
            dropDown.adapter = adapter
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
    fun resetFieldsRhombus(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        rhombusSides.text.clear()
        rhombusHeight.text.clear()
        rhombusAngle.text.clear()

        rhombusDiagonalFirst.text.clear()
        rhombusDiagonalSecond.text.clear()

        rhombusResult.text = "0"
    }

    fun calculateRhombus(view : View){
        var result = 0.0



        when (dropDown.selectedItem.toString()) {
            constants[0] ->
                if(rhombusSides.text.isNotEmpty() && rhombusHeight.text.isNotEmpty()) {
                    val sideA = rhombusSides.text.toString().toDouble()
                    val height = rhombusHeight.text.toString().toDouble()

                    result = sideA * height
            }
            constants[1] -> if(rhombusSides.text.isNotEmpty() && rhombusAngle.text.isNotEmpty()) {
                val sideA = rhombusSides.text.toString().toDouble()
                val sinus = rhombusAngle.text.toString().toDouble()

                result = sideA.pow(2.0) * sin(sinus)
            }

            constants[2] ->
                if(rhombusDiagonalFirst.text.isNotEmpty() && rhombusDiagonalSecond.text.isNotEmpty()) {
                    val diagonalFirst = rhombusDiagonalFirst.text.toString().toDouble()
                    val diagonalSecond = rhombusDiagonalSecond.text.toString().toDouble()

                    result = (diagonalFirst * diagonalSecond) / 2
                }
        }

        rhombusResult.text = " S = ${result}"


    }
}