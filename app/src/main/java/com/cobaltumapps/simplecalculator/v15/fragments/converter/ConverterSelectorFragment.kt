package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterSelectorBinding
import com.cobaltumapps.simplecalculator.v15.activities.ConverterPagerManager
import com.cobaltumapps.simplecalculator.v15.converter.adapters.ConverterSelectorAdapter
import com.cobaltumapps.simplecalculator.v15.converter.data.SelectorDataItem
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterUnit
import com.cobaltumapps.simplecalculator.v15.converter.interfaces.SelectorFragmentListener

class ConverterSelectorFragment(
    private val listener: ConverterPagerManager? = null,
    private val converterListener: ConverterUnitFragment? = null
): Fragment(), SelectorFragmentListener
{
    private val binding by lazy { FragmentConverterSelectorBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemsListview = listOf(
            SelectorDataItem(R.string.converter_weight, R.drawable.ic_weight, ConverterUnit.Weight),
            SelectorDataItem(R.string.converter_length, R.drawable.ic_length, ConverterUnit.Length),
            SelectorDataItem(R.string.converter_time, R.drawable.ic_time, ConverterUnit.Time),
            SelectorDataItem(R.string.converter_speed, R.drawable.ic_speed, ConverterUnit.Speed)
        )

        binding.selectorListRecycler.apply {
            adapter = ConverterSelectorAdapter(context, itemsListview, this@ConverterSelectorFragment)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onSelectedItem(selectedUnit: ConverterUnit) {
        listener?.goUnitConverter()
        converterListener?.onSelectedItem(selectedUnit)
    }
}

