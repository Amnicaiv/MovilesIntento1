package com.example.organizeit

import android.R
import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class UsuarioFunciones {

    private var isLogged =false
    private var isRegistered =false

    private val appDomainHost = "https://cubedout.000webhostapp.com/API/UserAction.php"



    fun tryLogin(
        email: String,
        password: String,
        context: Context,
        appContext : Context
    ){
//        val queue = Volley.newRequestQueue(currentContext)
//
//        //Mando la informacion ingresada por el usuario mediante el metodo GET hacia el php que se comunicara con la base de Datos
//        val url = "https://cubedout.000webhostapp.com/login.php?Correo=$email&Contra=$password&SendBTN=Submit"
//
//        // Request a string response from the provided URL.
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                //Trae de regreso el contenido de HTML del php accedido de forma de String
//                var result = response
//
//                //Busco el area donde inserte la respuesta de la base de datos y extraigo solo la info que importa
//                result = result.substring(result.indexOf("~")+1, result.indexOf("/~"))
//
//                if(result =="Credenciales incorrectas."){
//                    isLogged =false
//                    Toast.makeText(currentContext, result, Toast.LENGTH_SHORT).show()
//                }else{
//
//                    val mStrings = result.split("/").toTypedArray()
//
//
//
//                    val userID = mStrings[0]
//                    val nombreIBD= mStrings[1]
//                    val apellidosIBD= mStrings[2]
//                    val correoIBD= mStrings[3]
//                    val contraIBD=mStrings[4]
//                    Toast.makeText(currentContext, userID, Toast.LENGTH_SHORT).show()
//
//                    var dbManager = DBManager(appContext)
//
//                    var values = ContentValues()
//
//                    values.put("ID", userID)
//                    values.put("Nombre", nombreIBD)
//                    values.put("Apellidos", apellidosIBD)
//                    values.put("Correo", correoIBD)
//                    values.put("Contra", contraIBD)
//
//                    dbManager.CleanUserTable()
//                    val ID= dbManager.Insert(values)
//                    if (ID > 0){
//                        this.isLogged =true
//                        Toast.makeText(currentContext, "Bienvenido.", Toast.LENGTH_SHORT).show()
//                    }
//
//
//                }
//
//            },
//            Response.ErrorListener { Toast.makeText(currentContext, "Algo salio mal.", Toast.LENGTH_SHORT).show() })
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest)

        val requestQueue =
            Volley.newRequestQueue(context)
        val `object` = JSONObject()
        try {
            //input your API parameters
            `object`.put("Correo", email)
            `object`.put("Contra", password)
            `object`.put("Action", "Login")

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val url: String = appDomainHost
        val jsonObjectRequest =
            JsonObjectRequest(
                Request.Method.POST, url, `object`,
                Response.Listener { response ->
                    Toast.makeText(context, "Bienvenido.", Toast.LENGTH_SHORT).show()
                    var dbManager = DBManager(appContext)

                    var values = ContentValues()

                    values.put("ID", response.getInt("IdUsuario"))
                    values.put("Nombre",  response.getString("Nombre"))
                    values.put("Apellidos",  response.getString("Apellidos"))
                    values.put("Correo",  response.getString("Correo"))
                    values.put("Contra", response.getString("Contra"))

                    dbManager.CleanUserTable()
                    val ID= dbManager.Insert(values)
                    if (ID > 0){
                        this.isLogged =true
                        Toast.makeText(context, "Bienvenido.", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error -> Toast.makeText(context,"No se pudo authenticar sus credenciales, intentar mas tarde.", Toast.LENGTH_LONG).show() })
        requestQueue.add(jsonObjectRequest)
    }

    fun tryRegistry(data1:String, data2:String, data3:String, data4:String, data5:String, context: Context, appContext : Context){

        val requestQueue =
            Volley.newRequestQueue(context)
        val `object` = JSONObject()
        try {
            //input your API parameters
            `object`.put("Nombre", data1)
            `object`.put("Apellidos", data2)
            `object`.put("Correo", data3)
            `object`.put("Contra", data4)
            `object`.put("ImgData", data5)
            `object`.put("Action", "Register")

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val url: String = appDomainHost
        val jsonObjectRequest =
            JsonObjectRequest(
                Request.Method.POST, url, `object`,
                Response.Listener { response ->

                    isRegistered = true

                    tryLogin(data3,data4,context,appContext)
//                    var dbManager = DBManager(appContext)
//
//                    var values = ContentValues()
//
//                    values.put("ID", response.getInt("IdUsuario"))
//                    values.put("Nombre",  response.getString("Nombre"))
//                    values.put("Apellidos",  response.getString("Apellidos"))
//                    values.put("Correo",  response.getString("Correo"))
//                    values.put("Contra", response.getString("Contra"))
//
//                    dbManager.CleanUserTable()
//                    val ID= dbManager.Insert(values)
//                    if (ID > 0){
//                        isLogged =true
//                        Toast.makeText(context, "Bienvenido.", Toast.LENGTH_SHORT).show()
//                    }
                },
                Response.ErrorListener { error -> Toast.makeText(context,"No se pudo guardar en la base de datos, intentar de nuevo.", Toast.LENGTH_LONG).show() })
        requestQueue.add(jsonObjectRequest)

    }

    fun getUserID(context: Context): String? {
        val dbManager = DBManager(context)
        val userID = dbManager.GetUserId()
        return userID
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