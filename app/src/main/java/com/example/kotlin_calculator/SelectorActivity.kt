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
    fun switchToLength(view : View){
        // Переход на конвертер (LengthActivity)
        startActivity(Intent(this,LengthActivity::class.java))
        finishAfterTransition()
    }
    fun switchToSpeed(view : View){
        // Переход на конвертер (LengthActivity)
        startActivity(Intent(this,SpeedActivity::class.java))
        finishAfterTransition()
    }
    fun switchToData(view : View){
        // Переход на конвертер (LengthActivity)
        startActivity(Intent(this,DataActivity::class.java))
        finishAfterTransition()
    }
    fun switchToTemperature(view : View){
        // Переход на конвертер (TemperatureActivity)
        startActivity(Intent(this,TemperatureActivity::class.java))
        finishAfterTransition()
    }
    fun switchToSquare(view : View){
        // Переход на конвертер (TemperatureActivity)
        startActivity(Intent(this,SquareActivity::class.java))
        finishAfterTransition()
    }
}