package com.example.organizeit

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import org.json.JSONException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder




class RegisterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.button2.setOnClickListener(){

            val nombreInput = this.edit_Nombre_Registro.text.toString()
            val apellidosInput = this.edit_Apellidos_Registro.text.toString()
            val correoInput = this.edit_Email_Registro.text.toString()
            val contraInput = this.edit_Contra_Registro.text.toString()

            if(validateForm()){
                var dbManager = DBManager(this)
                var values = ContentValues()

                values.put("Nombre", nombreInput)
                values.put("Apellidos", apellidosInput)
                values.put("Correo", correoInput)
                values.put("Contra", contraInput)

                val ID= dbManager.Insert(values)

                if(ID >0){
                    //Toast.makeText(this, "Usuario Registrado, Bien", Toast.LENGTH_SHORT).show()

                    sendPost(nombreInput, apellidosInput, correoInput, contraInput)

                    /*val dummyActivity = Intent(applicationContext, dummy::class.java)
                    startActivity(dummyActivity)*/
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


    fun sendPost(data1:String, data2:String, data3:String, data4:String){
        //var urlPost = "https://10.0.2.2/MovilesAPI/register.php";

        //val queue = Volley.newRequestQueue(this)
        //val url = "http://10.0.2.2/MovilesAPI/register.php/?" + "Nombre=" + data1 +"&Apellidos="+data2+"&Correo="+data3+"&Contra="+data4
        //val url = "http://10.0.2.2/MovilesAPI/register.php?Nombre=Pedrito&Apellidos=Sola&Correo=pedro%40gmail.com&Contra=asldnas&SendBTN=Submit"



        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        val url = "https://cubedout.000webhostapp.com/register.php?Nombre=$data1&Apellidos=$data2&Correo=$data3&Contra=$data4&SendBTN=Submit"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                //Toast.makeText(this, "Response is: ${response.substring(0, 500)}", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { this.textView.text = "That didn't work!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)


    }

}