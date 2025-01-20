package com.cobaltumapps.simplecalculator.v15.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ActivityConverterBinding
import com.cobaltumapps.simplecalculator.v15.fragments.converter.ConverterUnitFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainConverterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val binding by lazy { ActivityConverterBinding.inflate(layoutInflater) }

    // Ad (Advertisement)
    private val adRequest by lazy { AdRequest.Builder().build() }

    private val converterUnitFragment by lazy { ConverterUnitFragment() }

    private lateinit var toggleButtonDrawer: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.converterToolbar)

        supportFragmentManager.commit {
            add(binding.converterViewHolder.id, converterUnitFragment, ConverterUnitFragment.TAG)
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
        when(item.itemId) {
            R.id.menu_weight -> {  }
            else -> {}
        }
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

    companion object {
        const val LOG_TAG = "MainConverterActivity" +
                "LogTag"
    }
}