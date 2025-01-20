package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterBinding
import com.cobaltumapps.simplecalculator.v15.converter.adapters.ConverterUnitsAdapter
import com.cobaltumapps.simplecalculator.v15.converter.adapters.OnAdapterSelectedItem
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterData
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitsModel
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.ConverterNumpadFragment

/** Клас, отображающий список конвертеров с помощью адаптера, где каждый элемент - конвертер. */
class ConverterFragment: Fragment(), OnAdapterSelectedItem {
    private val binding by lazy { FragmentConverterBinding.inflate(layoutInflater) }
    private val converterNumpadFragment = ConverterNumpadFragment()

    private var selectedItemPos = 0
    private var converterData: ConverterData ?= null

    private lateinit var converterUnitsAdapter: ConverterUnitsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        converterUnitsAdapter = ConverterUnitsAdapter(ConverterUnitsModel())

        binding.converterUnitRecycler.apply {
            adapter = converterUnitsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        parentFragmentManager.commit {
            add(binding.converterNumpadFrame.id, converterNumpadFragment, ConverterNumpadFragment.TAG)
        }
    }

    /** Устанавливает новый набор данных для фрагмента конвертера*/
    fun setNewConverterData(newConverterData: ConverterData) {
        this.converterData = newConverterData
        converterUnitsAdapter.setNewData(newConverterData.converterUnitsModel)
        setConverterData()
    }

    private fun setConverterData() {
        binding.converterUnitTitle.text = converterData?.title
        binding.converterUnitThumbnail.setImageDrawable(converterData?.drawable)
    }

    /** Запоминаем позицию выбраного элемента */
    override fun selectedItemPosition(position: Int) {
        selectedItemPos = position
    }

    companion object {
        const val TAG = "UnitFragmentTag"
    }
}