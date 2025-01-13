package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.databinding.ActivityMainCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.fragments.calculator.CalculatorFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class MainCalculatorActivity : AppCompatActivity(),
    CalculatorNavigationListener
{
    private val binding by lazy { ActivityMainCalculatorBinding.inflate(layoutInflater) }

    private lateinit var adRequest : AdRequest

    private val handler = Handler()
    private val delayMillis = 45000L // 20 секунд

    private val requestAdRunnable = object : Runnable {
        override fun run() {
            requestAd()

            // Повторно запускаем себя через delayMillis
            handler.postDelayed(this, delayMillis)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setBackgroundDrawable(null)

        val calculatorFragment = supportFragmentManager.findFragmentByTag(CalculatorFragment.TAG) as? CalculatorFragment
        if (calculatorFragment == null) {
            val newFragment = CalculatorFragment()
            supportFragmentManager.commit {
                replace(binding.placeHolder.id, newFragment, CalculatorFragment.TAG)
            }
        } else {
            calculatorFragment.setCalculatorNavigationListener(this)
        }

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
            binding.adViewBanner.loadAd(adRequest)

        }
    }

    override fun goArchive() {
        startActivity(Intent(this, ArchiveActivity::class.java))
    }

    override fun goConverters() {
        startActivity(Intent(this, MainConverterActivity::class.java))
    }

    companion object {
        const val LOG_TAG = "SC_MainActivityTag"
    }
}