package com.example.expoandes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.datatransport.runtime.dagger.internal.MapBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    private val db=FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        registrar()
    }

    fun registrar(){
        val nameEditText=findViewById<EditText>(R.id.nameSignUp)
        val emailEditText = findViewById<EditText>(R.id.emailSignUp)
        val passwordEditText = findViewById<EditText>(R.id.passwordSignUp)
        val signUpButton= findViewById<Button>(R.id.btnSignUp)
        val numberEditText = findViewById<EditText>(R.id.phonenumber)
        signUpButton.setOnClickListener(){
        if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && nameEditText.text.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(),
                passwordEditText.text.toString()).addOnCompleteListener(){
                if (it.isSuccessful){
                    db.collection("Mingle_users").document(emailEditText.text.toString()).set(
                        hashMapOf(
                            "nombre" to nameEditText.text.toString(),
                            "telefono" to numberEditText.text.toString()
                        )
                    )
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
        val dialog: AlertDialog =builder.create()
        dialog.show()

    }
    fun showHome(email:String,provider:ProviderType){
        val homeIntent = Intent(this,MainActivity::class.java).apply{
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }
}