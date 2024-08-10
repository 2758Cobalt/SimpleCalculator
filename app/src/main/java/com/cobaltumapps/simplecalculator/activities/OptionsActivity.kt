package com.cobaltumapps.simplecalculator.activities


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.dialogs.UpdateBoardDialogFragment
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.references.PreferenceKeys
import com.cobaltumapps.simplecalculator.services.VibratorService

class OptionsActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        supportFragmentManager.beginTransaction()
            .replace(R.id.optionsContainer, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private lateinit var sharedPreferences: SharedPreferences

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            sharedPreferences = requireContext().getSharedPreferences(ConstantsCalculator.vault,Context.MODE_PRIVATE)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ConstantsCalculator.privacyPolicyLink))

            // com.cobaltumapps.simplecalculator.system.CalculatorOld
            val autoSavePreference = findPreference<CheckBoxPreference>(PreferenceKeys.keyMemoryAutoSave) as CheckBoxPreference
            val saveLastCalculationPreference = findPreference<CheckBoxPreference>(PreferenceKeys.keySaveLastCalculation) as CheckBoxPreference

            // Keyboard
            val leftHandModePreference = findPreference<SwitchPreference>(PreferenceKeys.keyLeftHandMode) as SwitchPreference
            val oneHandedModePreference = findPreference<SwitchPreference>(PreferenceKeys.keyOneHandedMode) as SwitchPreference
            val vibrationPreference = findPreference<SwitchPreference>(PreferenceKeys.keyAllowVibration) as SwitchPreference

            // Info
            val privacyPolicy = findPreference<Preference>(PreferenceKeys.keyPrivacyPolicy)
            val versionAppPreference = findPreference<Preference>(PreferenceKeys.keyVersionApp)
            val whatsNewPreference = findPreference<Preference>(PreferenceKeys.keyUpdateInfo)


            // Назначает настройки из хранилища
            autoSavePreference.isChecked = sharedPreferences.getBoolean(PreferenceKeys.keyMemoryAutoSave, false)
            saveLastCalculationPreference.isChecked = sharedPreferences.getBoolean(PreferenceKeys.keySaveLastCalculation, true)

            leftHandModePreference.isChecked = sharedPreferences.getBoolean(PreferenceKeys.keyLeftHandMode, false)
            oneHandedModePreference.isChecked = sharedPreferences.getBoolean(PreferenceKeys.keyOneHandedMode, false)
            vibrationPreference.isChecked = sharedPreferences.getBoolean(PreferenceKeys.keyAllowVibration, true)

            autoSavePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    saveBooleanPreferences(PreferenceKeys.keyMemoryAutoSave, newValue as Boolean)
                    true
                }

            saveLastCalculationPreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    saveBooleanPreferences(PreferenceKeys.keySaveLastCalculation, newValue as Boolean)
                    true
                }

            leftHandModePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    saveBooleanPreferences(PreferenceKeys.keyLeftHandMode, newValue as Boolean)
                    Toast.makeText(context,resources.getString(R.string.system_restartMessage),Toast.LENGTH_LONG).show()

                    true
                }

            oneHandedModePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    saveBooleanPreferences(PreferenceKeys.keyOneHandedMode, newValue as Boolean)
                    true
                }

            vibrationPreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->

                    if (newValue as Boolean){
                        val vibrator = VibratorService()
                        vibrator.context = context
                        vibrator.playVibration(5)
                    }

                    saveBooleanPreferences(PreferenceKeys.keyAllowVibration, newValue)
                    true
                }


            privacyPolicy?.setOnPreferenceClickListener { startActivity(intent); true }
            whatsNewPreference?.setOnPreferenceClickListener { UpdateBoardDialogFragment().show(parentFragmentManager,UpdateBoardDialogFragment.TAG); true }

            val pkgInfo = context?.packageManager!!.getPackageInfo(context?.packageName!!,0)
            versionAppPreference?.summary = "${pkgInfo.versionName} (${pkgInfo.versionCode})"
        }

        private fun saveBooleanPreferences(key: String, variable: Boolean) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(key, variable)
            editor.apply()
        }


    }

}