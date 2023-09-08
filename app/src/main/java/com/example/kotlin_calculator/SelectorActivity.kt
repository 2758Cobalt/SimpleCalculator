package com.example.kotlin_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SelectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)
    }
    fun switchToMain(view : View){
        // Возврат на главную страницу (MainActivity)
        startActivity(Intent(this,MainActivity::class.java))
        finishAfterTransition()
    }
    fun switchToWeight(view : View){
        // Переход на конвертер (WeightActivity)
        startActivity(Intent(this,WeightActivity::class.java))
        finishAfterTransition()
    }
}