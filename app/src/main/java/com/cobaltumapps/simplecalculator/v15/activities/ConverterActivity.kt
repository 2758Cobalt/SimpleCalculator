package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityConverterNBinding

class ConverterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityConverterNBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}