package com.cobaltumapps.simplecalculator.v15.calculator.preferences.sidesheet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat.getString
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.SideSheetPreferencesBinding
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.references.PreferenceKeys
import com.cobaltumapps.simplecalculator.services.VibratorService
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.google.android.material.sidesheet.SideSheetDialog
import com.google.android.material.snackbar.Snackbar

class PreferencesSideSheetDialog(context: Context) : SideSheetDialog(context) {
    private val binding by lazy { SideSheetPreferencesBinding.inflate(layoutInflater) }

    private val sharedPreferences by lazy { context.getSharedPreferences(ConstantsCalculator.vault,Context.MODE_PRIVATE) }

    private val privacyPolicyLink = Intent(Intent.ACTION_VIEW, Uri.parse(ConstantsCalculator.privacyPolicyLink))

    // Configuration
    var preferencesUserData = PreferencesUserData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadConfig()

        binding.apply {
            setContentView(root)

            // Preferences
            prefAutoSave.setOnCheckedChangeListener { _, checked ->
                saveBooleanPreferences(PreferenceKeys.keyMemoryAutoSave, checked)
                preferencesUserData.memoryAutoSave = checked
            }

            prefKeepLastRecord.setOnCheckedChangeListener { _, checked ->
                saveBooleanPreferences(PreferenceKeys.keyKeepLastRecord, checked)
                preferencesUserData.keepLastRecord = checked
            }

            prefLeftHandMode.setOnCheckedChangeListener { _, checked ->
                saveBooleanPreferences(PreferenceKeys.keyLeftHandMode, checked)
                preferencesUserData.leftHandedMode = checked

                if (checked)
                    Snackbar.make(binding.root, getString(context, R.string.system_restartMessage), Snackbar.LENGTH_SHORT).show()
            }

            prefOneHandedMode.setOnCheckedChangeListener { _, checked ->
                saveBooleanPreferences(PreferenceKeys.keyOneHandedMode, checked)
                preferencesUserData.oneHandedMode = checked
            }

            prefVibration.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    val vibrator = VibratorService()
                    vibrator.context = context
                    vibrator.playVibration(5)
                }

                saveBooleanPreferences(PreferenceKeys.keyAllowVibration, checked)
                preferencesUserData.allowVibration = checked
            }

            // Back to old calculator
            prefToOldCalculator.setOnClickListener {  }

            // Privacy policy
            prefPrivacyPolicy.setOnClickListener {  }
            prefNews.setOnClickListener { }

            // Releave version
            val pkgInfo = context.packageManager.getPackageInfo(context.packageName!!,0)
            prefReleaseVersion.text = "${pkgInfo.versionName} (${pkgInfo.versionCode})"
        }
    }


    private fun loadConfig() {
        val newConfig =
            with(sharedPreferences) {
                PreferencesUserData(
                    getBoolean(PreferenceKeys.keyMemoryAutoSave, false),
                    getBoolean(PreferenceKeys.keyKeepLastRecord, true),
                    getBoolean(PreferenceKeys.keyLeftHandMode, false),
                    getBoolean(PreferenceKeys.keyOneHandedMode, false),
                    getBoolean(PreferenceKeys.keyAllowVibration, true)
                )
            }

        this.preferencesUserData = newConfig
        binding.apply {
            prefAutoSave.isChecked = newConfig.memoryAutoSave
            prefKeepLastRecord.isChecked = newConfig.keepLastRecord
            prefLeftHandMode.isChecked = newConfig.leftHandedMode
            prefOneHandedMode.isChecked = newConfig.oneHandedMode
            prefVibration.isChecked = newConfig.allowVibration
        }
    }

    private fun saveBooleanPreferences(key: String, variable: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, variable)
        editor.apply()
    }
}

