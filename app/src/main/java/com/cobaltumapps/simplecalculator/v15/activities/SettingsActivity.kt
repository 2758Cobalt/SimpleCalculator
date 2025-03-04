package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.databinding.ActivitySettingsBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.settings.SettingsSingleton
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName

/** Активность, предоставляющая набор параметров для настройки приложения */
class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            prefAutoSaveMemoryCheckBox.isChecked = SettingsSingleton.getPreferenceCondition(
                OptionName.AutoSaveMemory.name,
                false
            )

            prefKeepLastRecordCheckBox.isChecked = SettingsSingleton.getPreferenceCondition(
                OptionName.KeepLastRecord.name,
                false
            )

            prefAllowVibrationSwitch.isChecked = SettingsSingleton.getPreferenceCondition(
                OptionName.AllowVibration.name,
                true
            )

            prefAutoSaveMemoryCheckBox.setOnCheckedChangeListener { _, checked ->
                SettingsSingleton.setPreferenceCondition(
                    OptionName.AutoSaveMemory.name,
                    checked
                )
            }

            prefKeepLastRecordCheckBox.setOnCheckedChangeListener { _, checked ->
                SettingsSingleton.setPreferenceCondition(
                    OptionName.KeepLastRecord.name,
                    checked
                )
            }

            prefAllowVibrationSwitch.setOnCheckedChangeListener { _, checked ->
                SettingsSingleton.setPreferenceCondition(
                    OptionName.AllowVibration.name,
                    checked
                )
            }
        }
    }
}