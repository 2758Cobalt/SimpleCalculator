package com.cobaltumapps.simplecalculator.v15.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.activities.CalculatorListener
import com.cobaltumapps.simplecalculator.activities.ConverterActivity
import com.cobaltumapps.simplecalculator.databinding.ActivityMainCalculatorBinding
import com.cobaltumapps.simplecalculator.onBoarding.OnBoardingActivity
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.PreferencesManager
import com.cobaltumapps.simplecalculator.v15.constants.UserPreferences
import com.cobaltumapps.simplecalculator.v15.fragments.calculator.CalculatorFragmentN

class MainCalculatorActivity : AppCompatActivity(), CalculatorListener {
    private val binding by lazy { ActivityMainCalculatorBinding.inflate(layoutInflater) }
    private lateinit var sharedPreferences: SharedPreferences

    private val calculator: CalculatorFragmentN = CalculatorFragmentN(this)
    private val preferencesManager by lazy { PreferencesManager(this) }

    companion object {
        private const val KEYSTORE_BOARDING = "SC_MainActivity_key"
        private const val BOARDING_FORCE_LAUNCH = false // Игнорирует статус показа и запускает стартовый экран (для тестов)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setBackgroundDrawable(null)

        supportFragmentManager.commit {
            replace(R.id.placeHolder, calculator)
        }

        // Получаем хранилище
        sharedPreferences = this.getSharedPreferences(ConstantsCalculator.vault, Context.MODE_PRIVATE)

        // Проверяем был ли показан стартовый экран
        if (!BOARDING_FORCE_LAUNCH) {
            if (!sharedPreferences.getBoolean(KEYSTORE_BOARDING, false)){
                startActivity(Intent(this@MainCalculatorActivity, OnBoardingActivity::class.java))

                val editor = sharedPreferences.edit()
                editor.putBoolean(KEYSTORE_BOARDING, true)
                editor.apply()
            }
        }
        else
            startActivity(Intent(this@MainCalculatorActivity, OnBoardingActivity::class.java))

        calculator.updatePreferences(UserPreferences())
    }


    override fun goConverters() {
        val intent = Intent(this, ConverterActivity::class.java)
        startActivity(intent)
    }

    override fun goSettings() {
        preferencesManager.openPreferencesService()
    }
}