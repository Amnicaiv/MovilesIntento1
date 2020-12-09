package com.example.organizeit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(context: Context) {

    val dbName = "MI_OrganizeIt"
    val dbTable= "Users"
    private val colId = "ID"
    private val colName ="Nombre"
    private val colLastNames = "Apellidos"
    private val colCorreo = "Correo"
    private val colContra = "Contra"
    val dbVersion =1
    val sqlCreateTable ="CREATE TABLE IF NOT EXISTS " + dbTable +" ("+ colId +" INTEGER PRIMARY KEY," +
            colName + " TEXT, "+ colLastNames +" TEXT, "+ colCorreo + " TEXT, " + colContra + " TEXT);"


    val sqlCreateTableCat ="CREATE TABLE IF NOT EXISTS " + "categorias" + " (ID INTEGER PRIMARY KEY, nombre TEXT, descripcion TEXT, color TEXT, borrador BOOL, idUsuario INTEGER);"

    var sqlDB:SQLiteDatabase?=null


    init {
        val db= DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
        sqlDB!!.execSQL(sqlCreateTable)
        sqlDB!!.execSQL(sqlCreateTableCat)
    }

    inner class DatabaseHelperNotes:SQLiteOpenHelper{
        var context: Context?=null
        constructor(context: Context):super(context, dbName,null,dbVersion){
            this.context = context
        }
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            p0!!.execSQL(sqlCreateTableCat)
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

    fun GetUserId(): String? {
        val ID = sqlDB!!.rawQuery("SELECT ID FROM Users",null)
        if(ID.moveToFirst()){
            val str = ID.getString(ID.getColumnIndex("ID"))
            return str
        }
        return "No se encontro"
    }

    fun CleanUserTable(){
        sqlDB!!.execSQL("DELETE FROM Users;")
    }

    fun InsertCat(values:ContentValues):Long{
        val ID= sqlDB!!.insert("categorias", "",values)
        return ID
    }

    fun UpdateCatStatus(name:String, author:String, status:String, cv:ContentValues){
        sqlDB!!.update("categorias", cv, "nombre = ?", arrayOf(name))
    }

    fun getUserCat(userID: String): String? {
        val ID = sqlDB!!.rawQuery("SELECT nombre FROM categorias WHERE idUsuario = $userID",null)
        if(ID.moveToFirst()){
            val str = ID.getString(ID.getColumnIndex("nombre"))
            return str
        }
        return "No se encontro"
    }



}