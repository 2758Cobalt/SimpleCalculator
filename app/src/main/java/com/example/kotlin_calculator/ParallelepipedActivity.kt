package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ParallelepipedActivity : AppCompatActivity() {


    private lateinit var squareSideA : EditText
    private lateinit var squareSideB : EditText
    private lateinit var squareSideC : EditText

    private lateinit var squareResult : TextView

    private lateinit var bottomNavigationico : BottomNavigationView // Панель "меню"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallelepiped)

        squareSideA = findViewById(R.id.inputParallelepipedSideA)
        squareSideB = findViewById(R.id.inputParallelepipedSideB)
        squareSideC = findViewById(R.id.inputParallelepipedSideC)

        squareResult = findViewById(R.id.areaParallelepipedResult)

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
    fun resetFields(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        squareSideA.text.clear()
        squareSideB.text.clear()
        squareSideC.text.clear()
        squareResult.text = "0"
    }
    fun calculateParallelepiped(view : View) {
        var result : Double
        if (squareSideA.text.isNotEmpty() && squareSideB.text.isNotEmpty()) {
            if (squareSideC.text.isEmpty() || squareSideC.text.toString() == "0")
                squareSideC.setText("1")

            val sideA = squareSideA.text.toString().toDouble()
            val sideB = squareSideB.text.toString().toDouble()
            val sideC = squareSideC.text.toString().toDouble()

            Log.i("InfoApp","worked")


            result = 2 * ((sideA * sideB) + (sideA * sideC) + (sideB * sideC)) // 2 (ab + ac + bc)
            squareResult.text = "S = " + String.format("%.2f", result)

        }
    }
}