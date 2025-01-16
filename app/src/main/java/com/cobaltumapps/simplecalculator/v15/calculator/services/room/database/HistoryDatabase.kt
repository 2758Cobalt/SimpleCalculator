package com.cobaltumapps.simplecalculator.v15.calculator.services.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.constants.RoomHistoryConstants
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.dao.ArchivedHistoryDao
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.dao.HistoryDao
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

@Database(entities = [History::class, ArchivedHistory::class], version = 2, exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
    abstract fun getArchivedHistoryDao(): ArchivedHistoryDao


    companion object {
        private val MIGRATION_1_2 = object: Migration(1,2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Создание новой таблицы с изменённым типом столбца
                val table_migration_name = "SC_HistoryTableMIGRATION1_2"
                val table_default_name = "SC_HistoryTable"
                db.execSQL("""
                   CREATE TABLE IF NOT EXISTS `$table_migration_name` 
                   (
                   `id` INTEGER PRIMARY KEY AUTOINCREMENT, 
                   `user_expression` TEXT NOT NULL, 
                   `result_calculation` TEXT NOT NULL, 
                   `date_time_calculation` INTEGER NOT NULL)
                """.trimIndent())

                db.execSQL("""
                    INSERT INTO `$table_migration_name` (`id`,`user_expression`,`result_calculation`,`date_time_calculation`)
                    SELECT `id`, `user_expression`, `result_calculation`, `date_time_calculation`
                    FROM `$table_default_name`
                """.trimIndent())

                // Удаление старой таблицы
                db.execSQL("DROP TABLE `$table_default_name`")

                // Переименование новой таблицы в оригинальное имя
                db.execSQL("ALTER TABLE `$table_migration_name` RENAME TO $table_default_name")
            }
        }
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
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                instance = roomInstance
                return roomInstance
            }
        }
    }
}