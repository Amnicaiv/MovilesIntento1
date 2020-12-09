package com.example.organizeit

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*



class RegisterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        var userfunc = UsuarioFunciones()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.button2.setOnClickListener(){

            val nombreInput = this.edit_Nombre_Registro.text.toString()
            val apellidosInput = this.edit_Apellidos_Registro.text.toString()
            val correoInput = this.edit_Email_Registro.text.toString()
            val contraInput = this.edit_Contra_Registro.text.toString()

            if(validateForm()){


                userfunc.tryRegistry(nombreInput, apellidosInput, correoInput, contraInput, this)



                val timer = object: CountDownTimer(3000, 1000) {
                    override fun onFinish() {
                        userfunc.resetisLogged()
                        userfunc.resetisRegistered()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        if(userfunc.getisLogged() && userfunc.getisRegistered()){

                            userfunc.resetisLogged()
                            userfunc.resetisRegistered()

                            var dbManager = DBManager(applicationContext)
                            var values = ContentValues()

                            values.put("Nombre", nombreInput)
                            values.put("Apellidos", apellidosInput)
                            values.put("Correo", correoInput)
                            values.put("Contra", contraInput)

                            dbManager.CleanUserTable()
                            val ID= dbManager.Insert(values)

                            if(ID >0){
                                val menuActivity = Intent(applicationContext, CategoriesActivity::class.java)
                                startActivity(menuActivity)
                            }else{
                                Toast.makeText(applicationContext, "No se pudo agregar el usuario", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                timer.start()
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