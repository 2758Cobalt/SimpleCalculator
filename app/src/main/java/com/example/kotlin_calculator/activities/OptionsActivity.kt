package com.example.kotlin_calculator.activities


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.kotlin_calculator.R
import com.example.kotlin_calculator.references.ConstantsCalculator


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


            //
            val autoSavePreference = findPreference<CheckBoxPreference>(ConstantsCalculator.keysPreferences[0]) as CheckBoxPreference

            val leftHandModePreference = findPreference<SwitchPreference>(ConstantsCalculator.keysPreferences[1]) as SwitchPreference

            val expandModePreference = findPreference<CheckBoxPreference>(ConstantsCalculator.keysPreferences[2]) as CheckBoxPreference

            // Назначает настройки из хранилища
            autoSavePreference.isChecked = sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[0], false)
            leftHandModePreference.isChecked = sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[1], false)
            expandModePreference.isChecked = sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[2], false)

            autoSavePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    saveBooleanPreferences(ConstantsCalculator.keysPreferences[0], newValue as Boolean)
                    true
                }
            leftHandModePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    saveBooleanPreferences(ConstantsCalculator.keysPreferences[1], newValue as Boolean)
                    Toast.makeText(context,resources.getString(R.string.system_restartMessage),Toast.LENGTH_LONG).show()
                    true
                }
            expandModePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    saveBooleanPreferences(ConstantsCalculator.keysPreferences[2], newValue as Boolean)
                    true
                }
        }

        private fun saveBooleanPreferences(key: String, variable: Boolean) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(key, variable)
            editor.apply()
        }
    }

}