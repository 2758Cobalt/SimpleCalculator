package com.cobaltumapps.simplecalculator.v15.converter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.LayoutSelectorListItemBinding
import com.cobaltumapps.simplecalculator.v15.converter.data.SelectorDataItem
import com.cobaltumapps.simplecalculator.v15.converter.interfaces.SelectorFragmentListener

class SelectorListAdapter(
    val context: Context,
    private val dataList: List<SelectorDataItem>,
    private val listener: SelectorFragmentListener? = null
): RecyclerView.Adapter<SelectorListAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val itemBinding: LayoutSelectorListItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(selectorData: SelectorDataItem) {
            with(selectorData) {
                itemBinding.apply {
                    selectorListItemTitle.text = context.getString(titleResId)
                    selectorListItemIcon.setImageDrawable(ResourcesCompat.getDrawable(context.resources, drawableResId, context.theme))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = LayoutSelectorListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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