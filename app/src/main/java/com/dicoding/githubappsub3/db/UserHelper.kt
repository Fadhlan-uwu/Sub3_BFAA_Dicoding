package com.dicoding.githubappsub3.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dicoding.githubappsub3.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.dicoding.githubappsub3.db.DatabaseContract.UserColumns.Companion._ID
import com.dicoding.githubappsub3.db.DatabaseContract.UserColumns.Companion.LOGIN
import java.sql.SQLException

class UserHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: UserHelper? = null
        fun getInstance(context: Context): UserHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: UserHelper(context)
                }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    // ambil data
    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$LOGIN ASC"
        )
    }

    // ambil data dengan id
    fun queryById(id: String): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                "$_ID = ?",
                arrayOf(id),
                null,
                null,
                null,
                null)
    }

    // insert data
    fun insert(values: ContentValues?): Long {
        return database.insert(
            DATABASE_TABLE,
            null,
            values)
    }

    // update data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(
            DATABASE_TABLE,
            values,
            "$_ID = ?",
            arrayOf(id))
    }

    // delete data
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}