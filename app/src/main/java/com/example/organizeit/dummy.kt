package com.example.organizeit

import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.dummypage.*

class dummy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_categories)

      /*  val dbMan = DBManager(this)

        val userFunc = UsuarioFunciones()
        val catFunciones = CatFunciones()

        this.btn_Cat_Menu.setOnClickListener(){
            val catName = editNombreCat.text
            val catDesc = editDescCat.text
            val catColor = editColorCat.text
            val catAuthor = userFunc.getUserID(this)

            var values = ContentValues()
            //    val sqlCreateTableCat ="CREATE TABLE IF NOT EXISTS " + "categorias" + " (ID INTEGER PRIMARY KEY, nombre TEXT, descripcion TEXT, color TEXT, borrador BOOL, idUsuario INTEGER);"
            values.put("nombre", catName.toString())
            values.put("descripcion", catDesc.toString())
            values.put("color", catColor.toString())
            values.put("idUsuario", catAuthor)
            values.put("borrador", true)

            val ID= dbMan.InsertCat(values)
            if (ID >0){
                Toast.makeText(this, "Se creo la categoria $catAuthor.", Toast.LENGTH_SHORT).show()
                catFunciones.tryUpload(catName.toString(),catDesc.toString(),catColor.toString(),catAuthor.toString(),this,applicationContext)
            }







        }*/
    }
}