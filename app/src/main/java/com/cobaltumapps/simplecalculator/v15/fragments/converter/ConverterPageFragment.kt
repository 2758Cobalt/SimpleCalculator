package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterPageBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterNavigationItemSelectedListener
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ConverterPageNavigationListener
import com.cobaltumapps.simplecalculator.v15.converter.adapters.ConverterUnitsCycleAdapter
import com.cobaltumapps.simplecalculator.v15.converter.adapters.OnAdapterSelectedItem
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterLoaderData
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType
import com.cobaltumapps.simplecalculator.v15.converter.loader.ConverterInfoLoader
import com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces.InfoLoaderListener
import com.cobaltumapps.simplecalculator.v15.converter.mediator.ConverterMediator
import com.cobaltumapps.simplecalculator.v15.fragments.converter.interfaces.ConverterCalculatorListener

/** Фрагмент, который содержит общую информацию о конвертере */
class ConverterPageFragment(private val mediator: ConverterMediator): Fragment(),
    ConverterNavigationItemSelectedListener, InfoLoaderListener, OnAdapterSelectedItem,
    ConverterCalculatorListener
{
    private lateinit var binding: FragmentConverterPageBinding

    var navigationListener: ConverterPageNavigationListener? = null

    // Instances
    private var converterLoaderData = ConverterLoaderData()
    private var converterUnitsCycleAdapter = ConverterUnitsCycleAdapter(this@ConverterPageFragment)

    private lateinit var converterInfoLoader: ConverterInfoLoader

    init {
        mediator.pageFragmentInstance = this@ConverterPageFragment
    }

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

        with(binding) {
            converterUnitRecycler.apply {
                adapter = converterUnitsCycleAdapter
                layoutManager = LinearLayoutManager(requireContext())

                setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                    if (scrollY > oldScrollY) {
                        converterFAB.hide()
                    }
                    else if (scrollY < oldScrollY) {
                        converterFAB.show()
                    }
                }
            }

            converterFAB.setOnClickListener {
                navigationListener?.onClickedCalculatorButton()
            }

        }

        // Default selected item
        onConverterNavigationItemSelected(ConverterType.Weight)
    }

    /** Срабатывает при выборе конвертера в меню */
    override fun onConverterNavigationItemSelected(converterType: ConverterType) {
        updateConverterData(converterInfoLoader.getConverterData(converterType))
    }

    override fun updateConverterData(converterLoaderData: ConverterLoaderData) {
        this.converterLoaderData = converterLoaderData.also {
            converterUnitsCycleAdapter.setNewData(it.converterUnitsModel)
        }

        mediator.updateConverterData(converterLoaderData)
    }

    /** Срабатывает при выборе элемента */
    override fun selectedItemPosition(position: Int) {
        mediator.selectedItemPosition(position)
    }

    override fun listenCalculatedResults(results: Array<Number>) {
        converterUnitsCycleAdapter.setNewResults(results)
    }

    companion object {
        const val FRAG_TAG = "ConverterPageFragmentTag"
    }

}