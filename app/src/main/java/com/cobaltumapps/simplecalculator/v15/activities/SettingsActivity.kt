package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivitySettingsBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.settings.SettingsSingleton
import com.cobaltumapps.simplecalculator.v15.preferences.IntentKeys

/** Активность, предоставляющая набор параметров для настройки приложения */
class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            prefAutoSaveMemoryCheckBox.isChecked = SettingsSingleton.getPreferenceCondition(
                IntentKeys.SETTINGS_autoSaveMemoryKey,
                false
            )
            prefKeepLastRecordCheckBox.isChecked = SettingsSingleton.getPreferenceCondition(
                IntentKeys.SETTINGS_keepLastCalculationKey,
                false
            )
            prefAllowVibrationSwitch.isChecked = SettingsSingleton.getPreferenceCondition(
                IntentKeys.SETTINGS_allowVibrationKey,
                true
            )

            prefAutoSaveMemoryCheckBox.setOnCheckedChangeListener { _, checked ->
                SettingsSingleton.setPreferenceCondition(
                    IntentKeys.SETTINGS_autoSaveMemoryKey,
                    checked
                )
            }

            prefKeepLastRecordCheckBox.setOnCheckedChangeListener { _, checked ->
                SettingsSingleton.setPreferenceCondition(
                    IntentKeys.SETTINGS_keepLastCalculationKey,
                    checked
                )
            }

            prefAllowVibrationSwitch.setOnCheckedChangeListener { _, checked ->
                SettingsSingleton.setPreferenceCondition(
                    IntentKeys.SETTINGS_allowVibrationKey,
                    checked
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.apply {
            putBoolean(IntentKeys.SETTINGS_autoSaveMemoryKey, binding.prefAutoSaveMemoryCheckBox.isChecked)
            putBoolean(IntentKeys.SETTINGS_keepLastCalculationKey, binding.prefKeepLastRecordCheckBox.isChecked)
            putBoolean(IntentKeys.SETTINGS_allowVibrationKey, binding.prefAllowVibrationSwitch.isChecked)
        }
    }
}