package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.activities.ConverterActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityMainCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.fragments.calculator.CalculatorFragmentN

class MainCalculatorActivity : AppCompatActivity(), CalculatorNavigationListener {
    private val binding by lazy { ActivityMainCalculatorBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setBackgroundDrawable(null)

        val calculatorFragment = supportFragmentManager.findFragmentByTag(CalculatorFragmentN.TAG) as? CalculatorFragmentN
        if (calculatorFragment == null) {
            // Create new fragment instance and add it
            val newFragment = CalculatorFragmentN()
            supportFragmentManager.commit {
                replace(binding.placeHolder.id, newFragment, CalculatorFragmentN.TAG)
            }
        } else {
            // Fragment exists, just restore listener if necessary
            calculatorFragment.setCalculatorNavigationListener(this)
        }
    }

    override fun goConverters() {
        startActivity(
            Intent(this, ConverterActivity::class.java)
        )
    }

    override fun openNavigationMenu() {
        binding.mainDrawer.openDrawer(GravityCompat.START)
    }

    companion object {
        const val LOG_TAG = "SC_MainActivityTag"
    }
}