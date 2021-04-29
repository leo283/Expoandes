package com.example.expoandes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class   AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setup()
        session()
    }

    override fun onStart(){
        super.onStart()
        val authLayout=findViewById<LinearLayout>(R.id.auth_layout)
        authLayout.visibility=View.VISIBLE
    }
    fun setup(){
        val signUpButton = findViewById<Button>(R.id.signUpBtn)
        val loginButton = findViewById<Button>(R.id.loginBtn)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        signUpButton.setOnClickListener(){
            if (!(emailEditText.text.toString().toLowerCase(Locale.ROOT).endsWith("uniandes.edu.co"))){
                showAlert_correomal()
            }

            if(passwordEditText.text.isEmpty()){
                showAlert_nocontraseña()
            }
            if (emailEditText.text.isNotEmpty() &&  emailEditText.text.toString().toLowerCase(Locale.ROOT).endsWith("uniandes.edu.co")&& passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(),
                        passwordEditText.text.toString()).addOnCompleteListener(){
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email?:"",ProviderType.BASIC)
                    }

                    else {
                        showAlert()
                    }
                }
            }
        }

        loginButton.setOnClickListener(){
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(),
                        passwordEditText.text.toString()).addOnCompleteListener(){
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email?:"",ProviderType.BASIC)
                    }
                    else {
                        showAlert()
                    }
                }
            }
        }

    }

    fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()

    }
    fun showAlert_correomal(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Su correo no es Uniandes, intente de nuevo")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }
    fun showAlert_nocontraseña(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Por favor escriba algo en el campo de contraseña")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }

    fun showHome(email:String,provider:ProviderType){
        val homeIntent = Intent(this,MainActivity::class.java).apply{
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }

    private fun session(){
        val prefs=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email=prefs.getString("email",null)
        val provider=prefs.getString("provider",null)
        val autLayout=findViewById<LinearLayout>(R.id.auth_layout)
        if (email!=null && provider!=null){
            autLayout.visibility= View.INVISIBLE
            showHome(email,ProviderType.valueOf(provider))
        }
    }
}