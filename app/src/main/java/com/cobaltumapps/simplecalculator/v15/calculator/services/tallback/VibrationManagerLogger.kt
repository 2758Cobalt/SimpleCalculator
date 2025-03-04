package com.cobaltumapps.simplecalculator.v15.calculator.services.tallback

import android.util.Log

class VibrationManagerLogger: VibrationServiceController {
    override fun playVibration() {
        Log.d(LOG_TAG, "Vibrate!")
    }

    companion object {
        const val LOG_TAG = "SC_VibrationManagerTag"
    }
}