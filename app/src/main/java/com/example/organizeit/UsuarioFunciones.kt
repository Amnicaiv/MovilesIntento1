package com.example.organizeit

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*

class UsuarioFunciones {

    private var isLogged =false
    private var isRegistered =false

    fun tryLogin(
        email: String,
        password: String,
        currentContext: Context
    ){
        val queue = Volley.newRequestQueue(currentContext)

        //Mando la informacion ingresada por el usuario mediante el metodo GET hacia el php que se comunicara con la base de Datos
        val url = "https://cubedout.000webhostapp.com/login.php?Correo=$email&Contra=$password&SendBTN=Submit"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                //Trae de regreso el contenido de HTML del php accedido de forma de String
                var result = response

                //Busco el area donde inserte la respuesta de la base de datos y extraigo solo la info que importa
                result = result.substring(result.indexOf("~")+1, result.indexOf("/~"))

                if(result =="Credenciales incorrectas."){
                    isLogged =false
                    Toast.makeText(currentContext, result, Toast.LENGTH_SHORT).show()
                }else{
                    this.isLogged =true
                    Toast.makeText(currentContext, "Bienvenido.", Toast.LENGTH_SHORT).show()
                }

            },
            Response.ErrorListener { Toast.makeText(currentContext, "Algo salio mal.", Toast.LENGTH_SHORT).show() })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun tryRegistry(data1:String, data2:String, data3:String, data4:String, context: Context){

        val queue = Volley.newRequestQueue(context)

        val url = "https://cubedout.000webhostapp.com/register.php?Nombre=$data1&Apellidos=$data2&Correo=$data3&Contra=$data4&SendBTN=Submit"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                //Trae de regreso el contenido de HTML del php accedido de forma de String
                var result = response

                //Busco el area donde inserte la respuesta de la base de datos y extraigo solo la info que importa
                result = result.substring(result.indexOf("~")+1, result.indexOf("/~"))

                if(result =="No se pudo agregar el Usuario."){
                    isRegistered =false
                    isLogged =false
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                }else{
                    this.isRegistered =true
                    isLogged = true
                    Toast.makeText(context, "Bienvenido.", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { Toast.makeText(context, "Algo salio mal.", Toast.LENGTH_SHORT).show() })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)


    }

    fun getisLogged(): Boolean {
        return this.isLogged
    }

    fun resetisLogged(){
        this.isLogged = false
    }

    fun getisRegistered(): Boolean {
        return this.isRegistered
    }

    fun resetisRegistered(){
        this.isRegistered = false
    }
}