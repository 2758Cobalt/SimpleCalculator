package com.cobaltumapps.simplecalculator.v15.converter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerConverterUnitItemBinding
import com.cobaltumapps.simplecalculator.v15.converter.adapters.holders.ConverterUnitViewHolder
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitModel
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitsModel

/** Адаптер отображающий список элементов конвертеров, где каждый элемент - конвертер*/
class ConverterUnitsAdapter(private var dataList: ConverterUnitsModel): RecyclerView.Adapter<ConverterUnitViewHolder>(), OnAdapterSelectedItem {
    var listener: OnAdapterSelectedItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterUnitViewHolder {
        val binding = RecyclerConverterUnitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConverterUnitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConverterUnitViewHolder, position: Int) {
        val unitTitle = dataList.unitsNameList[position]

        // Если спец. символ отсувствует - то вместо него просто печатается первая буква названия
        val specialSymbol =
            try {
                dataList.unitsSpecialSymbolsList[position]
            } catch (ex: IndexOutOfBoundsException) {
                unitTitle.first().uppercase()
            }

        holder.bind(
            ConverterUnitModel(unitTitle, specialSymbol)
        )

        holder.itemView.setOnClickListener {
            selectedItemPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.unitsNameList.size
    }

    /** При нажатии на элемент - слушателю отправляется позиция выбраного элемента */
    override fun selectedItemPosition(position: Int) {
        listener?.selectedItemPosition(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(newData: ConverterUnitsModel) {
        this.dataList = newData
        notifyDataSetChanged()
    }
}

