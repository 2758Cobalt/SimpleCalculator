package com.cobaltumapps.simplecalculator.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.onBoarding.OnBoardingActivity
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.calculator.host.CalculatorFragmentN
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

private const val KEYSTORE_GETSTARTED = "getStarted_key"
private const val GETSTARTED_FORCE_LAUNCH = false // Игнорит статус показа и запускает стартовый экран (для тестов)
private const val LOG_TAG = "DebugTag"

class MainActivity: AppCompatActivity() {

    private lateinit var mAdView : AdView
    private lateinit var adRequest : AdRequest
    private lateinit var sharedPreferences: SharedPreferences

    private val handler = Handler()
    private val delayMillis = 45000L // 20 секунд

    private val requestAdRunnable = object : Runnable {
        override fun run() {
            requestAd()

            // Повторно запускаем себя через delayMillis
            handler.postDelayed(this, delayMillis)
        }
    }

    private val calculator: CalculatorFragmentN = CalculatorFragmentN()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(null)

        supportFragmentManager.beginTransaction().replace(R.id.placeHolder, calculator).commit()

        // Получаем хранилище
        sharedPreferences = this.getSharedPreferences(ConstantsCalculator.vault,Context.MODE_PRIVATE)

        // Проверяем был ли показан стартовый экран
        if (!GETSTARTED_FORCE_LAUNCH){
            if (!sharedPreferences.getBoolean(KEYSTORE_GETSTARTED,false)){
                startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))

                val editor = sharedPreferences.edit()
                editor.putBoolean(KEYSTORE_GETSTARTED,true)
                editor.apply()
            }

        }
        else
            startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))



    }

    override fun onResume() {
        super.onResume()

        // Запрашиваем рекламу
        requestAd()

        // Планируем выполнение запроса рекламы каждые 20 секунд
        handler.postDelayed(requestAdRunnable, delayMillis)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(requestAdRunnable)
    }

    private fun requestAd() {
        adRequest = AdRequest.Builder().build()
        MobileAds.initialize(this) {
            mAdView = findViewById(R.id.adViewBanner)
            mAdView.loadAd(adRequest)

        }
    }

    override fun onDestroy() {
        //calculator.saveData()
        super.onDestroy()
    }
}