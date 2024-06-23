package com.cobaltumapps.simplecalculator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.converterNavigation.UnitModel
import com.cobaltumapps.simplecalculator.references.Services

class ConverterRecyclerAdapter(
    private val context: Context,
    private var unitsList: List<UnitModel> = listOf(UnitModel("None","-1",false)),
    private var specialSymbols: List<String> = listOf()

) : RecyclerView.Adapter<ConverterRecyclerAdapter.ConverterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_converter_unit, parent, false)
        return ConverterHolder(view)
    }

    override fun getItemCount(): Int {
        return unitsList.size
    }

    override fun onBindViewHolder(holder: ConverterHolder, position: Int) {
        // Получаем объект UnitModel
        val unit = unitsList[position]

        // Назначаем текущему объекту пустышке информацию объекта UnitModel
        holder.setUnit(unit)


        // Если спец. символ отсувствует - то вместо него просто печатается первая буква названия
        if (specialSymbols.isNotEmpty()){
            if (specialSymbols == null)
                holder.unitSymbolBox.text = unit.getTitle().first().uppercase()
            else
                holder.unitSymbolBox.text = specialSymbols[position]
        }
        else
            holder.unitSymbolBox.text = unit.getTitle().first().uppercase()

        // Проверяет выбран ли элемент
        if (holder.unitSelectedState)
            holder.unitCard.setBackgroundResource(R.drawable.shape_white_box_selected)
        else
            holder.unitCard.setBackgroundResource(R.drawable.shape_white_box)

        // Обработка нажатия на элемент
        holder.itemView.setOnClickListener {
            Services.copyToClipboard(context, holder.unitResultField.text.toString())
        }

    }

    inner class ConverterHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var unitCard: androidx.constraintlayout.widget.ConstraintLayout

        var unitTitleField: TextView
        var unitSymbolBox: TextView
        var unitResultField: TextView

        var unitSelectedState: Boolean
        init {
            unitCard = itemView.findViewById(R.id.unitCardItem)

            unitTitleField = itemView.findViewById(R.id.unitTitle)
            unitResultField = itemView.findViewById(R.id.unitConvertInput)
            unitSymbolBox = itemView.findViewById(R.id.unitSymbol)
            unitSelectedState = false
        }

        fun setUnit(unit: UnitModel){
            unitTitleField.text = unit.getTitle()
            unitResultField.text = unit.getResult()
            unitSelectedState = unit.getSelection()
        }

    }

    fun updateItem(position: Int, newResult: Double, isSelected: Boolean){
        val unitMutable = unitsList.toMutableList()

        unitMutable[position].setResult(newResult.toString())
        unitMutable[position].setSelectedState(isSelected)

        notifyItemChanged(position)
    }


}
