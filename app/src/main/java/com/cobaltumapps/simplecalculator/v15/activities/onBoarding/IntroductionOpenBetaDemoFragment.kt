package com.cobaltumapps.simplecalculator.v15.activities.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cobaltumapps.simplecalculator.databinding.FragmentIntroductionOpenbetaBinding

class IntroductionOpenBetaDemoFragment: IntroductionFragment() {
    private val binding by lazy { FragmentIntroductionOpenbetaBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}