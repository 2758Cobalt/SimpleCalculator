package com.cobaltumapps.simplecalculator.ui.activities.extra.calculators.unit

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
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

        intent.getStringExtra(INTENT_CALC_ID)?.let { calcId ->
            extraUnitReviewerViewModel.onSelectedCalculator(calcId)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(INTENT_CALC_ID, ExtraSelectableCalculatorInfo::class.java)?.let { calcInfo ->
                setExtraCalculatorData(calcInfo.title, calcInfo.drawableResId, calcInfo.previewValues)
                extraUnitReviewerViewModel.onSelectedCalculator(calcInfo.calculatorId)
            }
        }
        else {
            intent.getParcelableExtra<ExtraSelectableCalculatorInfo>(INTENT_CALC_ID)?.let { calcInfo ->
                setExtraCalculatorData(calcInfo.title, calcInfo.drawableResId, calcInfo.previewValues)
                extraUnitReviewerViewModel.onSelectedCalculator(calcInfo.calculatorId)
            }
        }

    }

    private fun setExtraCalculatorData(title: String, imageRes: Int?, previewUnits: String?) {
        with(binding) {
            extraUnitCalculatorName.text = title
            extraUnitCalculatorImage.setImageResource(imageRes ?: R.drawable.ic_weight)
            extraUnitCalculatorPreviewUnits.text = previewUnits ?: ""
        }
    }

    companion object {
        const val INTENT_CALC_ID = "SC_ExtraUnitCalculatorIntentKey_CalculatorId"
    }

}