package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ActivityConverterBinding
import com.cobaltumapps.simplecalculator.onBoarding.ViewPagerAdapter
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterCalculatorNavigationListener
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterNavigationItemSelectedListener
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterPageNavigationListener
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.mediator.ConverterMediator
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterCalculatorFragment
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterPageFragment
import com.google.android.material.navigation.NavigationView

class MainConverterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ConverterNavigationItemSelectedListener, ConverterPageNavigationListener, ConverterCalculatorNavigationListener
{
    private val binding by lazy { ActivityConverterBinding.inflate(layoutInflater) }

    private val converterMediator = ConverterMediator()

    private lateinit var converterPageFragment: ConverterPageFragment
    private lateinit var converterCalculatorFragment: ConverterCalculatorFragment

    private lateinit var toggleButtonDrawer: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.converterToolbar)

        if (savedInstanceState == null) {
            converterPageFragment = ConverterPageFragment(converterMediator)
            converterCalculatorFragment = ConverterCalculatorFragment(converterMediator)

            converterPageFragment.navigationListener = this@MainConverterActivity
            converterCalculatorFragment.navigationListener = this@MainConverterActivity
        }

        with(binding) {
            converterNavigationView.setNavigationItemSelectedListener(this@MainConverterActivity)

            converterViewPager.apply {
                adapter = ViewPagerAdapter(
                    listOf(converterPageFragment, converterCalculatorFragment),
                    supportFragmentManager,
                    lifecycle
                )
                offscreenPageLimit = 2
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_converter_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.converter_action_home -> finishActivity(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConverterNavigationItemSelected(converterType: ConverterType) {
        converterPageFragment.onConverterNavigationItemSelected(converterType)
        binding.converterToolbar.title = converterType.name
    }

    override fun onClickedCalculatorButton() {
        binding.converterViewPager.setCurrentItem(1, true)
    }

    override fun onConfirmUserEntry() {
        binding.converterViewPager.setCurrentItem(0, true)
    }
}
