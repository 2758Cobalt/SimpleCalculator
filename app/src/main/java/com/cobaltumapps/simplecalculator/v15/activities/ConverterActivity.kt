package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityConverterNBinding
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterSelectorFragment

class ConverterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityConverterNBinding.inflate(layoutInflater) }
    private val converterSelector by lazy { ConverterSelectorFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}