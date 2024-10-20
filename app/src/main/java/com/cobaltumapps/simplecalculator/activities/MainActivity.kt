package com.cobaltumapps.simplecalculator.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ActivityMainBinding
import com.cobaltumapps.simplecalculator.onBoarding.OnBoardingActivity
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.PreferencesManager
import com.cobaltumapps.simplecalculator.v15.fragments.calculator.CalculatorFragmentN
import com.cobaltumapps.simplecalculator.v15.google.AdManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity: AppCompatActivity(), CalculatorNavigationListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var adManager = AdManager()

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
    private val preferencesManager by lazy { PreferencesManager(this) }

    companion object {
        private const val KEYSTORE_BOARDING = "SC_MainActivity_key"
        private const val BOARDING_FORCE_LAUNCH = false // Игнорит статус показа и запускает стартовый экран (для тестов)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setBackgroundDrawable(null)

        supportFragmentManager.beginTransaction().replace(R.id.placeHolder, calculator).commit()

        // Получаем хранилище
        sharedPreferences = this.getSharedPreferences(ConstantsCalculator.vault, Context.MODE_PRIVATE)

        // Проверяем был ли показан стартовый экран
        if (!BOARDING_FORCE_LAUNCH) {
            if (!sharedPreferences.getBoolean(KEYSTORE_BOARDING, false)){
                startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))

                val editor = sharedPreferences.edit()
                editor.putBoolean(KEYSTORE_BOARDING, true)
                editor.apply()
            }
        }
        else
            startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))

        //calculator.updatePreferences(UserPreferences())
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

    override fun goConverters() {
        val intent = Intent(this, ConverterActivity::class.java)
        startActivity(intent)
    }
}