package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ActivityCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.CalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.activities.onBoarding.IntroductionActivity
import com.cobaltumapps.simplecalculator.v15.calculator.components.settings.SettingsSingleton
import com.cobaltumapps.simplecalculator.v15.fragments.calculator.CalculatorPageFragment
import com.cobaltumapps.simplecalculator.v15.references.ConstantsCalculator
import com.google.android.material.navigation.NavigationView

class MainCalculatorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    CalculatorNavigationListener {
    private val binding by lazy { ActivityCalculatorBinding.inflate(layoutInflater) }
    private val sharedPreferences by lazy {
        getSharedPreferences(ConstantsCalculator.vaultPreferences, Context.MODE_PRIVATE)
    }

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        SettingsSingleton.getInstance(sharedPreferences)

        actionBarDrawerToggle = ActionBarDrawerToggle(this@MainCalculatorActivity,
            binding.calculatorDrawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        binding.calculatorDrawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        binding.calculatorNavigationView.setNavigationItemSelectedListener(this@MainCalculatorActivity)

        val calculatorPageFragment = supportFragmentManager.findFragmentByTag(CalculatorPageFragment.FRAG_TAG) as? CalculatorPageFragment
        if (calculatorPageFragment == null) {
            val newFragment = CalculatorPageFragment(this)
            supportFragmentManager.commit {
                replace(binding.calculatorPlaceHolder.id, newFragment, CalculatorPageFragment.FRAG_TAG)
            }
        }
        else
            calculatorPageFragment.calculatorNavigationListener = this

        val introductionCondition = sharedPreferences.getBoolean(INTRODUCTION_PREFERENCE_KEY, false)
        if (!introductionCondition) {
            startActivity(Intent(this@MainCalculatorActivity, IntroductionActivity::class.java))
            sharedPreferences.edit { putBoolean(INTRODUCTION_PREFERENCE_KEY, true) }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_nav_converters -> startActivity(Intent(this@MainCalculatorActivity, MainConverterActivity::class.java))
            R.id.menu_nav_features -> startActivity(Intent(this@MainCalculatorActivity, PurchasesActivity::class.java))
            R.id.menu_nav_archive -> startActivity(Intent(this@MainCalculatorActivity, ArchiveActivity::class.java))
            R.id.menu_nav_news_update -> startActivity(Intent(this@MainCalculatorActivity, NewsUpdateActivity::class.java))
            R.id.menu_nav_introduction -> startActivity(Intent(this@MainCalculatorActivity, IntroductionActivity::class.java))
            R.id.menu_nav_settings -> startActivity(Intent(this@MainCalculatorActivity, SettingsActivity::class.java))
        }
        return true
    }

    /** Открывает навигационное меню */
    override fun openNavigationMenu() {
        binding.calculatorDrawer.openDrawer(GravityCompat.START)
    }

    override fun onResume() {
        super.onResume()
        binding.calculatorDrawer.closeDrawer(GravityCompat.START)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        binding.calculatorDrawer.removeDrawerListener(actionBarDrawerToggle)
        super.onDestroy()
    }

    companion object {
        const val INTRODUCTION_PREFERENCE_KEY = "SC_IntroductionConditionShowedKey"
    }
}