package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.databinding.ActivityMainCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.fragments.calculator.CalculatorFragmentN

class MainCalculatorActivity : AppCompatActivity(),
    CalculatorNavigationListener
{
    private val binding by lazy { ActivityMainCalculatorBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setBackgroundDrawable(null)

        val calculatorFragment = supportFragmentManager.findFragmentByTag(CalculatorFragmentN.TAG) as? CalculatorFragmentN
        if (calculatorFragment == null) {
            val newFragment = CalculatorFragmentN()
            supportFragmentManager.commit {
                replace(binding.placeHolder.id, newFragment, CalculatorFragmentN.TAG)
            }
        } else {
            calculatorFragment.setCalculatorNavigationListener(this)
        }
    }

    override fun goConverters() {
        /*supportFragmentManager.commit {
            replace(binding.placeHolder.id, ConverterCalculatorFragment())
        }*/
        startActivity(Intent(this, MainConverterActivity::class.java))
        Toast.makeText(this, "Go converters", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val LOG_TAG = "SC_MainActivityTag"
    }
}