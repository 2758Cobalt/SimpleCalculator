package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlin.math.sqrt

class TriangleActivity : AppCompatActivity() {
    private lateinit var triangle_sideA : EditText
    private lateinit var triangle_sideB : EditText
    private lateinit var triangle_sideC : EditText

    private lateinit var triangleResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triangle)

        triangleResult = findViewById(R.id.areaTriangleResult)

        // Треугольник
        triangle_sideA = findViewById(R.id.inputTriangleSideA)
        triangle_sideB = findViewById(R.id.inputTriangleSideB)
        triangle_sideC = findViewById(R.id.inputTriangleSideC)
    }

    fun switchToSelector(view : View){
        startActivity(Intent(this,SelectorActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right)
        finishAfterTransition()
    }
    fun resetFieldsTriangle(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        triangle_sideA.text.clear()
        triangle_sideB.text.clear()
        triangle_sideC.text.clear()
        triangleResult.text = getString(R.string.tooltip_triangleText)
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