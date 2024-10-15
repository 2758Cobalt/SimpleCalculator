package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.activities.ConverterActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityMainCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.fragments.calculator.CalculatorFragmentN
import com.google.android.material.navigation.NavigationView

class MainCalculatorActivity : AppCompatActivity(),
    CalculatorNavigationListener,
    NavigationView.OnNavigationItemSelectedListener
{
    private val binding by lazy { ActivityMainCalculatorBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setBackgroundDrawable(null)
        binding.mainNavigation.setNavigationItemSelectedListener(this)

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.main_menu_calculator -> { binding.mainDrawer.close() }
            R.id.main_menu_converter -> { goConverters() }
            R.id.main_menu_news -> { goNews() }
        }
        return true
    }

    override fun goConverters() {
        startActivity(
            Intent(this, ConverterActivity::class.java)
        )
    }

    fun goNews() {
        //startActivity(Intent(this, ConverterActivity::class.java))
    }

    override fun openNavigationMenu() {
        binding.mainDrawer.openDrawer(GravityCompat.START)
    }

    companion object {
        const val LOG_TAG = "SC_MainActivityTag"
    }
}