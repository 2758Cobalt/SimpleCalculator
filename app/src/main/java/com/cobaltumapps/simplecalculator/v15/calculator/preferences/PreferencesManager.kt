package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.Context
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesDataManagerListener
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesUpdaterListener
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.sidesheet.PreferencesSideSheetDialog

/**Основной менеджер, отвечающий за работу с данными предпочтений*/
class PreferencesManager(context: Context): PreferencesDataManagerListener {
    private val sharedPreferences by lazy { context.getSharedPreferences(ConstantsCalculator.vault, Context.MODE_PRIVATE) }

    private val sideSheetDialog = PreferencesSideSheetDialog(context)

    private val preferencesDataManager = PreferencesDataManager(sharedPreferences)

    private var updateListeners: MutableList<PreferencesUpdaterListener> = mutableListOf()

    init {
        // При показе диалога
        sideSheetDialog.setOnShowListener {
            sideSheetDialog.loadConfig(loadData())
        }

        // При отклонении диалога
        sideSheetDialog.setOnDismissListener {
            saveData(sideSheetDialog.preferencesUserData)
            callListeners(loadData())
        }
    }

    fun addUpdateListener(newListener: PreferencesUpdaterListener) {
        updateListeners.add(newListener)
    }

    override fun loadData(): PreferencesUserData {
        val loadedData =  preferencesDataManager.loadData()
        callListeners(loadedData)
        return loadedData
    }

    override fun saveData(newData: PreferencesUserData) {
        preferencesDataManager.saveData(newData)
    }

    private fun callListeners(updateData: PreferencesUserData) {
        updateListeners.forEach { it.updatePreferences(updateData) }
    }

    fun openPreferencesDialog() {
        sideSheetDialog.show()
    }

    fun closePreferencesDialog() {
        sideSheetDialog.dismiss()
    }
}