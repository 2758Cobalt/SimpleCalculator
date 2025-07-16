package com.cobaltumapps.simplecalculator.ui.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.databinding.ActivityExtraCalculatorsBinding
import com.cobaltumapps.simplecalculator.ui.fragments.extra.calculators.ExtraCalculatorSelectorFragment

class ExtraCalculatorsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityExtraCalculatorsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.converterToolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        with(binding) {
            supportFragmentManager.commit {
                replace(calculatorsContent.id, ExtraCalculatorSelectorFragment(), "Tag")
            }
        }
    }

}