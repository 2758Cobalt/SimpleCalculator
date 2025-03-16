package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterPageBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterNavigationItemSelectedListener
import com.cobaltumapps.simplecalculator.v15.converter.adapters.ConverterUnitsCycleAdapter
import com.cobaltumapps.simplecalculator.v15.converter.adapters.OnAdapterSelectedItem
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterNumpadController
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterLoaderData
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.loader.ConverterInfoLoader
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoLoaderListener
import com.cobaltumapps.simplecalculator.v15.converter.loggers.ConverterPageLogger
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.ConverterNumpadFragment
import com.cobaltumapps.simplecalculator.v15.references.LogTags

/** Фрагмент, который содержит общую информацию о конвертере */
class ConverterPageFragment: Fragment(), ConverterNavigationItemSelectedListener, InfoLoaderListener, OnAdapterSelectedItem {
    private lateinit var binding: FragmentConverterPageBinding

    // Instances
    private val converterPageLogger = ConverterPageLogger()
    private val converterNumpadController = ConverterNumpadController()

    private var converterLoaderData = ConverterLoaderData()
    private var converterUnitsCycleAdapter = ConverterUnitsCycleAdapter(this@ConverterPageFragment)

    private lateinit var converterInfoLoader: ConverterInfoLoader
    private lateinit var converterNumpadFragment: ConverterNumpadFragment

    private var selectedItemPos: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        converterInfoLoader = ConverterInfoLoader(requireContext())
        converterNumpadFragment = ConverterNumpadFragment(converterNumpadController)

        with(binding) {
            converterUnitRecycler.apply {
                adapter = converterUnitsCycleAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            parentFragmentManager.commit {
                replace(converterNumpadFrame.id, converterNumpadFragment, ConverterNumpadFragment.FRAG_TAG)
            }

        }

        // Default selected item
        onConverterNavigationItemSelected(ConverterType.Weight)
    }

    /** Срабатывает при выборе конвертера в меню */
    override fun onConverterNavigationItemSelected(converterType: ConverterType) {
        updateConverterData(
            converterInfoLoader.getConverterData(converterType)
        )
    }

    override fun updateConverterData(converterLoaderData: ConverterLoaderData) {
        this.converterLoaderData = converterLoaderData.also {
            converterUnitsCycleAdapter.setNewData(it.converterUnitsModel)
        }
        converterPageLogger.updateConverterData(converterLoaderData)
        if (::binding.isInitialized) updatePageDataFields()
    }

    private fun updatePageDataFields() {
        with(binding) {
            converterLoaderData.let {
                converterUnitTitle.text = it.converterPageData.title
                converterUnitThumbnail.setImageDrawable(it.converterPageData.drawable)
            }
        }
    }

    /** Срабатывает при выборе элемента */
    override fun selectedItemPosition(position: Int) {
        selectedItemPos = position
        Log.d(LogTags.LOG_CONVERTER_PAGE_FRAGMENT, "Item selected position - $position")
    }

    companion object {
        const val FRAG_TAG = "ConverterPageFragmentTag"
    }
}
