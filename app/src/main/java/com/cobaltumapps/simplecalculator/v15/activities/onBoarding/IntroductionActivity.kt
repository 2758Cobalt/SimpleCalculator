package com.cobaltumapps.simplecalculator.v15.activities.onBoarding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ActivityIntroductionBinding
import com.cobaltumapps.simplecalculator.onBoarding.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class IntroductionActivity : AppCompatActivity() {
    private val binding by lazy { ActivityIntroductionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Картинки для getStarted
        val thumbnailsResources = resources.obtainTypedArray(R.array.getStarted_stickers)

        // Заголовок для getStarted
        val startedTitles = resources.getStringArray(R.array.getStarted_titles).toList()

        // Подзаголовок для getStarted
        val startedSubTitles = resources.getStringArray(R.array.getStarted_subTitles).toList()

        val boardingModelsList = mutableListOf<IntroductionModel>()

        for (index in startedTitles.indices) {
            boardingModelsList.add(
                IntroductionModel(
                    thumbnailsResources.getResourceId(index, -1),
                    startedTitles[index],
                    startedSubTitles[index]
                )
            )
        }

        thumbnailsResources.recycle()

        val createdFragmentsList = mutableListOf<IntroductionFragment>()

        boardingModelsList.forEach { model ->
            val fragment = IntroductionFragment()
            fragment.setNewBoardingModel(model)

            createdFragmentsList.add(
                fragment
            )
        }

        binding.boardingViewPager.apply {
            adapter = ViewPagerAdapter(createdFragmentsList, supportFragmentManager, lifecycle)
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        if (position == createdFragmentsList.size - 1) {
                            binding.boardingNextBtn.text = getString(R.string.buttonStart)
                            binding.boardingNextBtn.setOnClickListener { finish() }
                        }
                        else {
                            binding.boardingNextBtn.text = getString(R.string.buttonNext)
                            binding.boardingNextBtn.setOnClickListener { this@apply.currentItem = this@apply.currentItem + 1 }
                        }
                    }
                })
        }

        binding.boardingSkipBtn.setOnClickListener {
            finish()
        }

        TabLayoutMediator(binding.introTabIndicator, binding.boardingViewPager){ _, _ -> }.attach()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }
}