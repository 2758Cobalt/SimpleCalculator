package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityConverterNBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterViewPagerAdapter
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterUnit
import com.cobaltumapps.simplecalculator.v15.converter.interfaces.SelectorFragmentListener
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterSelectorFragment
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterUnitFragment

class MainConverterActivity : AppCompatActivity(), SelectorFragmentListener {
    private val binding by lazy { ActivityConverterNBinding.inflate(layoutInflater) }

    private val converterSelector by lazy { ConverterSelectorFragment(this) }
    private val converterUnitFragment by lazy { ConverterUnitFragment(this) }
    private var canLeaveFromSelector = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.converterToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.converterViewPager.apply {
            adapter = ConverterViewPagerAdapter(this@MainConverterActivity, listOf(converterSelector, converterUnitFragment))
            isUserInputEnabled = false
        }

        onBackPressedDispatcher.addCallback(object :  OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (canLeaveFromSelector) {
                    finish()
                }
                else {
                    binding.converterViewPager.setCurrentItem(0, true)
                    canLeaveFromSelector = true
                }
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSelectedItem(selectedUnit: ConverterUnit) {
        binding.converterViewPager.setCurrentItem(1, true)
        converterUnitFragment.onSelectedItem(selectedUnit)
        canLeaveFromSelector = false
    }
}