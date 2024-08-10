package com.cobaltumapps.simplecalculator.v15.calculator.services.memory

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.interfaces.MemorySharedStorage

class MemorySharedPreferences(
    private val memory: Memory,
    private val sharedPreferences: SharedPreferences
): MemorySharedStorage {
    private val sharedPreferencesKeys = ""

    override fun saveMemoryToStorage() {
        val editor = sharedPreferences.edit()
        editor.putFloat(sharedPreferencesKeys, memory.read())
        editor.apply()
    }

    override fun loadMemoryToStorage() {
        sharedPreferences.getFloat(sharedPreferencesKeys, 0f)
    }

    override fun updateMemoryInStorage() {
        val editor = sharedPreferences.edit()
        editor.putFloat(sharedPreferencesKeys, memory.read())
        editor.apply()
    }

    override fun removeMemoryInStorage() {
        val editor = sharedPreferences.edit()
        editor.remove(sharedPreferencesKeys)
        editor.apply()
    }
}