package com.cobaltumapps.simplecalculator.onBoarding

import android.content.Context
import android.graphics.drawable.Drawable
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
    private var boardingContent: onBoardingContent? = null

    private lateinit var thumbnailView: ImageView

    private lateinit var titleView: TextView
    private lateinit var subTitleView: TextView
    private var stickerDrawable: Drawable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (boardingContent != null)
            stickerDrawable = ResourcesCompat.getDrawable(resources,boardingContent!!.thumbnailResourceId!!,requireContext().theme)
        else{
            boardingContent = onBoardingContent(null,"NonTitle","NonSubtitle")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)

        thumbnailView = view.findViewById(R.id.illustrationContent)
        titleView = view.findViewById(R.id.titleContent)
        subTitleView = view.findViewById(R.id.subTitleContent)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (stickerDrawable == null)
            thumbnailView.visibility = View.GONE
        else
            thumbnailView.setImageDrawable(stickerDrawable)

        titleView.text = boardingContent!!.title
        subTitleView.text = boardingContent!!.subtitle
    }


    fun setNewContent(content: onBoardingContent){
        boardingContent = content
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("onboarding_fragment_imgId", boardingContent!!.thumbnailResourceId!!)
        outState.putString("onboarding_fragment_title", boardingContent!!.title)
        outState.putString("onboarding_fragment_subtitle", boardingContent!!.subtitle)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        if (savedInstanceState != null){
            val restoredImgResource = savedInstanceState.getInt("onboarding_fragment_imgId",0)
            val restoredTitle = savedInstanceState.getString("onboarding_fragment_title","NonTitle1")
            val restoredSubtitle = savedInstanceState.getString("onboarding_fragment_subtitle","NonSubtitle1")
            boardingContent = onBoardingContent(restoredImgResource, restoredTitle, restoredSubtitle)
        }
        super.onViewStateRestored(savedInstanceState)
    }
}