package com.cobaltumapps.simplecalculator.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.cobaltumapps.simplecalculator.R


class UpdateBoardDialogFragment: DialogFragment() {
    private lateinit var appNewVersion: TextView
    private lateinit var updateNotes: TextView

    private var releaseNotes: List<String> = listOf()

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val windowParams = window?.attributes
        windowParams?.dimAmount = 0.3f
        window?.attributes = windowParams

        // Установите ширину диалога как процент от ширины экрана, например, 80%
        val displayMetrics = context?.resources?.displayMetrics
        val percentageWidth = (displayMetrics!!.widthPixels * 0.8).toInt()
        windowParams?.width = percentageWidth

        window?.attributes = windowParams


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.updateboard_dialog, container, false)

        releaseNotes = resources.getStringArray(R.array.updateBoard_news).toList()

        appNewVersion = view.findViewById(R.id.newVersionApp)
        updateNotes = view.findViewById(R.id.updateNotes)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawableResource(R.drawable.shape_gradient_dialog)
        }

        updateNotes.text = ""
        for (note in releaseNotes){
            updateNotes.append("• $note\n")
        }

        appNewVersion.text = getString(R.string.updateBoard_appVersion) + ": " + context?.packageManager!!.getPackageInfo(context?.packageName!!,0).versionName.toString()


        return view
    }



    companion object {
        const val TAG = "UpdateBoardDialogTag"
    }
}