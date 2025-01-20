package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.cobaltumapps.simplecalculator.databinding.ActivityCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.fragments.calculator.CalculatorFragment
import com.cobaltumapps.simplecalculator.v15.google.admob.SimpleApplication
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainCalculatorActivity : AppCompatActivity(),
    CalculatorNavigationListener
{
    private val binding by lazy { ActivityCalculatorBinding.inflate(layoutInflater) }

    // Ad (Advertisement)
    private val adRequest by lazy { AdRequest.Builder().build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        (application as SimpleApplication).showAdIfAvailable(this) { }

        val calculatorFragment = supportFragmentManager.findFragmentByTag(CalculatorFragment.TAG) as? CalculatorFragment
        if (calculatorFragment == null) {
            val newFragment = CalculatorFragment()
            supportFragmentManager.commit {
                replace(binding.calculatorPlaceHolder.id, newFragment, CalculatorFragment.TAG)
            }
        } else {
            calculatorFragment.setCalculatorNavigationListener(this)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            MobileAds.initialize(this@MainCalculatorActivity) {
                binding.calculatorAdViewBanner.loadAd(adRequest)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.calculatorAdViewBanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.calculatorAdViewBanner.pause()
    }

    override fun onDestroy() {
        binding.calculatorAdViewBanner.destroy()
        super.onDestroy()
    }

    override fun goArchive() {
        startActivity(Intent(this, ArchiveActivity::class.java))
    }

    override fun goConverters() {
        startActivity(Intent(this, MainConverterActivity::class.java))
    }

    companion object {
        const val LOG_TAG = "SC_MainCalculatorActivity" +
                "Tag"
    }
}