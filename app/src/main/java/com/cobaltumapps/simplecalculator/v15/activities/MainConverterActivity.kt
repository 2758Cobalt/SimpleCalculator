package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ActivityConverterBinding
import com.cobaltumapps.simplecalculator.onBoarding.ViewPagerAdapter
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterNavigationItemSelectedListener
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterCalculatorFragment
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterPageFragment
import com.google.android.material.navigation.NavigationView

class MainConverterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ConverterNavigationItemSelectedListener
{
    private val binding by lazy { ActivityConverterBinding.inflate(layoutInflater) }

    private val converterPageFragment by lazy { ConverterPageFragment() }
    private val converterCalculatorFragment by lazy { ConverterCalculatorFragment() }

    private lateinit var toggleButtonDrawer: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.converterToolbar)

        with(binding) {
            converterNavigationView.setNavigationItemSelectedListener(this@MainConverterActivity)

            converterViewPager.apply {
                adapter = ViewPagerAdapter(
                    listOf(converterPageFragment, converterCalculatorFragment),
                    supportFragmentManager,
                    lifecycle
                )
            }

            toggleButtonDrawer = ActionBarDrawerToggle(
                this@MainConverterActivity, binding.converterDrawer, binding.converterToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )

            converterDrawer.addDrawerListener(toggleButtonDrawer)
            toggleButtonDrawer.syncState()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        onConverterNavigationItemSelected(
            when(item.itemId) {
                R.id.menu_weight -> ConverterType.Weight
                R.id.menu_length -> ConverterType.Length
                R.id.menu_time -> ConverterType.Time
                R.id.menu_speed -> ConverterType.Speed
                R.id.menu_temperature -> ConverterType.Temperature
                R.id.menu_volume -> ConverterType.Volume
                R.id.menu_area -> ConverterType.Area
                R.id.menu_frequency -> ConverterType.Frequency
                R.id.menu_data -> ConverterType.Data
                else -> ConverterType.Weight
            }
        )

        binding.converterDrawer.closeDrawers()
        return true
    }

    override fun onDestroy() {
        binding.converterDrawer.removeDrawerListener(toggleButtonDrawer)
        super.onDestroy()
    }

    override fun onConverterNavigationItemSelected(converterType: ConverterType) {
        converterPageFragment.onConverterNavigationItemSelected(converterType)
        binding.converterToolbar.title = converterType.name
    }
}