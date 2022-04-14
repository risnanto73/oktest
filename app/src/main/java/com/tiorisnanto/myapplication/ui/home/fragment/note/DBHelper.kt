package com.tiorisnanto.myapplication.ui.home.fragment.note

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME " +
                    "($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_AGE TEXT, $COLUMN_EMAIL TEXT, $COLUMN_DATE TEXT, $COLUMN_IMAGE BLOB)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertRow(name: String, age:String, email: String, date: String) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_AGE, age)
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_DATE, date)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateRow(row_id: String, name: String, age:String, email: String, date: String) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_AGE, age)
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_DATE, date)

        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(row_id))
        db.close()
    }

    fun deleteRow(row_id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(row_id))
        db.close()
    }

    fun getAllRow(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "myDBfile.db"
        const val TABLE_NAME = "users"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_DATE = "date"
        const val COLUMN_IMAGE = "image"
    }


}