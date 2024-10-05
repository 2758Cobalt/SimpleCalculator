package com.cobaltumapps.simplecalculator.v15.calculator.preferences.sidesheet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.SideSheetPreferencesBinding
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.references.PreferenceKeys
import com.cobaltumapps.simplecalculator.services.VibratorService
import com.google.android.material.sidesheet.SideSheetDialog

class PreferencesSideSheet(context: Context) : SideSheetDialog(context) {
    private val binding by lazy { SideSheetPreferencesBinding.inflate(layoutInflater) }

    private val privacyPolicyLink = Intent(Intent.ACTION_VIEW, Uri.parse(ConstantsCalculator.privacyPolicyLink))

    private val sharedPreferences by lazy { context.getSharedPreferences(ConstantsCalculator.vault,Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setContentView(root)

            prefAutoSave.setOnCheckedChangeListener { compoundButton, checked ->
                saveBooleanPreferences(PreferenceKeys.keyMemoryAutoSave, checked)
            }

            prefKeepLastRecord.setOnCheckedChangeListener { compoundButton, checked ->
                saveBooleanPreferences(PreferenceKeys.keySaveLastCalculation, checked)
            }

            prefLeftHandMode.setOnCheckedChangeListener { compoundButton, checked ->
                saveBooleanPreferences(PreferenceKeys.keyLeftHandMode, checked)
                Toast.makeText(context, getString(context,R.string.system_restartMessage), Toast.LENGTH_LONG).show()
            }

            prefOneHandedMode.setOnCheckedChangeListener { compoundButton, checked ->
                saveBooleanPreferences(PreferenceKeys.keyOneHandedMode, checked)
            }

            prefVibration.setOnCheckedChangeListener { compoundButton, checked ->
                if (checked){
                    val vibrator = VibratorService()
                    vibrator.context = context
                    vibrator.playVibration(5)
                }

                saveBooleanPreferences(PreferenceKeys.keyAllowVibration, checked)
            }

            // Privacy policy
            prefPrivacyPolicy.setOnClickListener {  }
            prefNews.setOnClickListener { }

            // Releave version
            val pkgInfo = context.packageManager.getPackageInfo(context.packageName!!,0)
            prefReleaseVersion.text = "${pkgInfo.versionName} (${pkgInfo.versionCode})"
        }
    }

    private fun saveBooleanPreferences(key: String, variable: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, variable)
        editor.apply()
    }

}

