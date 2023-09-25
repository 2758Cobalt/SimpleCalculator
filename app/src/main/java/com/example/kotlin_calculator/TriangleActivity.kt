package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.sqrt

class TriangleActivity : AppCompatActivity() {
    private lateinit var triangle_sideA : EditText
    private lateinit var triangle_sideB : EditText
    private lateinit var triangle_sideC : EditText

    private lateinit var triangleResult : TextView

    private lateinit var bottomNavigationico : BottomNavigationView // Панель "меню"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triangle)

        triangleResult = findViewById(R.id.areaTriangleResult)

        // Треугольник
        triangle_sideA = findViewById(R.id.inputTriangleSideA)
        triangle_sideB = findViewById(R.id.inputTriangleSideB)
        triangle_sideC = findViewById(R.id.inputTriangleSideC)

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
    fun resetFieldsTriangle(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        triangle_sideA.text.clear()
        triangle_sideB.text.clear()
        triangle_sideC.text.clear()
        triangleResult.text = "0"
    }

    fun calculateTriangle(view : View){

        if(triangle_sideA.text.isNotEmpty() && triangle_sideB.text.isNotEmpty() && triangle_sideC.text.isNotEmpty())
        {
            val sideA = triangle_sideA.text.toString().toDouble()
            val sideB = triangle_sideB.text.toString().toDouble()
            val sideC = triangle_sideC.text.toString().toDouble()

            val perimeter = (sideA + sideB + sideC) / 2

            triangleResult.text = "≈" + String.format("%.2f",
                sqrt((perimeter) * (perimeter - sideA) * (perimeter - sideB) * (perimeter - sideC))
            )
        }

    }
}