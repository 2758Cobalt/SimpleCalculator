package com.cobaltumapps.simplecalculator.v15.fragments.history

import android.content.Context
import com.cobaltumapps.simplecalculator.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CalculatorHistoryDisplayAlerts(private var context: Context) {

    fun alertHistoryCleaning(onAccepted: () -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.display_history_dialogAlertTitle)
            .setMessage(R.string.display_history_clearAllHistory_dialogMessage)
            .setNeutralButton(R.string.display_history_dialog_cancel)  { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.display_history_dialog_accept) { _, _ ->
                onAccepted.invoke()
            }
            .show()
    }

    fun alertStoreToArchive(onAccepted: () -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.display_history_dialogAlertTitle)
            .setMessage(R.string.display_history_storeToArchive_dialogMessage)
            .setNeutralButton(R.string.display_history_dialog_cancel)  { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.display_history_dialog_accept) { _, _ ->
                onAccepted.invoke()
            }
            .show()
    }
}