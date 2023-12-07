package com.example.kotlin_calculator

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity


class MainActivity: AppCompatActivity() {
    private lateinit var calculatorMenu: Button                         // Кнопка меню
    private lateinit var calculatorContainer : FrameLayout
    private lateinit var selectorContainer : FrameLayout
    private var selectorMode : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        calculatorMenu = findViewById(R.id.calculatorMainMenu)         // Ссылка на кнопку "trigger"

        calculatorContainer = findViewById(R.id.fragment_container_calculator)
        selectorContainer = findViewById(R.id.fragment_container_selector)

        val ft = supportFragmentManager.beginTransaction()

        ft.replace(R.id.fragment_container_calculator, CalculatorFragment())
        ft.replace(R.id.fragment_container_selector, SelectorFragment())
        ft.commit()


        calculatorMenu.setOnClickListener { selectorSwitch() }
    }

    private fun selectorSwitch() {
        selectorMode = !selectorMode
        val slideDown: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down)
        val slideUp: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)
        val animationListener = object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                if (!selectorMode)
                    calculatorContainer.visibility = View.VISIBLE
                else
                    selectorContainer.visibility = View.VISIBLE
            }
            override fun onAnimationEnd(animation: Animation) {
                if (selectorMode)
                    calculatorContainer.visibility = View.INVISIBLE
                else
                    selectorContainer.visibility = View.INVISIBLE
            }
            override fun onAnimationRepeat(p0: Animation?) {
            }
        }

        if(selectorMode)
        {
            calculatorMenu.text = "Calculator"

            slideDown.setAnimationListener(animationListener)
            calculatorContainer.startAnimation(slideDown)
        }
        else{
            calculatorMenu.text = "Menu"

            slideUp.setAnimationListener(animationListener)
            calculatorContainer.startAnimation(slideUp)
        }


    }
    fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}