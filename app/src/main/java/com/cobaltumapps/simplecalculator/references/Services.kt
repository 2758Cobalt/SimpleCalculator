package com.cobaltumapps.simplecalculator.references

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.cobaltumapps.simplecalculator.R

object Services {

    // Копирует в буфер обмена
    fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context,context.resources.getText(R.string.system_copyToClipboard),Toast.LENGTH_SHORT).show()
    }

}