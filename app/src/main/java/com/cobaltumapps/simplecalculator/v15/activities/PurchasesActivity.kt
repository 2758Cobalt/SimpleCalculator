package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityPurchasesBinding
import com.cobaltumapps.simplecalculator.v15.google.billing.BillingClientManager_d


class PurchasesActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPurchasesBinding.inflate(layoutInflater) }
    
    private val billingClientManagerD by lazy { BillingClientManager_d(this@PurchasesActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}