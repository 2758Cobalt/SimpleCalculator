package com.example.kotlin_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculatorFragment = CalculatorFragment()
        val convertorsFragment = ConvertersFragment()

        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()

        ft.replace(R.id.fragment_container_calculator, calculatorFragment)
        ft.commit()

        //val container: FrameLayout = findViewById(R.id.fragment_container_calculator)


//        container.setOnTouchListener { _, event ->
//            handleTouch(event, container)
//            true
//        }
    }

    private fun handleTouch(event: MotionEvent, container: FrameLayout) {
        val y = event.rawY.toInt()

        val layoutParams = container.layoutParams as FrameLayout.LayoutParams

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Начало перемещения
                val yDelta = y - layoutParams.topMargin
                container.tag = yDelta
            }
            MotionEvent.ACTION_MOVE -> {
                // Процесс перемещения
                val yDelta = container.tag as? Int ?: 0
                layoutParams.topMargin = y - yDelta
                container.layoutParams = layoutParams
            }
            MotionEvent.ACTION_UP -> {
                // Завершение перемещения
                container.tag = null
            }
        }

        // Обновление визуального представления контейнера
        container.requestLayout()
    }
}
