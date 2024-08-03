package com.cobaltumapps.simplecalculator.abstractClasses

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.references.LogTags

/* @title - Заголовок конвертера
*  @thumbnailResource - Ресурс иконки которая отображаться в конвертере
*/

abstract class Converter: Fragment() {
    var title: String = "UnitRecyclerModel"

    var iconResource: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Проверяем индекс иконки
        if (iconResource != null) Log.d(LogTags.LOG_CONVERTER,"Thumbnail id:$iconResource of ${javaClass}")
        else Log.e(LogTags.LOG_CONVERTER,"Thumbnail is null :${javaClass}")
    }

}

