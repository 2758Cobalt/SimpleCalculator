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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateFields()
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
}