package com.cobaltumapps.simplecalculator.converterNavigation

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.references.LogTags

/* @title - Заголовок конвертера
*  @thumbnailResource - Ресурс иконки которая отображаться в конвертере
*/

abstract class Converter: Fragment() {
    var title: String = "UnitModel"

    var thumbnailResource: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Проверяем индекс иконки
        if (thumbnailResource != null) Log.d(LogTags.LOG_CONVERTER,"Thumbnail id:$thumbnailResource of ${javaClass}")
        else Log.e(LogTags.LOG_CONVERTER,"Thumbnail is null :${javaClass}")
    }

}

