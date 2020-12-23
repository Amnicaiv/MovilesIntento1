package com.example.organizeit

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class CatFunciones {


    fun tryUpload(
        nombre: String,
        desc: String,
        color:String,
        autor:String,
        currentContext: Context,
        appContext : Context
    ){
        val queue = Volley.newRequestQueue(currentContext)
        var isUploaded = false
        //Mando la informacion ingresada por el usuario mediante el metodo GET hacia el php que se comunicara con la base de Datos
        val url = "https://cubedout.000webhostapp.com/addCat.php?nombre=$nombre&descripcion=$desc&color=$color&userId=$autor&SendBTN=Submit"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                //Trae de regreso el contenido de HTML del php accedido de forma de String
                var result = response

                //Busco el area donde inserte la respuesta de la base de datos y extraigo solo la info que importa
                result = result.substring(result.indexOf("~")+1, result.indexOf("/~"))

                if(result =="No se pudo agregar la Categoria."){
                    isUploaded =false
                    Toast.makeText(currentContext, result, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(currentContext, "La categoria se guardo en la nube", Toast.LENGTH_SHORT).show()
                    val dbMan = DBManager(currentContext)
                    val contentValues = ContentValues()


                    contentValues.put("borrador", false)

                    dbMan.UpdateCatStatus(nombre,autor,false.toString(),contentValues)
                }

            },
            Response.ErrorListener { Toast.makeText(currentContext, "No se Guardo en la nube.", Toast.LENGTH_SHORT).show() })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}