package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterBinding
import com.cobaltumapps.simplecalculator.v15.converter.adapters.UnitItemsAdapter
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.ConverterNumpadFragment

class ConverterUnitFragment: Fragment() {
    private val binding by lazy { FragmentConverterBinding.inflate(layoutInflater) }
    private val converterNumpadFragment = ConverterNumpadFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.converterUnitRecycler.apply {
            adapter = UnitItemsAdapter(listOf())
            layoutManager = LinearLayoutManager(context)
        }

        parentFragmentManager.commit {
            add(binding.converterNumpadFrame.id, converterNumpadFragment, ConverterNumpadFragment.TAG)
        }
    }

    companion object {
        const val TAG = "UnitFragmentTag"
    }
}