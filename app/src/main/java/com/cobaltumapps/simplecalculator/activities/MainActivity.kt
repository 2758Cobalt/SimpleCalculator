package com.cobaltumapps.simplecalculator.activities

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.fragments.CalculatorFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.Locale


class MainActivity: AppCompatActivity() {

    private lateinit var mAdView : AdView
    private lateinit var adRequest : AdRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(null) // Отключение перерисовки заднего фона цветом темы

        val fragManager = supportFragmentManager
        fragManager.beginTransaction().replace(R.id.placeHolder, CalculatorFragment()).commit()





    }

    override fun onResume() {
        super.onResume()
        adRequest = AdRequest.Builder().build()
        MobileAds.initialize(this){
            mAdView = findViewById(R.id.adViewBanner)
            mAdView.loadAd(adRequest)
        }
    }
}