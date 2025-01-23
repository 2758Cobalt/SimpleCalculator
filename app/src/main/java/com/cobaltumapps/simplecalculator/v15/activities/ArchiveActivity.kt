package com.cobaltumapps.simplecalculator.v15.activities

import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ActivityArchiveBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ArchiveController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.archive.ArchiveRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.viewmodel.ArchivedHistoryViewModel
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.viewmodel.HistoryViewModel
import com.google.android.material.snackbar.Snackbar

class ArchiveActivity : AppCompatActivity(), ArchiveController {
    private val binding by lazy { ActivityArchiveBinding.inflate(layoutInflater) }

    // ViewModels
    private lateinit var archivedHistoryViewModel: ArchivedHistoryViewModel
    private lateinit var historyViewModel: HistoryViewModel

    // RecylcerView
    private val archiveRecyclerAdapter by lazy { ArchiveRecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        archivedHistoryViewModel = ViewModelProvider(this)[ArchivedHistoryViewModel::class.java]

        setSupportActionBar(binding.archiveToolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.archiveRecyclerView.apply {
            adapter = archiveRecyclerAdapter
            layoutManager = LinearLayoutManager(this@ArchiveActivity.applicationContext)
        }

        binding.archiveDeleteButton.setOnClickListener {
            clearArchive()
        }
        binding.archiveRestoreButton.setOnClickListener {
            restoreAllItemsToHistory()
        }

        val deleteIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_delete, theme)
        val unarchiveIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_unarchive, theme)

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        deleteArchiveItem(
                            archiveRecyclerAdapter.getArchiveList()[viewHolder.bindingAdapterPosition]
                        )
                    }
                    ItemTouchHelper.RIGHT -> {
                        unarchiveHistoryItem(
                            archiveRecyclerAdapter.getArchiveList()[viewHolder.bindingAdapterPosition]
                        )
                    }
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView

                // Swipe to left
                if (dX < 0) {
                    // Drawing the delete icon
                    deleteIcon?.apply {
                        val iconMargin = (itemView.height - intrinsicHeight) / 2

                        val iconTop = itemView.top + iconMargin
                        val iconBottom = iconTop + intrinsicHeight

                        val iconLeft = itemView.right - (iconMargin - (dX.toInt() / 10)) - intrinsicWidth
                        val iconRight = itemView.right - (iconMargin - (dX.toInt() / 10))

                        // Установка прозрачности иконки в зависимости от длины вектора dX (length = endTouchX - startTouchX)
                        alpha = if (dX > -255) dX.toInt() * -1 else 255
                        setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        draw(c)
                    }
                }

                // Swipe to right
                else {
                    // Drawing the archive icon
                    unarchiveIcon?.apply {
                        val iconMargin = (itemView.height - intrinsicHeight) / 2

                        val iconTop = itemView.top + iconMargin
                        val iconBottom = iconTop + intrinsicHeight

                        val iconLeft = itemView.left + (iconMargin + (dX.toInt() / 10))
                        val iconRight = iconLeft + intrinsicWidth

                        // Установка прозрачности иконки в зависимости от длины вектора dX (length = endTouchX - startTouchX)
                        alpha = if (dX < 255) dX.toInt() else 255
                        setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        draw(c)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val touchHelper = ItemTouchHelper(simpleCallback)
        touchHelper.attachToRecyclerView(binding.archiveRecyclerView)

        loadHistoryList()

    }

    /** Вставляет запись архива в адаптер */
    override fun insertArchiveItem(archivedHistory: ArchivedHistory) {
        archiveRecyclerAdapter.insertArchiveItem(archivedHistory)
    }

    /** Удаляет запись архива из БД и адаптера */
    override fun deleteArchiveItem(archivedHistory: ArchivedHistory) {
        archiveRecyclerAdapter.deleteArchiveItem(archivedHistory)
        archivedHistoryViewModel.deleteArchivedHistoryItem(archivedHistory)
    }

    /** Получает из БД список записей архива */
    private fun loadHistoryList() {
        archivedHistoryViewModel.archivedHistoryList.observe(this) { archivedItemsList ->
            archiveRecyclerAdapter.setNewList(archivedItemsList)
        }
    }

    /** Возвращает запись обратно в историю и удаляет из архива */
    fun unarchiveHistoryItem(archivedHistory: ArchivedHistory) {
        historyViewModel.insertHistoryItem(
            History(
                null,
                archivedHistory.user_expression,
                archivedHistory.result_calculation,
                archivedHistory.date_time_calculation
            )
        )
        Snackbar.make(binding.root, getString(R.string.archive_snackbar_restoreAllItems), Snackbar.LENGTH_SHORT)
            .setAction(R.string.archive_snackbar_action_caption) {}
            .show()
        archiveRecyclerAdapter.deleteArchiveItem(archivedHistory)
        archivedHistoryViewModel.deleteArchivedHistoryItem(archivedHistory)
    }

    /** Возвращает все записи в историю */
    private fun restoreAllItemsToHistory() {
        for (x in archiveRecyclerAdapter.getArchiveList()) {
            historyViewModel.insertHistoryItem(
                History(
                    null,
                    x.user_expression,
                    x.result_calculation,
                    x.date_time_calculation
                )
            )
        }
        clearArchive()
    }

    /** Очищает все элементы архива */
    override fun clearArchive() {
        archiveRecyclerAdapter.clearArchive()
        archivedHistoryViewModel.clearArchivedHistory()
    }
}

