package com.example.organizeit

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
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
                    values.put("Imagen", response.getString("Imagen"))

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

    fun tryUserInfoMod(data1:String, data2:String, data3:String, data4:String, data5:String, userId:Int, context: Context, appContext : Context){

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
            `object`.put("idUsuario", userId)
            `object`.put("Action", "EditUser")

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

                },
                Response.ErrorListener { error -> Toast.makeText(context,"No se pudo guardar en la base de datos, intentar de nuevo.", Toast.LENGTH_LONG).show() })
        requestQueue.add(jsonObjectRequest)

    }

    fun getUserID(context: Context): String? {
        val dbManager = DBManager(context)
        return dbManager.GetUserId()
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