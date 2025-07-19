package com.cobaltumapps.simplecalculator.v15.services

import android.content.Context
import android.view.LayoutInflater
import com.cobaltumapps.simplecalculator.databinding.DialogEnterManuallyBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object InputDialog {
    fun showInputDialog(context: Context, title: String = "Title", onSubmitValue: (Float) -> Unit) {
        val binding = DialogEnterManuallyBinding.inflate(LayoutInflater.from(context))

        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setView(binding.root)
            .setPositiveButton("Accept") { dialog, _ ->
                onSubmitValue(binding.inputDialogForm.text.toString().toFloat())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showInputDialog(context: Context, title: String = "Title", defaultValue: Number, onSubmitValue: (Float) -> Unit) {
        val binding = DialogEnterManuallyBinding.inflate(LayoutInflater.from(context))

        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setView(binding.root)
            .setPositiveButton("Accept") { dialog, _ ->
                onSubmitValue(binding.inputDialogForm.text.toString().toFloat())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

        binding.inputDialogForm.setText(defaultValue.toString())
    }
}