package com.cobaltumapps.simplecalculator.ui.fragments.extra.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentExtraUnitCalculatorBinding
import com.cobaltumapps.simplecalculator.domain.viewmodel.ExtraUnitReviewerViewModel
import com.cobaltumapps.simplecalculator.domain.viewmodel.UnitCalculatorViewModel
import com.cobaltumapps.simplecalculator.ui.recycler.adapters.extra.ExtraUnitFeedAdapter
import com.cobaltumapps.simplecalculator.v15.services.InputDialog

/** Fragment for display list of calculators */
class ExtraUnitReviewerFragment: Fragment() {
    private val binding by lazy { FragmentExtraUnitCalculatorBinding.inflate(layoutInflater) }
    private val extraReviewerAdapter by lazy { ExtraUnitFeedAdapter(unitCalculatorViewModel) }

    private val extraUnitReviewerViewModel by activityViewModels<ExtraUnitReviewerViewModel>()
    private val unitCalculatorViewModel by activityViewModels<UnitCalculatorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.extraReviewerRecycler.apply {
            adapter = extraReviewerAdapter
        }

        extraUnitReviewerViewModel.selectedCalcId.observe(viewLifecycleOwner) { unitInfo ->
            extraReviewerAdapter.submitList(unitInfo)
        }

        unitCalculatorViewModel.apply {
            selectedItemPos.observe(viewLifecycleOwner) { itemPosition -> selectedItem(itemPosition) }

            onDialogCall.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let { itemId ->
                    showInputDialog(getString(R.string.extra_unit_calculator_entryDialigTitle).format(itemId.toString()))
                }
            }
        }

    }

    private fun showInputDialog(title: String) {
        InputDialog.showInputDialog(requireContext(), title) {
            unitCalculatorViewModel.onEnteredUnitValue(it) { pos, entry ->
                extraReviewerAdapter.updateValueItemByPos(pos, entry)
            }
        }
    }

    private fun selectedItem(itemPosition: Int) {
        extraReviewerAdapter.updateSelection(itemPosition)
    }

}