package com.example.organizeit

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.io.ByteArrayOutputStream


class RegisterActivity : AppCompatActivity(){
    var PICK_IMAGE = 1
    lateinit var bitmap:Bitmap
    lateinit var imageView:ImageView
    var imageLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {

        var userfunc = UsuarioFunciones()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        imageView = this.imageView3

        this.button2.setOnClickListener(){

            val nombreInput = this.edit_Nombre_Registro.text.toString()
            val apellidosInput = this.edit_Apellidos_Registro.text.toString()
            val correoInput = this.edit_Email_Registro.text.toString()
            val contraInput = this.edit_Contra_Registro.text.toString()



            if(validateForm()){

                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
                val b: ByteArray = baos.toByteArray()
                val encodedImage: String = Base64.encodeToString(b, Base64.DEFAULT)


                userfunc.tryRegistry(nombreInput, apellidosInput, correoInput, contraInput, encodedImage, this, applicationContext)



                val timer = object: CountDownTimer(6000, 1000) {
                    override fun onFinish() {
                        userfunc.resetisLogged()
                        userfunc.resetisRegistered()
                    }

                    override fun onTick(millisUntilFinished: Long) {

                        if(userfunc.getisLogged() && userfunc.getisRegistered()){

                         /*   userfunc.resetisLogged()
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
                                finish()
                            }else{
                                Toast.makeText(applicationContext, "No se pudo agregar el usuario", Toast.LENGTH_SHORT).show()
                            }*/

                            val menuActivity = Intent(applicationContext, CategoriesActivity::class.java)
                            startActivity(menuActivity)
                            finish()
                        }
                    }
                }
                timer.start()
            }
        }

        this.LoadImg_BTN.setOnClickListener(){
            openGallery()

        }
    }




    private fun checkPassWord(str: String): Boolean {
        var ch: Char
        var capitalFlag = false
        var lowerCaseFlag = false
        var numberFlag = false
        if(str.length>=8){
            for (i in 0 until str.length) {
                ch = str[i]
                if (Character.isDigit(ch)) {
                    numberFlag = true
                } else if (Character.isUpperCase(ch)) {
                    capitalFlag = true
                } else if (Character.isLowerCase(ch)) {
                    lowerCaseFlag = true
                }
                if (numberFlag && capitalFlag && lowerCaseFlag) return true
            }
        }
        return false
    }

    fun validateForm(): Boolean {
        val isEmailInFormat = Patterns.EMAIL_ADDRESS.matcher(this.edit_Email_Registro.text).matches();

        if(this.edit_Nombre_Registro.text.isNotEmpty() && this.edit_Apellidos_Registro.text.isNotEmpty() && this.edit_Contra_Registro.text.isNotEmpty() &&
                this.edit_Email_Registro.text.isNotEmpty() && imageLoaded
        ){
            if (isEmailInFormat){

                if(checkPassWord(this.edit_Contra_Registro.text.toString())){
                    return true
                }else{
                    Toast.makeText(this, "Contraseña debe contener una mayuscula, minuscula, un numero y tener mas de 7 characteres.", Toast.LENGTH_LONG).show()
                }
                return false

            }else{
                Toast.makeText(this, "Ingrese un correo valido.", Toast.LENGTH_SHORT).show()
                return false
            }



        }else{
            Toast.makeText(this, "Falto un campo o mas de llenar.", Toast.LENGTH_SHORT).show()
        }

        return false
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
            val uri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri)
            val newBM = getResizedBitmap(bitmap, 250,250)
            if (newBM != null) {
                bitmap = newBM
                imageLoaded=true
            }
            imageView.setImageBitmap(newBM)
        }
    }

    fun openGallery(){
        val pickImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImage,PICK_IMAGE)
    }

    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false
        )
        bm.recycle()
        return resizedBitmap
    }
}