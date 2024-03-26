package com.cobaltumapps.simplecalculator.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.fragments.CalculatorFragment
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

private const val KEYSTORE_GETSTARTED = "getStarted_key"

class MainActivity: AppCompatActivity() {

    private lateinit var mAdView : AdView
    private lateinit var adRequest : AdRequest
    private lateinit var sharedPreferences: SharedPreferences

    private val calculator: CalculatorFragment = CalculatorFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(null)

        supportFragmentManager.beginTransaction().replace(R.id.placeHolder,calculator).commit()
        // Получаем хранилище
        sharedPreferences = this.getSharedPreferences(ConstantsCalculator.vault,Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean(KEYSTORE_GETSTARTED,false)){
            startActivity(Intent(this@MainActivity,GetStartedActivity::class.java))

            val editor = sharedPreferences.edit()
            editor.putBoolean(KEYSTORE_GETSTARTED,true)
            editor.apply()
        }



    }

    override fun onResume() {
        super.onResume()
        requestAd()
    }


    private fun requestAd() {
        adRequest = AdRequest.Builder().build()
        MobileAds.initialize(this) {
            mAdView = findViewById(R.id.adViewBanner)
            mAdView.loadAd(adRequest)

        }
    }
}