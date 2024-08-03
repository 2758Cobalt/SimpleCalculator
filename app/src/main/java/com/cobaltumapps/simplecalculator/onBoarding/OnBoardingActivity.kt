package com.cobaltumapps.simplecalculator.onBoarding

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.cobaltumapps.simplecalculator.R

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var nextButton: Button
    private lateinit var skipButton: Button

    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        var pageList: List<ViewPagerFragment> = listOf()

        // Картинки для getStarted
        val thumbnailsResources = resources.obtainTypedArray(R.array.getStarted_stickers)

        // Заголовок для getStarted
        val startedTitles = resources.getStringArray(R.array.getStarted_titles).toList()

        // Подзаголовок для getStarted
        val startedSubTitles = resources.getStringArray(R.array.getStarted_subTitles).toList()

        for (index in startedTitles.indices){
            val newFragment = ViewPagerFragment()
            newFragment.setNewContent(
                onBoardingContent(
                thumbnailsResources.getResourceId(index,-1),
                startedTitles[index],
                startedSubTitles[index])
            )

            // Добавляет в массив созданный фрагмент
            pageList = pageList.plus(newFragment)

        }

        thumbnailsResources.recycle()

        nextButton = findViewById(R.id.nextButton)
        skipButton = findViewById(R.id.skipButton)

        viewPager2 = findViewById(R.id.getStartedPlace)

        val adapter = ViewPagerAdapter(pageList,supportFragmentManager,lifecycle)
        viewPager2.adapter = adapter

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if (position == pageList.size - 1){
                    nextButton.text = getString(R.string.buttonStart)
                    nextButton.setOnClickListener { finish() }
                }
                else{
                    nextButton.text = getString(R.string.buttonNext)
                    nextButton.setOnClickListener { viewPager2.currentItem = viewPager2.currentItem + 1 }
                }
            }
        })
        skipButton.setOnClickListener { finish() }

    }

}