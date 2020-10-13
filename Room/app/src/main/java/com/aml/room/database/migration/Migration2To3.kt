package com.aml.room.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration2To3 : Migration(2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS temp_user (userId INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL UNIQUE)")
    }
}