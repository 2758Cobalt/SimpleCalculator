package com.example.kotlin_calculator.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlin_calculator.fragments.CalculatorFragment
import com.example.kotlin_calculator.R
import com.example.kotlin_calculator.fragments.SelectorFragment
import com.example.kotlin_calculator.adapters.ViewPagerAdapter
import kotlin.system.exitProcess


class MainActivity: AppCompatActivity() {
    private lateinit var viewpager: ViewPager2
    private var fragList = listOf(                    // Список фрагментов, которые создаются в начале
        CalculatorFragment(),
        SelectorFragment())

    private var adapter = ViewPagerAdapter(this, fragList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(null) // Отключение перерисовки заднего фона цветом темы

        viewpager = findViewById(R.id.placeHolder)
        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = 1 // Предзагрузка фрагмента наперёд



    }
    fun replaceFragmentInViewPager(pos: Int, newFragment: Fragment){
        adapter.replace(pos,newFragment) // Вызов метода замены
        viewpager.adapter = adapter // Переназначение адаптера
        viewpager.post {
            viewpager.setCurrentItem(1, false) // Установка позиции
        }

    }
}