package com.cobaltumapps.simplecalculator.ui.activities.extra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityPurchasesBinding


class PurchasesActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPurchasesBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}