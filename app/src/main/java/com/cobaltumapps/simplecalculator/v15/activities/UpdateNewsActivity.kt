package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityUpdateNewsBinding

/** Активность, отображающая информацию обновления для приложения */
class UpdateNewsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUpdateNewsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.updatesToolbar)
    }
}