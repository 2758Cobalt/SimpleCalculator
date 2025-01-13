package com.cobaltumapps.simplecalculator.v15.activities.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory

interface ArchiveController {
    fun insertArchiveItem(archivedHistory: ArchivedHistory)
    fun deleteArchiveItem(archivedHistory: ArchivedHistory)
}