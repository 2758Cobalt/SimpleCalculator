package com.cobaltumapps.simplecalculator.interfaces

import android.view.View
import com.cobaltumapps.simplecalculator.adapters.ConverterRecyclerAdapter

interface OnAdapterItemClickListener : NumpadListener {
    fun onItemClick(holder: ConverterRecyclerAdapter.ConverterHolder, view: View, name: String, position: Int)
}