package com.cobaltumapps.simplecalculator.v15.services

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.cobaltumapps.simplecalculator.R

object ClipboardService {

    // Копирует в буфер обмена
    fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context,context.resources.getText(R.string.system_copyToClipboard),Toast.LENGTH_SHORT).show()
    }

}