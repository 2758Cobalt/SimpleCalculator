package com.cobaltumapps.simplecalculator.ui.activities.extra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ActivityNewsUpdateBinding

/** Активность, отображающая информацию обновления для приложения */
class NewsUpdateActivity : AppCompatActivity() {
    private val binding by lazy { ActivityNewsUpdateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.updatesToolbar)

        val listNewsUpdates = resources.getStringArray(R.array.update_news).toList()

        binding.newsUpdatesList.text = ""
        listNewsUpdates.forEach { textString ->
            binding.newsUpdatesList.append("$BALLET_SYMBOL $textString\n")
        }
    }

    companion object {
        const val BALLET_SYMBOL = "•"
    }
}