package com.cobaltumapps.simplecalculator.ui.activities.extra.calculators.unit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.cobaltumapps.simplecalculator.databinding.ActivityExtraUnitCalculatorBinding
import com.cobaltumapps.simplecalculator.domain.viewmodel.ExtraUnitReviewerViewModel
import com.cobaltumapps.simplecalculator.ui.fragments.extra.calculators.ExtraUnitReviewerFragment

class ExtraUnitCalculatorActivity : AppCompatActivity() {
    private val binding by lazy { ActivityExtraUnitCalculatorBinding.inflate(layoutInflater) }

    private val extraUnitReviewerViewModel by lazy { ViewModelProvider(this)[ExtraUnitReviewerViewModel::class] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.extraUnitToolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeButtonEnabled(true)
        }

        supportFragmentManager.commit {
            replace(binding.extraUnitActivityContent.id, ExtraUnitReviewerFragment(), "UnitReviewerTag")
        }
    }

    override fun onStart() {
        super.onStart()

        intent.getStringExtra(INTENT_CALC_ID)?.let {
            extraUnitReviewerViewModel.onSelectedCalculator(it)
        }
    }

    companion object {
        const val INTENT_CALC_ID = "SC_ExtraUnitCalculatorIntentKey_CalculatorId"
    }

}