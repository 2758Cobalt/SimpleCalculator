package com.cobaltumapps.simplecalculator.v15.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cobaltumapps.simplecalculator.v15.sqlite.interfaces.Dao

@Database(
    entities = [RoomHistoryData::class],
    version = 1)
abstract class SqliteRoomDatabase: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        private const val dbName = "SC_CalculatorHistoryDB.db"

        fun getDatabase(context: Context): SqliteRoomDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                SqliteRoomDatabase::class.java,
                dbName
            ).build()
        }
    }
}

