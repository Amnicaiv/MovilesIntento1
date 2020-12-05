package com.example.organizeit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.button.setOnClickListener(){
            val email = this.editTextTextEmailAddress3.text.toString()
            val password = this.editTextTextPassword.text.toString()

            if(validateLogin(email,password)){
                val dummyActivity = Intent(applicationContext, dummy::class.java)
                startActivity(dummyActivity)
            }
        }

        this.textView2.setOnClickListener(){
            val registerActivity = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(registerActivity)
        }
     /*   this.lbl_Registrarse.setOnClickListener(){

        }*/
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