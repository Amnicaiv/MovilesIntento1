package com.example.organizeit

import android.app.ActivityManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_categories.*


class Activity_CreateCategory : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_categories)

        val Nombre = ""
        val Descripcion = ""
        val Color = "Rojo"


        this.button6.setOnClickListener(){
            Toast.makeText(this, "Guardar Categoria", Toast.LENGTH_SHORT).show()
            this.editTextTextMultiLine
        }

        this.button7.setOnClickListener(){
            Toast.makeText(this, "Se descarto la categoria.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}