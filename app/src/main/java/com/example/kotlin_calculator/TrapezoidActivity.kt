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

class TrapezoidActivity : AppCompatActivity() {

    private lateinit var trapezoidSideA: EditText
    private lateinit var trapezoidSideB: EditText
    private lateinit var trapezoidHeight: EditText

    private lateinit var trapezoidResult: TextView

    private lateinit var bottomNavigationico : BottomNavigationView // Панель "меню"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trapezoid)

        trapezoidSideA = findViewById(R.id.inputTrapeziodSideA)
        trapezoidSideB = findViewById(R.id.inputTrapeziodSideB)
        trapezoidHeight = findViewById(R.id.inputTrapeziodHeight)

        trapezoidResult = findViewById(R.id.areaTrapezoidResult)

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

    fun resetFieldsTrapezoid(view: View) {
        // Сбрасывает значения в полях - Кнопка "Reset"
        trapezoidSideA.text.clear()
        trapezoidResult.text = "0"
    }

    fun calculateTrapezoid(view: View) {
        var result : Double
        if (trapezoidSideA.text.isNotEmpty()) {
            val sideA = trapezoidSideA.text.toString().toDouble()
            val sideB = trapezoidSideB.text.toString().toDouble()
            val height = trapezoidHeight.text.toString().toDouble()

            result = ((sideA + sideB) * height) / 2


            trapezoidResult.text = "S ≈ " + String.format("%.3f", result)
        }
    }

}