package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.Context
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesDataManagerListener
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesUpdaterListener
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.sidesheet.PreferencesSideSheetDialog

/**Основной менеджер, отвечающий за работу с данными предпочтений*/
class PreferencesManager(context: Context): PreferencesDataManagerListener {
    private val sharedPreferences by lazy { context.getSharedPreferences(ConstantsCalculator.vault,Context.MODE_PRIVATE) }

    private val sideSheetDialog = PreferencesSideSheetDialog(context)

    val preferencesDataManager = PreferencesDataManager(sharedPreferences)

    var updaterListener: PreferencesUpdaterListener? = null

    init {
        sideSheetDialog.setOnShowListener {
            sideSheetDialog.loadConfig(loadData())
        }

        sideSheetDialog.setOnDismissListener {
            saveData(sideSheetDialog.preferencesUserData)
            updaterListener?.updatePreferences(sideSheetDialog.preferencesUserData)
        }
    }

    override fun loadData(): PreferencesUserData {
        return preferencesDataManager.loadData()
    }

    override fun saveData(newData: PreferencesUserData) {
        preferencesDataManager.saveData(newData)
    }

    fun openPreferencesDialog() {
        sideSheetDialog.show()
    }

    fun closePreferencesDialog() {
        sideSheetDialog.dismiss()
    }
}