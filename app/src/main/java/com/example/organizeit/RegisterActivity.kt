package com.example.organizeit

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.button2.setOnClickListener(){
            if(validateForm()){
                var dbManager = DBManager(this)
                var values = ContentValues()
                values.put("Nombre", this.edit_Nombre_Registro.text.toString())
                values.put("Apellidos", this.edit_Apellidos_Registro.text.toString())
                values.put("Correo", this.edit_Email_Registro.text.toString())
                values.put("Contra", this.edit_Contra_Registro.text.toString())

                val ID= dbManager.Insert(values)

                if(ID >0){
                    Toast.makeText(this, "Usuario Registrado, Bien", Toast.LENGTH_SHORT).show()
                    val dummyActivity = Intent(applicationContext, dummy::class.java)
                    startActivity(dummyActivity)
                }else{
                    Toast.makeText(this, "No se pudo agregar el usuario", Toast.LENGTH_SHORT).show()
                }

            }
        }



    }

    fun validateForm(): Boolean {
        val isEmailInFormat = Patterns.EMAIL_ADDRESS.matcher(this.edit_Email_Registro.text).matches();

        if(this.edit_Nombre_Registro.text.isNotEmpty() && this.edit_Apellidos_Registro.text.isNotEmpty() && this.edit_Contra_Registro.text.isNotEmpty() &&
            this.edit_Email_Registro.text.isNotEmpty()){
            if (isEmailInFormat){
                Toast.makeText(this, "Todo bien", Toast.LENGTH_SHORT).show()
                return true
            }else{
                Toast.makeText(this, "Ingrese un correo valido.", Toast.LENGTH_SHORT).show()
                return false
            }

        }else{
            Toast.makeText(this, "Falto un campo o mas de llenar.", Toast.LENGTH_SHORT).show()
        }

        return false
    }

}