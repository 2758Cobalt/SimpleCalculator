package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.databinding.ActivityMainCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.fragments.calculator.CalculatorFragment

class MainCalculatorActivity : AppCompatActivity(),
    CalculatorNavigationListener
{
    private val binding by lazy { ActivityMainCalculatorBinding.inflate(layoutInflater) }

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