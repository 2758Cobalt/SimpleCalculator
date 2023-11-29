package com.example.kotlin_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView : BottomNavigationView // Панель "меню"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculatorFragment = CalculatorFragment() // Фрагмент Home
        val convertersFragment = ConvertersFragment()// Фрагмент Night

        val fragmentManager = supportFragmentManager

        var fragment = fragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.fragment_container,calculatorFragment)
            ft.commit()

            // Ссылка на bottomNavigation
            bottomNavigationView = findViewById(R.id.fragment_navigation)

            // Назначение выбраного меню по-умолчанию (калькулятор)
            bottomNavigationView.selectedItemId = R.id.bottom_calculator

            // Панель навигации | Событие OnItemSelected
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.bottom_calculator -> {
                        fragmentManager.beginTransaction().replace(R.id.fragment_container,calculatorFragment).commit()
                        true
                    }

                    //Selector
                    R.id.bottom_selector -> {
                        val ft = fragmentManager.beginTransaction()
                        ft.replace(R.id.fragment_container,convertersFragment)
                        ft.commit()
                        true
                    }

                    // FinishApp
                    R.id.bottom_exit -> {
                        Log.v("CalculatorDebug","owner: ${this@MainActivity} Выход из приложения")
                        finish()
                        exitProcess(0)
                        true
                    }
                    else -> false
                }
            }


        }

    }

}