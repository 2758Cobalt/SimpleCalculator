package com.cobaltumapps.simplecalculator.ui.fragments.extra.calculators

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo
import com.cobaltumapps.simplecalculator.data.extra.constants.ConversionContext
import com.cobaltumapps.simplecalculator.databinding.FragmentExtraUnitCalculatorBinding
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit.UnitConversionContext
import com.cobaltumapps.simplecalculator.domain.viewmodel.ExtraUnitReviewerViewModel
import com.cobaltumapps.simplecalculator.domain.viewmodel.UnitCalculatorViewModel
import com.cobaltumapps.simplecalculator.ui.recycler.adapters.extra.ExtraUnitFeedAdapter
import com.cobaltumapps.simplecalculator.v15.services.InputDialog
import java.io.IOException

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
    private var enteredUserValue = 0f

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
                    showInputDialog(getString(R.string.extra_unit_calculator_entryDialigTitle).format(itemId.toString()))
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

    private fun calculate(userEntry: Float, selectedItemPos: Int) {
        val result = unitConversionContext.calculate(selectedCalculatorId, userEntry, selectedItemPos)
        Log.i("DebugTag", "Result calculation:\nPosition: $selectedItemPos\nResult: $result")

        val updatedInfo = updateUnitsValues(result)
        if (updatedInfo.isNotEmpty()) extraReviewerAdapter.updateAdapterData(updatedInfo)
    }

    private fun updateUnitsValues(valuesToUpdate: List<Number>): List<ExtraUnitInfo> {
        return extraReviewerAdapter.currentList.onEachIndexed { index, item ->

            item.unitValue = try {
                valuesToUpdate[index].toFloat()
            } catch (ex: IndexOutOfBoundsException) {
                Log.e("DebugTag", "FATAL: ${ex.localizedMessage}")
                0f
            } catch (ex: IOException) {
                Toast.makeText(requireContext(), "An unknown error occurred.", Toast.LENGTH_LONG).show()
                Log.e("DebugTag", "FATAL: ${ex.localizedMessage}")
                0f
            }

        }
    }

}