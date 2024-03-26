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
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.references.Services

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
        private var oldSharedPreference: Array<Any> = arrayOf()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            sharedPreferences = requireContext().getSharedPreferences(ConstantsCalculator.vault,Context.MODE_PRIVATE)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ConstantsCalculator.privacyPolicyLink))
            val intentTelegram = Intent(Intent.ACTION_VIEW, Uri.parse(ConstantsCalculator.telegramLink))

            val autoSavePreference = findPreference<CheckBoxPreference>(ConstantsCalculator.keysPreferences[0]) as CheckBoxPreference
            val leftHandModePreference = findPreference<SwitchPreference>(ConstantsCalculator.keysPreferences[1]) as SwitchPreference
            val miniModePreference = findPreference<SwitchPreference>(ConstantsCalculator.keysPreferences[2]) as SwitchPreference
            val vibrationPreference = findPreference<SwitchPreference>(ConstantsCalculator.keysPreferences[3]) as SwitchPreference

            val privacyPolicy = findPreference<Preference>("key_privacyPolicy")
            val supportTelegram = findPreference<Preference>("key_telegram")

            oldSharedPreference = arrayOf(
                sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[0], false),
                sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[1], false),
                sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[2], false),
                sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[3], false)
            )

            // Назначает настройки из хранилища
            autoSavePreference.isChecked = oldSharedPreference[0] as Boolean
            leftHandModePreference.isChecked = oldSharedPreference[1] as Boolean
            miniModePreference.isChecked = oldSharedPreference[2] as Boolean
            vibrationPreference.isChecked = oldSharedPreference[3] as Boolean

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
            miniModePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    saveBooleanPreferences(ConstantsCalculator.keysPreferences[2], newValue as Boolean)
                    true
                }

            vibrationPreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    if (newValue as Boolean)
                        Services.playVibration(requireContext(),5)

                    saveBooleanPreferences(ConstantsCalculator.keysPreferences[3], newValue)
                    true
                }

            privacyPolicy?.setOnPreferenceClickListener { startActivity(intent); true }
            supportTelegram?.setOnPreferenceClickListener { startActivity(intentTelegram); true }
        }

        private fun saveBooleanPreferences(key: String, variable: Boolean) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(key, variable)
            editor.apply()
        }


    }

}