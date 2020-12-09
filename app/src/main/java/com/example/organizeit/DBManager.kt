package com.example.organizeit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBManager {

    val dbName = "MI_OrganizeIt"
    val dbTable= "Users"
    val colId = "ID"
    val colName ="Nombre"
    val colLastNames = "Apellidos"
    val colCorreo = "Correo"
    val colContra = "Contra"
    val dbVersion =1
    val sqlCreateTable ="CREATE TABLE IF NOT EXISTS " + dbTable +" ("+ colId +" INTEGER PRIMARY KEY," +
            colName + " TEXT, "+ colLastNames +" TEXT, "+ colCorreo + " TEXT, " + colContra + " TEXT);"

    var sqlDB:SQLiteDatabase?=null


    constructor(context: Context){
        val db= DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperNotes:SQLiteOpenHelper{
        var context: Context?=null
        constructor(context: Context):super(context, dbName,null,dbVersion){
            this.context = context
        }
        override fun onCreate(p0: SQLiteDatabase?) {
            Toast.makeText(this.context, "database created", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table  IF EXISTS " +dbTable)
        }
    }

    fun Insert(values:ContentValues): Long {
        sqlDB!!.execSQL("DELETE FROM Users;")
        val ID= sqlDB!!.insert(dbTable, "",values)
        return ID
    }

    fun CheckLoggedUser(): Boolean {
        val selectQuery = "SELECT * FROM Users;"
        val ID = sqlDB!!.rawQuery(selectQuery,null)
        if (ID != null){
            return true
        }
        return false
    }

    fun CleanUserTable(){
        sqlDB!!.execSQL("DELETE FROM Users;")
    }



}