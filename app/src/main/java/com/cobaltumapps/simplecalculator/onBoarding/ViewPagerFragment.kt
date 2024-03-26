package com.cobaltumapps.simplecalculator.onBoarding

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.R

class ViewPagerFragment(private val context: Context, newOnBoardingContent: onBoardingContent): Fragment() {
    private var onBoardingContent: onBoardingContent = newOnBoardingContent

    private lateinit var thumbnailView: ImageView
    private lateinit var titleView: TextView
    private lateinit var subTitleView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        thumbnailView = view.findViewById(R.id.illustrationContent)
        titleView = view.findViewById(R.id.titleContent)
        subTitleView = view.findViewById(R.id.subTitleContent)

        Log.i("DebugTag","!Set new data: \nimage res: ${onBoardingContent.thumbnailResourceId}" +
                "\ntitle: ${onBoardingContent.title}" +
                "\nsubtitle: ${onBoardingContent.subTitle}")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thumbnailView.setImageDrawable(ResourcesCompat.getDrawable(resources,onBoardingContent.thumbnailResourceId,context.theme))
        titleView.text = onBoardingContent.title
        subTitleView.text = onBoardingContent.subTitle
    }
}