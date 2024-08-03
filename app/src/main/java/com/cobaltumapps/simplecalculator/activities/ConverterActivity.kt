package com.cobaltumapps.simplecalculator.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.adapters.viewpagers.ConverterViewPagerAdapter
import com.cobaltumapps.simplecalculator.enums.ConverterTypes
import com.cobaltumapps.simplecalculator.fragments.converter.ConverterCalculatorFragment
import com.cobaltumapps.simplecalculator.fragments.converter.UnitConverterFragment
import com.cobaltumapps.simplecalculator.managers.MemoryManager
import com.cobaltumapps.simplecalculator.models.ConverterDataModel
import com.cobaltumapps.simplecalculator.references.ConvertersValues
import com.cobaltumapps.simplecalculator.references.IntentKeys
import com.google.android.material.navigation.NavigationView

class ConverterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var viewPager: ViewPager2
    private lateinit var fragmentManager : FragmentManager

    // Меню навигации
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var converterToolbar: androidx.appcompat.widget.Toolbar

    private lateinit var toggle: ActionBarDrawerToggle

    // Создание конвертера величин
    private var unitConverter = UnitConverterFragment()

    // Создание калькулятора для ввода преобразуемого значения
    private var converterCalculator = ConverterCalculatorFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        converterToolbar = findViewById(R.id.converterToolbar)
        setSupportActionBar(converterToolbar)

        fragmentManager = supportFragmentManager

        viewPager = findViewById(R.id.converterViewPager)

        drawerLayout = findViewById(R.id.drawerLayout)

        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        viewPager.adapter = ConverterViewPagerAdapter(this, listOf(unitConverter, converterCalculator))
        viewPager.offscreenPageLimit = 1

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
            }
        })

        converterCalculator.parentViewPager = viewPager
        converterCalculator.unitConverterReference = unitConverter

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, converterToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Создаём объект памяти и сохраняем туда значение из калькулятора
        val newMemory = MemoryManager()
        newMemory.save(intent.getDoubleExtra(IntentKeys.memoryKey,0.0))

        converterCalculator.setMemoryManager(newMemory)

        if (savedInstanceState == null){
            onNavigationItemSelected(navigationView.menu.getItem(0))
        }
    }

    fun openNavigationMenu(){
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private val navCurrentItemKey = "nav_item_key"
    private val calculatorUserInputKey = "calc_userInput_key"

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(navCurrentItemKey,navigationView.menu.getItem(navigationView.checkedItem!!.itemId).itemId)
        outState.putDouble(calculatorUserInputKey,converterCalculator.getUserInput())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        unitConverter = UnitConverterFragment()
        viewPager.adapter = ConverterViewPagerAdapter(this, listOf(unitConverter, converterCalculator))

        onNavigationItemSelected(navigationView.menu.getItem(savedInstanceState.getInt(navCurrentItemKey,0)))
        converterCalculator.setUserInput(savedInstanceState.getDouble(calculatorUserInputKey,0.0).toString())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.converter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.converterCalculator -> {
                viewPager.setCurrentItem(1,true)
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val dataModel = ConverterDataModel()

        when(item.itemId) {
            R.id.menu_weight -> {
                dataModel.title = resources.getString(R.string.converter_weight)
                dataModel.thumbnailResourceId = R.drawable.ic_weight
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.weight_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.weight_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.weightValues
            }

            R.id.menu_length -> {
                dataModel.title = resources.getString(R.string.converter_length)
                dataModel.thumbnailResourceId = R.drawable.ic_length
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.length_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.length_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.lengthValues
            }

            R.id.menu_time -> {
                dataModel.title = resources.getString(R.string.converter_time)
                dataModel.thumbnailResourceId = R.drawable.ic_time
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.time_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.time_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.timeValues
            }

            R.id.menu_speed -> {
                dataModel.title = resources.getString(R.string.converter_speed)
                dataModel.thumbnailResourceId = R.drawable.ic_speed
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.speed_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.speed_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.speedValues
            }

            R.id.menu_temperature -> {
                dataModel.title = resources.getString(R.string.converter_temperature)
                dataModel.thumbnailResourceId = R.drawable.ic_temperatures
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.temperature_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.temperature_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.temperatureValue
                dataModel.converterType = ConverterTypes.Temperature
            }

            R.id.menu_volume -> {
                dataModel.title = resources.getString(R.string.converter_volume)
                dataModel.thumbnailResourceId = R.drawable.ic_volume
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.volume_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.volume_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.volumeValues
            }

            R.id.menu_area -> {
                dataModel.title = resources.getString(R.string.converter_area)
                dataModel.thumbnailResourceId = R.drawable.ic_area
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.area_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.area_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.areaValues
            }
            R.id.menu_frequency -> {
                dataModel.title = resources.getString(R.string.converter_frequency)
                dataModel.thumbnailResourceId = R.drawable.ic_frequency
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.frequency_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.frequency_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.frequencyValues
            }

            R.id.menu_data -> {
                dataModel.title = resources.getString(R.string.converter_data)
                dataModel.thumbnailResourceId = R.drawable.ic_data
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.data_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.data_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.dataValues
            }

            else -> {
                dataModel.title = resources.getString(R.string.converter_weight)
                dataModel.thumbnailResourceId = R.drawable.ic_weight
                dataModel.converterUnitsNamesList = resources.getStringArray(R.array.weight_unit).toMutableList()
                dataModel.convertersSpecialSymbolsList = resources.getStringArray(R.array.weight_symbols).toList()
                dataModel.convertersValuesToConvert = ConvertersValues.weightValues
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        converterToolbar.title = dataModel.title
        unitConverter.setUserInput(0.0)
        unitConverter.setNewConverterModel(dataModel)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            if (viewPager.currentItem == 1) {
                viewPager.currentItem = 0
            }
            else {
                super.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        drawerLayout.removeDrawerListener(toggle)
        super.onDestroy()
    }

}