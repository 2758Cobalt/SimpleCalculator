package com.cobaltumapps.simplecalculator.v15.sqlite

object DatabaseQueries {
    const val queryCreateTable =
        "CREATE TABLE ${SqliteDatabaseHelper.TABLE_NAME}" +
                " (" +
                "${SqliteDatabaseHelper.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${SqliteDatabaseHelper.COLUMN_EXPRESSION} TEXT DEFAULT '0' NOT NULL, " +
                "${SqliteDatabaseHelper.COLUMN_RESULT} TEXT DEFAULT '0.0' NOT NULL" +
                ");"

    const val queryDeleteTableExists = "DROP TABLE IF EXISTS ${SqliteDatabaseHelper.TABLE_NAME}"
}