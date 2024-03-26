package com.cobaltumapps.simplecalculator.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.onBoarding.ViewPagerAdapter
import com.cobaltumapps.simplecalculator.onBoarding.ViewPagerFragment
import com.cobaltumapps.simplecalculator.onBoarding.onBoardingContent

class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        var pageList: List<ViewPagerFragment> = listOf()

        // Картинки для getStarted
        val thumbnailsResources = listOf(
            R.drawable.sticker_calculator)

        // Заголовок для getStarted
        val startedTitles = resources.getStringArray(R.array.getStarted_titles).toList()

        // Подзаголовок для getStarted
        val startedSubTitles = resources.getStringArray(R.array.getStarted_subTitles).toList()

        for (index in startedTitles.indices){
            val newFragment = ViewPagerFragment(this, onBoardingContent(
                thumbnailsResources[index],
                startedTitles[index],
                startedSubTitles[index]) )

            // Добавляет в массив созданный фрагмент
            pageList = pageList.plus(newFragment)

        }

        val nextButton = findViewById<Button>(R.id.getStartButton)

        val viewPager2 = findViewById<ViewPager2>(R.id.getStartedPlace)

        val adapter = ViewPagerAdapter(pageList,supportFragmentManager,lifecycle)
        viewPager2.adapter = adapter

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if (position == pageList.size - 1){
                    nextButton.text = "start"
                    nextButton.setOnClickListener { finish() }
                }
                else{
                    nextButton.text = "next"
                    nextButton.setOnClickListener { viewPager2.currentItem = viewPager2.currentItem + 1 }
                }
            }
        })

    }
}