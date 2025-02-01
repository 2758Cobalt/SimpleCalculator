package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.cobaltumapps.simplecalculator.databinding.ActivitySettingsBinding
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.calculator.services.tallback.VibrationService
import com.cobaltumapps.simplecalculator.v15.preferences.IntentKeys

/** Активность, предоставляющая набор параметров для настройки приложения */
class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val vibrationService by lazy { VibrationService(this@SettingsActivity) }

    // Configuration
    private var preferencesUserData = PreferencesUserData()
    private val sharedPreferences by lazy { getSharedPreferences(ConstantsCalculator.vaultPreferences, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null)
            setConditionOptions()
        else {
            preferencesUserData.apply {
                memoryAutoSave = savedInstanceState.getBoolean(IntentKeys.SETTINGS_autoSaveMemoryKey, binding.prefAutoSaveMemoryCheckBox.isChecked)
                keepLastRecord = savedInstanceState.getBoolean(IntentKeys.SETTINGS_keepLastCalculationKey, binding.prefKeepLastRecordCheckBox.isChecked)
                allowVibration = savedInstanceState.getBoolean(IntentKeys.SETTINGS_allowVibrationKey, binding.prefAllowVibrationSwitch.isChecked)
            }

            with(binding) {
                prefAutoSaveMemoryCheckBox.isChecked = savedInstanceState.getBoolean(IntentKeys.SETTINGS_autoSaveMemoryKey, false)
                prefKeepLastRecordCheckBox.isChecked = savedInstanceState.getBoolean(IntentKeys.SETTINGS_keepLastCalculationKey, false)
                prefAllowVibrationSwitch.isChecked = savedInstanceState.getBoolean(IntentKeys.SETTINGS_autoSaveMemoryKey, true)
            }
        }

        with(binding) {
            prefAutoSaveMemoryCheckBox.setOnCheckedChangeListener { _, checked ->
                preferencesUserData.memoryAutoSave = checked
            }

            prefKeepLastRecordCheckBox.setOnCheckedChangeListener { _, checked ->
                preferencesUserData.keepLastRecord = checked
            }

            prefAllowVibrationSwitch.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    vibrationService.playVibration(true)
                }
                preferencesUserData.allowVibration = checked
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

    override fun onDestroy() {
        saveSettings()
        super.onDestroy()
    }

    // Устанавливает состояние переключателям согласно параметрам (если они отсувствуют - устанавливаются значения по-умолчанию)
    private fun setConditionOptions() {
        loadSettings()

        with(binding) {
            with(preferencesUserData) {
                prefAutoSaveMemoryCheckBox.isChecked = memoryAutoSave
                prefKeepLastRecordCheckBox.isChecked = keepLastRecord
                prefAllowVibrationSwitch.isChecked = allowVibration
            }
        }
    }

    private fun loadSettings() {
        with(sharedPreferences) {
            preferencesUserData.apply {
                memoryAutoSave = getBoolean(OptionName.AutoSaveMemory.name, false)
                keepLastRecord = getBoolean(OptionName.KeepLastRecord.name, false)
                allowVibration = getBoolean(OptionName.AllowVibration.name, true)
            }
        }
    }

    // Сохраняет все настройки в хранилище SharePreferences
    private fun saveSettings() {
        sharedPreferences.edit {
            with(preferencesUserData) {
                putBoolean(OptionName.AutoSaveMemory.name, memoryAutoSave)
                putBoolean(OptionName.KeepLastRecord.name, keepLastRecord)
                putBoolean(OptionName.AllowVibration.name, allowVibration)
            }
        }
    }
}