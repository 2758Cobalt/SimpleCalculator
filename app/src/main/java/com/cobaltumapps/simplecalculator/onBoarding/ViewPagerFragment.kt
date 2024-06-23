package com.cobaltumapps.simplecalculator.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.R

class ViewPagerFragment: Fragment() {
    private var boardingContent = onBoardingContent()

    private lateinit var thumbnailView: ImageView

    private lateinit var titleView: TextView
    private lateinit var subTitleView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)

        thumbnailView = view.findViewById(R.id.illustrationContent)
        titleView = view.findViewById(R.id.titleContent)
        subTitleView = view.findViewById(R.id.subTitleContent)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thumbnailView.setImageDrawable(ResourcesCompat.getDrawable(resources,boardingContent.thumbnailResourceId,requireContext().theme))
        titleView.text = boardingContent.title
        subTitleView.text = boardingContent.subtitle
    }


    fun setNewContent(content: onBoardingContent){
        boardingContent = content
    }
}