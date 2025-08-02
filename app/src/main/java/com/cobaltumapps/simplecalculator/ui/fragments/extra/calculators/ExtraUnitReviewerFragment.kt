package com.cobaltumapps.simplecalculator.ui.fragments.extra.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo
import com.cobaltumapps.simplecalculator.data.extra.constants.context.ConversionContext
import com.cobaltumapps.simplecalculator.databinding.FragmentExtraUnitCalculatorBinding
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit.UnitConversionContext
import com.cobaltumapps.simplecalculator.domain.viewmodel.ExtraUnitReviewerViewModel
import com.cobaltumapps.simplecalculator.domain.viewmodel.UnitCalculatorViewModel
import com.cobaltumapps.simplecalculator.ui.recycler.adapters.extra.ExtraUnitFeedAdapter
import com.cobaltumapps.simplecalculator.v15.services.InputDialog
import java.io.IOException
import java.math.BigDecimal

/** Fragment for display list of calculators */
class ExtraUnitReviewerFragment: Fragment() {
    private val binding by lazy { FragmentExtraUnitCalculatorBinding.inflate(layoutInflater) }
    private val extraReviewerAdapter by lazy { ExtraUnitFeedAdapter(unitCalculatorViewModel) }

    private val extraUnitReviewerViewModel by activityViewModels<ExtraUnitReviewerViewModel>()
    private val unitCalculatorViewModel by activityViewModels<UnitCalculatorViewModel>()

    private val unitConversionContext = UnitConversionContext(ConversionContext.conversionContext)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    private var selectedCalculatorId: String = ""
    private var enteredUserValue = "0.0"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.extraReviewerRecycler.apply {
            adapter = extraReviewerAdapter
        }

        extraUnitReviewerViewModel.apply {
            selectedCalcId.observe(viewLifecycleOwner) { selectedCalculatorId = it }

            loadedCalcInfo.observe(viewLifecycleOwner) { unitInfo ->
                extraReviewerAdapter.submitList(unitInfo)
            }
        }

        unitCalculatorViewModel.apply {
            selectedItemPos.observe(viewLifecycleOwner) { itemPosition ->
                selectedItem(itemPosition)
                if (itemPosition >= 0) {
                    calculate(enteredUserValue, itemPosition)
                }
            }

            onDialogCall.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let { itemId ->

                    val unitName = extraUnitReviewerViewModel.loadedCalcInfo.value?.get(itemId)?.unitName ?: itemId.toString()
                    showInputDialog(getString(R.string.extra_unit_calculator_entryDialigTitle).format(unitName))
                }
            }

            onEnteredValue.observe(viewLifecycleOwner) { enteredUserValue = it }
        }

    }

    private fun showInputDialog(title: String) {
        InputDialog.showInputDialog(requireContext(), title, enteredUserValue) {
            unitCalculatorViewModel.onEnteredUnitValue(it) { pos, entry ->
                calculate(entry, pos)
            }
        }
    }

    private fun selectedItem(itemPosition: Int) {
        extraReviewerAdapter.updateAdapterItems(itemPosition)
    }

    private fun calculate(userEntry: String, selectedItemPos: Int) {
        val result = unitConversionContext.calculate(selectedCalculatorId, userEntry, selectedItemPos)

        val updatedInfo = updateUnitsValues(result)
        if (updatedInfo.isNotEmpty()) extraReviewerAdapter.updateAdapterData(updatedInfo)
    }

    private fun updateUnitsValues(valuesToUpdate: List<Number>): List<ExtraUnitInfo> {
        return extraReviewerAdapter.currentList.onEachIndexed { index, item ->

            item.unitValue = try {
                BigDecimal(valuesToUpdate[index].toString())

            } catch (ex: IndexOutOfBoundsException) {
                BigDecimal("0.0")

            } catch (ex: IOException) {
                Toast.makeText(requireContext(), "An unknown error occurred.", Toast.LENGTH_LONG).show()
                BigDecimal("0.0")
            }

        }
    }

}