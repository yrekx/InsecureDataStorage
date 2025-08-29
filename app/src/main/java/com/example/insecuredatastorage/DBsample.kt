package com.example.insecuredatastorage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBsample(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "employees.db"
        val TABLE_NAME = "employees_info"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_EMAIL = "email"
        val COLUMN_POSITION = "position"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_POSITION + " TEXT" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addEmployee(name: String, email: String, position: String) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_POSITION, position)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun addMultipleEmployees(employees: List<Triple<String, String, String>>) {
        for ((name, email, position) in employees) {
            addEmployee(name, email, position)
        }
    }

    fun addDefaultEmployees() {
        val employees = listOf(
            Triple("Bob", "bob.johnson@example.com", "Project Manager"),
            Triple("Charlie", "charlie.davis@example.com", "Data Analyst"),
            Triple("Diana", "diana.miller@example.com", "UX Designer"),
            Triple("Ethan", "ethan.wilson@example.com", "Software Engineer"),
            Triple("Alice", "alice.smith@example.com", "Product Manager")
        )
        addMultipleEmployees(employees)
    }

    fun getAllEmployee(): List<Employee> {
        val employees = mutableListOf<Employee>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val position = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSITION))
                employees.add(Employee(id, name, email, position))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return employees
    }

    fun getEmployee(id: Int): Cursor? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_POSITION),
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        return cursor
    }

    fun deleteEmployee(id: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }
}