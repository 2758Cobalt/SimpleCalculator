package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityFeaturesBinding
import com.cobaltumapps.simplecalculator.v15.google.billing.BillingClientManager
import com.cobaltumapps.simplecalculator.v15.google.billing.BillingConnectionManager


class FeaturesActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFeaturesBinding.inflate(layoutInflater) }
    
    private val billingClientManager by lazy { BillingClientManager(this@FeaturesActivity) }
    private val billingConnectionManager by lazy { BillingConnectionManager(billingClientManager.getBillingClient()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}