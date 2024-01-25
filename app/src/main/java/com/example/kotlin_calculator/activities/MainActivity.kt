package com.example.kotlin_calculator.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_calculator.fragments.CalculatorFragment
import com.example.kotlin_calculator.R


class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(null) // Отключение перерисовки заднего фона цветом темы

        supportFragmentManager.beginTransaction().replace(R.id.placeHolder,CalculatorFragment()).commit()


    }

//    fun replaceFragmentInViewPager(pos: Int, newFragment: Fragment){
//        adapter.replace(pos,newFragment) // Вызов метода замены
//        viewpager.adapter = adapter // Переназначение адаптера
//        viewpager.post {
//            viewpager.setCurrentItem(1, false) // Установка позиции
//        }
//
//    }
}