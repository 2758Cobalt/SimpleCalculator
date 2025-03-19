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
class ConverterUnitsCycleAdapter(
    private var listener: OnAdapterSelectedItem? = null
): RecyclerView.Adapter<ConverterUnitViewHolder>(), OnAdapterSelectedItem {
    private var selectedItem = -1

    private var dataList: ConverterUnitsModel = ConverterUnitsModel()
    private var valuesArray : Array<Number> = arrayOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterUnitViewHolder {
        val binding = RecyclerConverterUnitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConverterUnitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConverterUnitViewHolder, position: Int) {
        val unitTitle = dataList.unitsNameList[position]

        // Если спец. символ отсувствует - то вместо него просто печатается первая буква названия
        val specialSymbol = try {
                dataList.unitsSpecialSymbolsList?.get(position)
            } catch (ex: IndexOutOfBoundsException) {
                unitTitle.first().uppercase()
            }

        if (selectedItem == position) {
            holder.bind(ConverterUnitModel(unitTitle, specialSymbol, valuesArray.getOrNull(position) ?: 0))
            holder.isSelected(true)
        }
        else {
            holder.bind(
                ConverterUnitModel(unitTitle, specialSymbol, valuesArray.getOrNull(position) ?: 0)
            )
            holder.isSelected(false)
        }

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
        selectedItem = position
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(newData: ConverterUnitsModel) {
        this.dataList = newData
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewResults(newResults: Array<Number>) {
        valuesArray = newResults
        notifyDataSetChanged()
    }
    companion object {
        const val LOG_TAG = "UnitsCycleAdapter"
    }
}

