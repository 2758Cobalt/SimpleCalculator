package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ActivityConverterBinding
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterData
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.services.ConverterModelCreatorService
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainConverterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val binding by lazy { ActivityConverterBinding.inflate(layoutInflater) }

    // Ad (Advertisement)
    private val adRequest by lazy { AdRequest.Builder().build() }

    private val converterFragment by lazy { ConverterFragment() }
    private val converterModelCreatorService = ConverterModelCreatorService(this)

    private lateinit var toggleButtonDrawer: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.converterToolbar)
        binding.converterNavigationView.setNavigationItemSelectedListener(this@MainConverterActivity)

        supportFragmentManager.commit {
            add(binding.converterViewHolder.id, converterFragment, ConverterFragment.TAG)
        }

        toggleButtonDrawer = ActionBarDrawerToggle(
            this@MainConverterActivity, binding.converterDrawer, binding.converterToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.converterDrawer.addDrawerListener(toggleButtonDrawer)
        toggleButtonDrawer.syncState()

        // Ad initialization
        lifecycleScope.launch(Dispatchers.Main) {
            MobileAds.initialize(this@MainConverterActivity) {
                binding.converterAdViewBanner.loadAd(adRequest)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Создаём креатор моделей конвертера
        val createdConverterData = when(item.itemId) {
            R.id.menu_weight -> getConverterData(ConverterType.Weight)
            R.id.menu_length -> getConverterData(ConverterType.Length)
            R.id.menu_time -> getConverterData(ConverterType.Time)
            R.id.menu_speed -> getConverterData(ConverterType.Speed)
            R.id.menu_temperature -> getConverterData(ConverterType.Temperature)
            R.id.menu_volume -> getConverterData(ConverterType.Volume)
            R.id.menu_area -> getConverterData(ConverterType.Area)
            R.id.menu_frequency -> getConverterData(ConverterType.Frequency)
            R.id.menu_data -> getConverterData(ConverterType.Data)
            else -> getConverterData(ConverterType.Weight)
        }

        converterFragment.setNewConverterData(createdConverterData)
        return true
    }

    override fun onResume() {
        super.onResume()
        binding.converterAdViewBanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.converterAdViewBanner.pause()
    }

    override fun onDestroy() {
        binding.converterAdViewBanner.destroy()
        binding.converterDrawer.removeDrawerListener(toggleButtonDrawer)
        super.onDestroy()
    }

    /** Возвращает данные для конвертера исходя из выбраного типа конвертера с помощью навигации */
    private fun getConverterData(converterType: ConverterType): ConverterData {
        return converterModelCreatorService.getConverterModel(converterType)
    }

    companion object {
        const val LOG_TAG = "MainConverterActivity" +
                "LogTag"
    }
}