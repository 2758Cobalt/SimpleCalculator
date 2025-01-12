package com.cobaltumapps.simplecalculator.v15.calculator.services.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.constants.RoomHistoryConstants
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.dao.HistoryDao
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao

    companion object {
        @Volatile
        private var instance: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomInstance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    RoomHistoryConstants.DATABASE_NAME
                ).build()
                instance = roomInstance
                return roomInstance
            }
        }
    }
}