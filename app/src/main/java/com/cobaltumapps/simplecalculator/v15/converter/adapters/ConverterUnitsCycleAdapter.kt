package com.cobaltumapps.simplecalculator.v15.converter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerConverterUnitItemBinding
import com.cobaltumapps.simplecalculator.v15.converter.adapters.holders.ConverterUnitViewHolder
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitModel
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitsModel
import com.cobaltumapps.simplecalculator.v15.converter.mediator.ConverterMediatorHardUpdater

/** Адаптер отображающий список элементов конвертеров, где каждый элемент - конвертер*/
class ConverterUnitsCycleAdapter(
    private var listener: OnAdapterSelectedItem? = null
): RecyclerView.Adapter<ConverterUnitViewHolder>(),
    OnAdapterSelectedItem, ConverterMediatorHardUpdater
{
    var mediatorHardUpdaterListener: ConverterMediatorHardUpdater? = null

    private var selectedItem = -1

    private var dataList: ConverterUnitsModel = ConverterUnitsModel()
    private var valuesArray : MutableList<Number> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterUnitViewHolder {
        val binding = RecyclerConverterUnitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConverterUnitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConverterUnitViewHolder, position: Int) {
        val unitTitle = dataList.unitsNameList[position]

        val unitValue = valuesArray.getOrNull(position) ?: 0

        // Если спец. символ отсувствует - то вместо него просто печатается первая буква названия
        val specialSymbol = try {
                dataList.unitsSpecialSymbolsList?.get(position)
            } catch (ex: IndexOutOfBoundsException) {
                unitTitle.first().uppercase()
            }

        if (selectedItem == position) {
            holder.bind(
                ConverterUnitModel(unitTitle, specialSymbol, unitValue)
            )
            holder.isSelected(true)
        }
        else {
            holder.bind(
                ConverterUnitModel(unitTitle, specialSymbol, unitValue)
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
        if (position < 0 || position >= itemCount) return // Проверка на выход за границы

        if (selectedItem != position) {
            val prevSelected = selectedItem
            selectedItem = position

            listener?.selectedItemPosition(position)
            hardUpdateCalculations()

            updateHolders(prevSelected, position)
        }
    }

    fun setNewData(newData: ConverterUnitsModel) {
        valuesArray.clear()
        selectedItem = -1
        dataList = newData
        notifyDataSetChanged()
    }

    fun setNewResults(newResults: Array<Number>) {
        valuesArray = newResults.toMutableList()
        notifyItemRangeChanged(0, dataList.unitsNameList.size)
    }

    private fun updateHolders(prevPosition: Int, newPosition: Int) {
        if (prevPosition in 0..<itemCount) {
            notifyItemChanged(prevPosition)
        }
        if (newPosition in 0..<itemCount) {
            notifyItemChanged(newPosition)
        }
    }

    override fun hardUpdateCalculations() {
        mediatorHardUpdaterListener?.hardUpdateCalculations()
    }

    companion object {
        const val LOG_TAG = "UnitsCycleAdapter"
    }

}