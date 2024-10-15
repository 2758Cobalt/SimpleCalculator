package com.cobaltumapps.simplecalculator.v15.calculator.preferences.sidesheet

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat.getString
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.SideSheetPreferencesBinding
import com.cobaltumapps.simplecalculator.services.VibratorService
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.google.android.material.sidesheet.SideSheetDialog
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar

class PreferencesSideSheetDialog(context: Context) : SideSheetDialog(context)  {
    private val binding by lazy { SideSheetPreferencesBinding.inflate(layoutInflater) }
    private var vibrationStrength = 5L

    // Configuration
    var preferencesUserData = PreferencesUserData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vibrator = VibratorService()
        vibrator.context = context

        binding.apply {
            setContentView(root)

            // Preferences
            prefAutoSave.setOnCheckedChangeListener { _, checked -> preferencesUserData.memoryAutoSave = checked }

            prefKeepLastRecord.setOnCheckedChangeListener { _, checked -> preferencesUserData.keepLastRecord = checked }

            prefLeftHandMode.setOnCheckedChangeListener { _, checked ->
                preferencesUserData.leftHandedMode = checked

                if (checked)
                    Snackbar.make(binding.root, getString(context, R.string.system_restartMessage), Snackbar.LENGTH_SHORT).show()
            }

            prefOneHandedMode.setOnCheckedChangeListener { _, checked -> preferencesUserData.oneHandedMode = checked }

            prefVibration.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    vibrator.playVibration(vibrationStrength)
                }

                preferencesUserData.allowVibration = checked
                prefVibrationStrengthSlider.isEnabled = checked
            }

            prefVibrationStrengthSlider.addOnSliderTouchListener(object: Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    vibrationStrength = (slider.value * 10).toLong()
                    vibrator.playVibration(vibrationStrength)
                    preferencesUserData.vibrationStrength = vibrationStrength
                }
            })

            // Back to old calculator
            prefToOldCalculator.setOnClickListener {  }

            // Privacy policy
            prefPrivacyPolicy.setOnClickListener {  }
            prefNews.setOnClickListener { }

            // Release version
            val pkgInfo = context.packageManager.getPackageInfo(context.packageName!!,0)
            prefReleaseVersion.text = "${pkgInfo.versionName} (${pkgInfo.versionCode})"
        }
    }

    fun loadConfig(newConfig: PreferencesUserData) {
        binding.apply {
            with(newConfig) {
                prefAutoSave.isChecked = memoryAutoSave
                prefKeepLastRecord.isChecked = keepLastRecord
                prefLeftHandMode.isChecked = leftHandedMode
                prefOneHandedMode.isChecked = oneHandedMode
                prefVibration.isChecked = allowVibration
                prefVibrationStrengthSlider.value = vibrationStrength.toFloat() / 10
            }
        }

        this.preferencesUserData = newConfig
    }
}

