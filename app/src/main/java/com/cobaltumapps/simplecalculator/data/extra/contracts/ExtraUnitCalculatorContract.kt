package com.cobaltumapps.simplecalculator.data.extra.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.cobaltumapps.simplecalculator.ui.activities.extra.calculators.unit.ExtraUnitCalculatorActivity

class ExtraUnitCalculatorContract: ActivityResultContract<String, Boolean>() {
    override fun createIntent(
        context: Context,
        input: String
    ): Intent {
        return Intent(context, ExtraUnitCalculatorActivity::class.java).apply {
            putExtra(ExtraUnitCalculatorActivity.Companion.INTENT_CALC_ID, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK || intent != null
    }
}