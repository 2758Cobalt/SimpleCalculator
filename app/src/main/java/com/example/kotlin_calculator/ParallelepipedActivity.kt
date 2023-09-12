package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class ParallelepipedActivity : AppCompatActivity() {

    private lateinit var constants : Array<String>

    private lateinit var square_sideA : EditText
    private lateinit var square_sideB : EditText
    private lateinit var square_sideC : EditText

    private lateinit var squareResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallelepiped)
    }
    fun switchToSelector(view : View){
        startActivity(Intent(this,SelectorActivity::class.java))
        finishAfterTransition()
    }
    fun resetFields(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        square_sideA.text.clear()
        square_sideB.text.clear()
        square_sideC.text.clear()
        squareResult.text = getString(R.string.tooltip_parallelepipedText)
    }
    fun calculateParallelepiped(view : View) {
        var result = 0.0
        if (square_sideA.text.isNotEmpty() && square_sideB.text.isNotEmpty()) {
            if (square_sideC.text.isEmpty() || square_sideC.text.toString() == "0")
                square_sideC.setText("1")

            val sideA = square_sideA.text.toString().toDouble()
            val sideB = square_sideB.text.toString().toDouble()
            val sideC = square_sideC.text.toString().toDouble()


            result = 2 * ((sideA * sideB) + (sideA * sideC) + (sideB * sideC)) // 2 (ab + ac + bc)
            squareResult.text = "S = " + String.format("%.2f", result)

        }
    }
}