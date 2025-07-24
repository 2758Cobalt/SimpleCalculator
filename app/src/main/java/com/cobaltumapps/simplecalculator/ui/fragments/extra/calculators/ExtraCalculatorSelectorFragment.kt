package com.cobaltumapps.simplecalculator.ui.fragments.extra.calculators

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.data.extra.constants.ExtraCalculatorsKeys
import com.cobaltumapps.simplecalculator.data.extra.contracts.ExtraUnitCalculatorContract
import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.databinding.FragmentExtraSelectorBinding
import com.cobaltumapps.simplecalculator.domain.viewmodel.ExtraSelectorViewModel
import com.cobaltumapps.simplecalculator.ui.fragments.extra.calculators.interfaces.ExtraSelector
import com.cobaltumapps.simplecalculator.ui.recycler.adapters.extra.reviewer.ExtraSelectorAdapter

/** Fragment for selection an extra calculators */
class ExtraCalculatorSelectorFragment: Fragment(), ExtraSelector {
    private val binding by lazy { FragmentExtraSelectorBinding.inflate(layoutInflater) }

    private val extraSelectorUnitContract = registerForActivityResult(ExtraUnitCalculatorContract()) { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    private val extraSelectorAdapter = ExtraSelectorAdapter(this)
    private val extraSelectorViewModel by activityViewModels<ExtraSelectorViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.extraSelectorCalculatorsList.apply {
            adapter = extraSelectorAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        extraSelectorAdapter.submitList(extraSelectorViewModel.createCalculators())
    }

    override fun onSelectedItem(calculatorItem: ExtraSelectableCalculatorInfo) {
        when(calculatorItem.type) {
            ExtraCalculatorType.Unit -> extraSelectorUnitContract.launch(calculatorItem.calculatorId)
            else -> extraSelectorUnitContract.launch(ExtraCalculatorsKeys.CALC_UNIT_WEIGHT_ID)
        }
        Log.d("DebugTag", "picked item ${calculatorItem.title} type: ${calculatorItem.type}. ID: ${calculatorItem.calculatorId}")
    }
}