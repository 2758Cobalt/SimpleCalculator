package com.example.kotlin_calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val calculatorFragment = CalculatorFragment()

        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()

        ft.replace(R.id.fragment_container_calculator, calculatorFragment)
        ft.commit()
    }
}