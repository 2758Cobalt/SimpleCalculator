package com.cobaltumapps.simplecalculator.v15.calculator.preferences.sidesheet

import android.content.Context
import android.os.Bundle
import com.cobaltumapps.simplecalculator.databinding.SideSheetPreferencesBinding
import com.cobaltumapps.simplecalculator.services.VibratorService
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.google.android.material.sidesheet.SideSheetDialog
import com.google.android.material.slider.Slider

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
                    vibrationStrength = slider.value.toLong()
                    vibrator.playVibration(vibrationStrength)
                    preferencesUserData.vibrationStrength = vibrationStrength
                }
            })

            // Privacy policy
            prefPrivacyPolicy.setOnClickListener {  }

            // Release version
            val pkgInfo = context.packageManager.getPackageInfo(context.packageName!!,0)
            prefReleaseVersion.text = "${pkgInfo.versionName} (${pkgInfo.versionCode})"

            prefResetPreferences.setOnClickListener {
                loadConfig(PreferencesUserData())
            }
        }
    }

    /** Загрузка конфигурации */
    fun loadConfig(newConfig: PreferencesUserData) {
        binding.apply {
            with(newConfig) {
                prefAutoSave.isChecked = memoryAutoSave
                prefKeepLastRecord.isChecked = keepLastRecord
                prefOneHandedMode.isChecked = oneHandedMode
                prefVibration.isChecked = allowVibration
                if (vibrationStrength < prefVibrationStrengthSlider.valueFrom || vibrationStrength > prefVibrationStrengthSlider.valueTo) {
                    vibrationStrength = prefVibrationStrengthSlider.valueFrom.toLong()
                    prefVibrationStrengthSlider.value = prefVibrationStrengthSlider.valueFrom
                }
                prefVibrationStrengthSlider.value = vibrationStrength.toFloat()
            }
        }

        this.preferencesUserData = newConfig
    }
}

