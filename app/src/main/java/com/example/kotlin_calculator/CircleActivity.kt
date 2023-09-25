package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.PI
import kotlin.math.pow

class CircleActivity : AppCompatActivity() {
    private lateinit var circleRadius: EditText
    private lateinit var circleLength: EditText

    private lateinit var circleResult: TextView
    private lateinit var circleLengthResult: TextView

    private lateinit var bottomNavigationico : BottomNavigationView // Панель "меню"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle)

        circleRadius = findViewById(R.id.inputCircleRadius)
        circleLength = findViewById(R.id.inputCircleLength)

        circleResult = findViewById(R.id.areaCircleResult)
        circleLengthResult = findViewById(R.id.areaCircleLengthResult)

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

    fun resetFieldsCircle(view: View) {
        // Сбрасывает значения в полях - Кнопка "Reset"
        circleRadius.text.clear()
        circleResult.text = "0"
    }

    fun calculateCircle(view: View) {
        var result : Double
        if (circleRadius.text.isNotEmpty()) {
            val radius = circleRadius.text.toString().toDouble()
            result = PI * radius.pow(2.0)

            circleResult.text = "S ≈ " + String.format("%.3f", result)
        }
    }

    fun resetFieldsCircleLength(view: View) {
        // Сбрасывает значения в полях - Кнопка "Reset"
        circleLength.text.clear()
        circleLengthResult.text = "0"
    }

    fun calculateCircleLength(view: View) {
        var result: Double
        if (circleLength.text.isNotEmpty()) {
            val radius = circleLength.text.toString().toDouble()
            result = 2 * PI * radius

            circleLengthResult.text = "S ≈ " + String.format("%.3f", result)

        }
    }
}