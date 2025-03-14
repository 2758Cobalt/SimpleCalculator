package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterNavigationItemSelectedListener
import com.cobaltumapps.simplecalculator.v15.converter.adapters.ConverterUnitsCycleAdapter
import com.cobaltumapps.simplecalculator.v15.converter.adapters.OnAdapterSelectedItem
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterData
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.loader.ConverterInfoLoader
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoLoaderListener
import com.cobaltumapps.simplecalculator.v15.converter.loggers.ConverterPageLogger
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.MiniNumpadFragment

/** Фрагмент, который содержит общую информацию о конвертере */
class ConverterPageFragment: Fragment(), ConverterNavigationItemSelectedListener, InfoLoaderListener, OnAdapterSelectedItem {
    private lateinit var binding: FragmentConverterBinding

    // Instances
    private val converterPageLogger = ConverterPageLogger()
    private val miniNumpadFragment = MiniNumpadFragment()

    private var converterData = ConverterData()
    private var converterUnitsCycleAdapter = ConverterUnitsCycleAdapter(this@ConverterPageFragment)

    private lateinit var converterInfoLoader: ConverterInfoLoader

    private var selectedItemPos: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        converterInfoLoader = ConverterInfoLoader(requireContext())

        with(binding) {
            converterUnitRecycler.apply {
                adapter = converterUnitsCycleAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            parentFragmentManager.commit {
                replace(converterNumpadFrame.id, miniNumpadFragment, MiniNumpadFragment.FRAG_TAG)
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

    override fun updateConverterData(converterData: ConverterData) {
        this.converterData = converterData.also {
            converterUnitsCycleAdapter.setNewData(it.converterUnitsModel)
        }
        converterPageLogger.updateConverterData(converterData)
        if (::binding.isInitialized) updateDataFields()
    }

    private fun updateDataFields() {
        with(binding) {
            converterData.let {
                converterUnitTitle.text = it.converterPageData.title
                converterUnitThumbnail.setImageDrawable(it.converterPageData.drawable)
            }
        }
    }

    /** Срабатывает при выборе элемента */
    override fun selectedItemPosition(position: Int) {
        selectedItemPos = position
        Log.d(LOG_TAG, "Item selected position - $position")
    }

    companion object {
        const val FRAG_TAG = "ConverterPageFragmentTag"
        const val LOG_TAG = "ConverterPageFragmentLogTag"
    }
}
