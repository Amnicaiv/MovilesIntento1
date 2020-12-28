package com.example.organizeit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_modify_data.*
import kotlinx.android.synthetic.main.activity_modify_data.GuardarModifUsu_BTN
import kotlinx.android.synthetic.main.activity_modify_data.edit_Apellidos_Registro
import kotlinx.android.synthetic.main.activity_modify_data.edit_Contra_Registro
import kotlinx.android.synthetic.main.activity_modify_data.edit_Email_Registro
import kotlinx.android.synthetic.main.activity_modify_data.edit_Nombre_Registro
import kotlinx.android.synthetic.main.activity_register.*
import java.io.ByteArrayOutputStream

class ModifyDataActivity : AppCompatActivity() {


    var PICK_IMAGE = 1
    lateinit var decodedBitmap: Bitmap
    lateinit var imageView: ImageView
    var imageLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_data)

        val phoneDB = DBManager(this)
        var userfunc = UsuarioFunciones()
        imageView=this.avatarView

        val userData =phoneDB.getUserData(phoneDB.GetUserId().toString())

        this.edit_Nombre_Registro.setText(userData.nombre)
        this.edit_Apellidos_Registro.setText(userData.apellidos)
        this.edit_Email_Registro.setText(userData.correo)
        this.edit_Contra_Registro.setText(userData.contra)


        var newText = userData.imagen.replace('-', ' ')


        val decodedBytes: ByteArray = Base64.decode(
            userData.imagen.substring(newText.indexOf(",") + 1),
            Base64.DEFAULT
        )

        decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        this.avatarView.setImageBitmap(decodedBitmap)



        this.GuardarModifUsu_BTN.setOnClickListener(){
            Toast.makeText(this, "Intentar guardar.", Toast.LENGTH_SHORT).show()
            val nombreEntrada = this.edit_Nombre_Registro.text.toString()
            val apellidoEntrada = this.edit_Apellidos_Registro.text.toString()
            val correoEntrada = this.edit_Email_Registro.text.toString()
            val contrasenaEntrada = this.edit_Contra_Registro.text.toString()


            val outputStream = ByteArrayOutputStream()
            decodedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            var encodedString = Base64.encodeToString(
                outputStream.toByteArray(),
                Base64.DEFAULT
            )

            var newText = encodedString.replace(' ', '-')
            newText = newText.replace('\t', '-')
            newText = newText.replace('\n', '-')


            phoneDB.GetUserId()?.toInt()?.let { it1 ->
                if(validateForm()){
                    userfunc.tryUserInfoMod(nombreEntrada,apellidoEntrada,correoEntrada,contrasenaEntrada, newText,
                        it1,this,applicationContext)
                }
            }

        }

        this.button9.setOnClickListener(){
            openGallery()
        }

    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
            val uri = data?.data
            decodedBitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri)
            val newBM = getResizedBitmap(decodedBitmap, 250,250)
            if (newBM != null) {
                decodedBitmap = newBM
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



}