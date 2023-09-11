package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlin.math.sqrt

class SquareActivity : AppCompatActivity() {
    private lateinit var constants : Array<String>

    private lateinit var square_sideA : EditText
    private lateinit var square_sideB : EditText
    private lateinit var square_sideC : EditText

    private lateinit var triangle_sideA : EditText
    private lateinit var triangle_sideB : EditText
    private lateinit var triangle_sideC : EditText

    private lateinit var squareResult : TextView
    private lateinit var triangleResult : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)

        constants = resources.getStringArray(R.array.triangle_formules)


        squareResult = findViewById(R.id.areaSquareResult)
        triangleResult = findViewById(R.id.areaTriangleResult)

        // Параллелепипед
        square_sideA = findViewById(R.id.inputSideA)
        square_sideB = findViewById(R.id.inputSideB)
        square_sideC = findViewById(R.id.inputSideC)

        // Треугольник
        triangle_sideA = findViewById(R.id.inputTriangleSideA)
        triangle_sideB = findViewById(R.id.inputTriangleSideB)
        triangle_sideC = findViewById(R.id.inputTriangleSideC)

    }
    fun switchToSelector(view : View){
        startActivity(Intent(this,SelectorActivity::class.java))
        finishAfterTransition()
    }
    fun resetFieldsParallelepiped(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        square_sideA.text.clear()
        square_sideB.text.clear()
        square_sideC.text.clear()
        squareResult.text = getString(R.string.tooltip_parallelepipedText)
    }

    fun resetFieldsTriangle(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        triangle_sideA.text.clear()
        triangle_sideB.text.clear()
        triangle_sideC.text.clear()
        triangleResult.text = getString(R.string.tooltip_triangleText)
    }
    fun calculateSquare(view : View){
        var result = 0.0
        if(square_sideA.text.isNotEmpty() && square_sideB.text.isNotEmpty())
        {
            if(square_sideC.text.isEmpty() || square_sideC.text.toString() == "0")
                square_sideC.setText("1")

            val sideA = square_sideA.text.toString().toDouble()
            val sideB = square_sideB.text.toString().toDouble()
            val sideC = square_sideC.text.toString().toDouble()


            result = 2 * ( (sideA * sideB) + (sideA * sideC) + (sideB * sideC)) // 2 (ab + ac + bc)
            squareResult.text = "S = " + String.format("%.2f",result)

        }

    }

    fun calculateTriangle(view : View){

        if(triangle_sideA.text.isNotEmpty() && triangle_sideB.text.isNotEmpty() && triangle_sideC.text.isNotEmpty())
        {
            val sideA = triangle_sideA.text.toString().toDouble()
            val sideB = triangle_sideB.text.toString().toDouble()
            val sideC = triangle_sideC.text.toString().toDouble()

            val perimeter = (sideA + sideB + sideC) / 2

            triangleResult.text = "≈" + String.format("%.2f",sqrt((perimeter) * (perimeter - sideA) * (perimeter - sideB) * (perimeter - sideC)))
        }

    }
}