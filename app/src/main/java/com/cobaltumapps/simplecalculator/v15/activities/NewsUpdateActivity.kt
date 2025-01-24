package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityNewsUpdateBinding

/** Активность, отображающая информацию обновления для приложения */
class NewsUpdateActivity : AppCompatActivity() {
    private val binding by lazy { ActivityNewsUpdateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.updatesToolbar)
    }
}