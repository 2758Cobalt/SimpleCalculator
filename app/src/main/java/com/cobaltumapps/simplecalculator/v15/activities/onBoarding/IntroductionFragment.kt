package com.cobaltumapps.simplecalculator.v15.activities.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentIntroductionBinding

class IntroductionFragment: Fragment() {
    private val binding by lazy { FragmentIntroductionBinding.inflate(layoutInflater) }
    private var boardingModel: IntroductionModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null)
            updateFields()
        else {
            binding.titleContent.text = savedInstanceState.getString(STATE_TITLE_KEY, "N/A")
            binding.subtitleContent.text = savedInstanceState.getString(STATE_SUBTITLE_KEY, "N/A ST")
        }
    }

    fun setNewBoardingModel(boardingModel: IntroductionModel) {
        this.boardingModel = boardingModel
    }

    private fun updateFields() {
        if (boardingModel != null) {
            binding.illustrationContent.setImageDrawable(ResourcesCompat.getDrawable(resources, boardingModel!!.thumbnailDrawable, requireContext().theme))
            binding.titleContent.text = boardingModel?.title
            binding.subtitleContent.text = boardingModel?.subtitle
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_TITLE_KEY, binding.titleContent.text.toString())
        outState.putString(STATE_SUBTITLE_KEY, binding.subtitleContent.text.toString())
        super.onSaveInstanceState(outState)
    }
    companion object {
        const val STATE_TITLE_KEY = "SC_TestKey"
        const val STATE_SUBTITLE_KEY = "SC_TestKey1"
    }
}