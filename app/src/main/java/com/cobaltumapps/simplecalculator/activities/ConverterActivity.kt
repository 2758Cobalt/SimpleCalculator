package com.cobaltumapps.simplecalculator.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitConverter
import com.cobaltumapps.simplecalculator.fragments.ConverterNumpadFragment
import com.google.android.material.navigation.NavigationView

class ConverterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var fragmentManager : FragmentManager
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var drawerMenuButton: ImageButton

    // Конвертеры величин
    private var unitConverter = UnitConverter()


    // Создаём клавиатуру
    private var converterNumpad = ConverterNumpadFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        fragmentManager = supportFragmentManager

        // Размещаем клавиатуру numpad для конвертера
        fragmentManager.beginTransaction().replace(R.id.numpadContainer, converterNumpad).commit()

        drawerLayout = findViewById(R.id.drawerLayout)
        drawerMenuButton = findViewById(R.id.drawerMenuButton)

        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        drawerMenuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setCheckedItem(R.id.menu_weight)

        converterNumpad.setNumpadListener(unitConverter)

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, unitConverter).commit()
        supportActionBar?.title = unitConverter.title

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_weight -> {
            }
//            R.id.menu_length -> switchConverter(lengthConverter)
//            R.id.menu_time -> switchConverter(timeConverter)
//
//            R.id.menu_speed -> switchConverter(speedConverter)
//            R.id.menu_temperature -> switchConverter(temperatureConverter)
//            R.id.menu_volume -> switchConverter(volumeConverter)
//
//            R.id.menu_area -> switchConverter(areaConverter)
//            R.id.menu_frequency -> switchConverter(frequencyConverter)
//            R.id.menu_data -> switchConverter(dataConverter)

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }

}