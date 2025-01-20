package com.cobaltumapps.simplecalculator.v15.converter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerConverterUnitItemBinding
import com.cobaltumapps.simplecalculator.v15.converter.data.SelectorDataItem

class UnitItemsAdapter(private val dataList: List<SelectorDataItem>): RecyclerView.Adapter<UnitItemsAdapter.UnitItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitItemHolder {
        val binding = RecyclerConverterUnitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UnitItemHolder(binding)
    }

    override fun onBindViewHolder(holder: UnitItemHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class UnitItemHolder(private val itemBinding: RecyclerConverterUnitItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(selectorData: SelectorDataItem) {
        }
    }
}