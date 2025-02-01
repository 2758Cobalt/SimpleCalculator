package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.cobaltumapps.simplecalculator.databinding.ActivitySettingsBinding
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.calculator.services.tallback.VibrationService
import com.cobaltumapps.simplecalculator.v15.preferences.IntentKeys
import com.cobaltumapps.simplecalculator.v15.preferences.PreferencesKeys
import com.google.android.material.slider.Slider

/** Активность, предоставляющая набор параметров для настройки приложения */
class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val vibrationService by lazy { VibrationService(this@SettingsActivity) }

    private var vibrationStrength = 5L

    enum class OptionName (keyOptionName: String) {
        AutoSaveMemory(PreferencesKeys.keyMemoryAutoSave),
        KeepLastRecord(PreferencesKeys.keyKeepLastRecord),
        AllowCalculationsHistory(PreferencesKeys.keyAllowCalculationsHistory),
        AllowVibration(PreferencesKeys.keyAllowVibration),
        VibrationStrength(PreferencesKeys.keyVibrationStrength)
    }

    // Configuration
    var preferencesUserData = PreferencesUserData()
    private val sharedPreferences by lazy { getSharedPreferences(ConstantsCalculator.vault, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null)
            setConditionOptions()
        else {
            preferencesUserData.apply {
                memoryAutoSave = savedInstanceState.getBoolean(IntentKeys.SETTINGS_autoSaveMemoryKey, binding.prefAutoSaveMemoryCheckBox.isChecked)
                keepLastRecord = savedInstanceState.getBoolean(IntentKeys.SETTINGS_keepLastCalculationKey, binding.prefKeepLastRecordCheckBox.isChecked)
                allowCalculationsHistory = savedInstanceState.getBoolean(IntentKeys.SETTINGS_allowCalculationsHistoryKey, binding.prefAllowCalcHistoryCheckBox.isChecked)

                allowVibration = savedInstanceState.getBoolean(IntentKeys.SETTINGS_allowVibrationKey, binding.prefAllowVibrationSwitch.isChecked)
                vibrationStrength = savedInstanceState.getLong(IntentKeys.SETTINGS_vibrationStrengthKey, binding.prefVibrationStrengthSlider.value.toLong())
            }

            with(binding) {
                prefAutoSaveMemoryCheckBox.isChecked = savedInstanceState.getBoolean(IntentKeys.SETTINGS_autoSaveMemoryKey, false)
                prefKeepLastRecordCheckBox.isChecked = savedInstanceState.getBoolean(IntentKeys.SETTINGS_keepLastCalculationKey, false)
                prefAllowCalcHistoryCheckBox.isChecked = savedInstanceState.getBoolean(IntentKeys.SETTINGS_allowCalculationsHistoryKey, true)

                prefAllowVibrationSwitch.isChecked = savedInstanceState.getBoolean(IntentKeys.SETTINGS_autoSaveMemoryKey, true)
                prefVibrationStrengthSlider.value = savedInstanceState.getLong(IntentKeys.SETTINGS_vibrationStrengthKey, vibrationStrength).toFloat()
            }
        }

        with(binding) {
            prefAutoSaveMemoryCheckBox.setOnCheckedChangeListener { _, checked ->
                preferencesUserData.memoryAutoSave = checked
            }

            prefKeepLastRecordCheckBox.setOnCheckedChangeListener { _, checked ->
                preferencesUserData.keepLastRecord = checked
            }

            prefAllowCalcHistoryCheckBox.setOnCheckedChangeListener { _, checked ->
                preferencesUserData.allowCalculationsHistory = checked
            }

            prefAllowVibrationSwitch.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    vibrationService.apply {
                        duration = vibrationStrength
                        playVibration()
                    }
                }

                prefVibrationStrengthSlider.isEnabled = checked
                preferencesUserData.allowVibration = checked
            }

            prefVibrationStrengthSlider.addOnSliderTouchListener(object: Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    if (prefAllowVibrationSwitch.isChecked) {
                        vibrationStrength = slider.value.toLong()
                        vibrationService.apply {
                            duration = vibrationStrength
                            playVibration()
                        }
                        preferencesUserData.vibrationStrength = vibrationStrength
                    }
                }
            })
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.apply {
            putBoolean(IntentKeys.SETTINGS_autoSaveMemoryKey, binding.prefAutoSaveMemoryCheckBox.isChecked)
            putBoolean(IntentKeys.SETTINGS_keepLastCalculationKey, binding.prefKeepLastRecordCheckBox.isChecked)
            putBoolean(IntentKeys.SETTINGS_allowCalculationsHistoryKey, binding.prefAllowCalcHistoryCheckBox.isChecked)
            putBoolean(IntentKeys.SETTINGS_allowVibrationKey, binding.prefAllowVibrationSwitch.isChecked)
            putLong(IntentKeys.SETTINGS_vibrationStrengthKey, binding.prefVibrationStrengthSlider.value.toLong())
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
                prefAllowCalcHistoryCheckBox.isChecked = allowCalculationsHistory

                prefAllowVibrationSwitch.isChecked = allowVibration
                this@SettingsActivity.vibrationStrength = vibrationStrength
            }

            // Проверка, которая проверяет выход за предел значений prefVibrationStrengthSlider
            if (vibrationStrength < prefVibrationStrengthSlider.valueFrom
                || vibrationStrength > prefVibrationStrengthSlider.valueTo)
            {
                vibrationStrength = prefVibrationStrengthSlider.valueFrom.toLong()
                prefVibrationStrengthSlider.value = prefVibrationStrengthSlider.valueFrom
            }
            prefVibrationStrengthSlider.value = vibrationStrength.toFloat()
            prefVibrationStrengthSlider.isEnabled = prefAllowVibrationSwitch.isChecked
        }
    }

    private fun loadSettings() {
        with(sharedPreferences) {
            preferencesUserData.apply {
                memoryAutoSave = getBoolean(OptionName.AutoSaveMemory.name, false)
                keepLastRecord = getBoolean(OptionName.KeepLastRecord.name, false)
                allowCalculationsHistory = getBoolean(OptionName.AllowCalculationsHistory.name, true)
                allowVibration = getBoolean(OptionName.AllowVibration.name, true)
                vibrationStrength = getLong(OptionName.VibrationStrength.name, vibrationStrength)
            }
        }
    }

    // Сохраняет все настройки в хранилище SharePreferences
    private fun saveSettings() {
        sharedPreferences.edit {
            with(preferencesUserData) {
                putBoolean(OptionName.AutoSaveMemory.name, memoryAutoSave)
                putBoolean(OptionName.KeepLastRecord.name, keepLastRecord)
                putBoolean(OptionName.AllowCalculationsHistory.name, allowCalculationsHistory)
                putBoolean(OptionName.AllowVibration.name, allowVibration)
                putLong(OptionName.VibrationStrength.name, vibrationStrength)
            }
        }
    }
}