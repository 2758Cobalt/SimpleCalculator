package com.cobaltumapps.simplecalculator.v15.converter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerSelectorItemBinding
import com.cobaltumapps.simplecalculator.v15.converter.data.SelectorDataItem
import com.cobaltumapps.simplecalculator.v15.converter.interfaces.SelectorFragmentListener

class ConverterSelectorAdapter(
    val context: Context,
    private val dataList: List<SelectorDataItem>,
    private val listener: SelectorFragmentListener? = null
): RecyclerView.Adapter<ConverterSelectorAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val itemBinding: RecyclerSelectorItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(selectorData: SelectorDataItem) {
            with(selectorData) {
                itemBinding.apply {
                    selectorListItemTitle.text = context.getString(titleResId)
                    selectorItemIcon.setIconResource(drawableResId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RecyclerSelectorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener?.onSelectedItem(item.unitType)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}