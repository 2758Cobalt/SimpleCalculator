package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.archive

import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory

interface ArchiveController {
    fun insertArchiveItem(archivedHistory: ArchivedHistory)
    fun deleteArchiveItem(archivedHistory: ArchivedHistory)
    fun clearArchive()
}