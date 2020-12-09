package com.example.organizeit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Patterns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbMan = DBManager(this)

        if(dbMan.GetUserId() != "No se encontro"){
            //Toast.makeText(this, dbMan.GetUserId().toString(), Toast.LENGTH_SHORT).show()
            val menuActivity = Intent(applicationContext, CategoriesActivity::class.java)
            startActivity(menuActivity)
        }

        var userFunc = UsuarioFunciones()

        this.button.setOnClickListener(){

            userFunc.resetisLogged()
            val email = this.editTextTextEmailAddress3.text.toString()
            val password = this.editTextTextPassword.text.toString()

            if(validateLogin(email,password)){
                /*val dummyActivity = Intent(applicationContext, dummy::class.java)
                startActivity(dummyActivity)*/
                userFunc.tryLogin(email, password, this, applicationContext)

            }


            val timer = object: CountDownTimer(3000, 1000) {
                override fun onFinish() {
                    userFunc.resetisLogged()
                }

                override fun onTick(millisUntilFinished: Long) {
                    if (userFunc.getisLogged()){
                        val menuActivity = Intent(applicationContext, dummy::class.java)
                        startActivity(menuActivity)
                        userFunc.resetisLogged()
                    }
                }
            }
            timer.start()
        }

        this.textView2.setOnClickListener(){
            val registerActivity = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(registerActivity)
        }
    }



    fun validateLogin(emailP: String, passwordP: String): Boolean {

        val isEmailInFormat = Patterns.EMAIL_ADDRESS.matcher(emailP).matches();
        if ( (emailP.isNotEmpty() && isEmailInFormat) && (passwordP.isNotEmpty()) ){

            return true
        }else {
            if (emailP.isEmpty()) {
                Toast.makeText(this, R.string.usermessage_noemail, Toast.LENGTH_SHORT).show()
                return false
            }
            if(!isEmailInFormat){
                Toast.makeText(this, R.string.usermessage_noemailformat, Toast.LENGTH_SHORT).show()
                return false
            }
            if(passwordP.isEmpty()){
                Toast.makeText(this, R.string.usermessage_nopassword, Toast.LENGTH_SHORT).show()
                return false
            }
        }

        //Toast.makeText(this, isEmailInFormat.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Correo o contraseña incorrecta.", Toast.LENGTH_SHORT).show()
        return false
    }

}