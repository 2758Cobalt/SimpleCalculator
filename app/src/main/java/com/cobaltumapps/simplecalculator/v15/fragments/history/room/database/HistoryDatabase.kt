package com.cobaltumapps.simplecalculator.v15.fragments.history.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cobaltumapps.simplecalculator.v15.fragments.history.room.dao.HistoryDao
import com.cobaltumapps.simplecalculator.v15.fragments.history.room.repository.HistoryEntity

private const val dbName = "SC_CalculatorHistoryDatabase"

@Database(version = 1, entities = [HistoryEntity::class], exportSchema = false)
abstract class HistoryDatabase(): RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    dbName
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}