package com.example.socialapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

// Database Version
private const val DATABASE_VERSION = 1

// Database Name
private const val DATABASE_NAME = "socialapp.db"

// Table Name
private const val TABLE_USER = "User"

// Column Names
private const val COLUMN_ID = "id"
private const val COLUMN_NAME = "name"
private const val COLUMN_EMAIL = "email"
private const val COLUMN_MOBILE = "mobile"
private const val COLUMN_PASSWORD = "password"

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Create User table
        val createUserTable = ("CREATE TABLE $TABLE_USER ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_EMAIL TEXT, "
                + "$COLUMN_MOBILE TEXT, "
                + "$COLUMN_PASSWORD TEXT)"
                )
        db?.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop older table if it exists and create a new one
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    // Function to add a new user
    fun addUser(name: String, email: String, mobile: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_NAME, name)
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_MOBILE, mobile)
        values.put(COLUMN_PASSWORD, password)

        // Inserting Row and checking for success
        return try {
            val success = db.insertOrThrow(TABLE_USER, null, values)
            db.close() // Closing database connection
            success
        } catch (e: Exception) {
            e.printStackTrace()
            db.close()
            -1  // Return -1 if there's an error during insertion
        }
    }

    // Function to check if the user exists
    fun checkUser(email: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USER WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        val exists = cursor.count > 0
        cursor.close()
        db.close()  // Close the database after checking
        return exists
    }

    // Function to check if the user with given mobile and password exists
    fun checkUserCredentials(mobile: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USER WHERE $COLUMN_MOBILE = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(mobile, password))

        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }
}
