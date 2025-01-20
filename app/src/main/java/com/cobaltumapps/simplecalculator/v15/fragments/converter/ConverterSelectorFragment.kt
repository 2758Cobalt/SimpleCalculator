package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterSelectorBinding
import com.cobaltumapps.simplecalculator.v15.converter.adapters.UnitItemsAdapter

class ConverterSelectorFragment: Fragment() {
    private val binding by lazy { FragmentConverterSelectorBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectorListRecycler.apply {
            adapter = UnitItemsAdapter(listOf())
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        const val TAG = "UnitFragmentTag"
    }
}