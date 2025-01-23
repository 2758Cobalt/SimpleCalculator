package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityFeaturesBinding

class FeaturesActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFeaturesBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}