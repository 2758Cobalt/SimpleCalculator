package com.cobaltumapps.simplecalculator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.interfaces.OnAdapterItemClickListener
import com.cobaltumapps.simplecalculator.models.UnitRecyclerModel
import com.cobaltumapps.simplecalculator.references.Services

class ConverterRecyclerAdapter(
    private val context: Context,
    var unitsList: List<UnitRecyclerModel> = listOf(UnitRecyclerModel("None","-1",false)),
    var specialSymbols: List<String> = listOf()

) : RecyclerView.Adapter<ConverterRecyclerAdapter.ConverterHolder>() {
    lateinit var listener: OnAdapterItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_converter_unit, parent, false)
        return ConverterHolder(view)
    }

    override fun getItemCount(): Int {
        return unitsList.size
    }

    override fun onBindViewHolder(holder: ConverterHolder, position: Int) {
        // Получаем объект UnitRecyclerModel
        val unit = unitsList[position]

        // Назначаем текущему объекту пустышке информацию объекта UnitRecyclerModel
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
        if (holder.unitSelectedState){
            holder.unitCard.setBackgroundResource(R.drawable.shape_white_box_selected)

            holder.unitSymbolBox.setBackgroundResource(R.drawable.shape_gradient_orange_box)
        }
        else{
            holder.unitCard.setBackgroundResource(R.drawable.shape_white_box)

            holder.unitSymbolBox.setBackgroundResource(R.drawable.shape_gray_box)
        }

        // Обработка нажатия на элемент
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }

        holder.itemView.setOnLongClickListener {
            Services.copyToClipboard(context, holder.unitResultField.text.toString())
            true
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

        fun setUnit(unit: UnitRecyclerModel){
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
