package com.example.expoandes

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class   AddActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        agregar()
    }



    fun agregar(){
        val prefs = this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email=prefs.getString("email",null)
        println(email)
        val nombre=findViewById<EditText>(R.id.articulo_nombre)
        val descripcion = findViewById<EditText>(R.id.articulo_descripcion)
        val precio=findViewById<EditText>(R.id.articulo_precio)
        val texto_precio=precio!!.text
        val texto_descripcion=descripcion!!.text
        val texto_nombre_cosa=nombre!!.text
        val addbtn=findViewById<Button>(R.id.agregarBtn)

        val data_general=db.collection("Cosas_disponibles").document("cosas")


        addbtn.setOnClickListener(){
            val doc_data=db.collection("Mingle_users").document(email.toString()).collection("mis_prestamos").document(texto_nombre_cosa.toString())
            doc_data.addSnapshotListener{snapshot,e->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.exists()) {
                    doc_data.set(
                            hashMapOf("nombre" to texto_nombre_cosa.toString(),
                                   "estado" to "disponible",
                                    "descripcion" to texto_descripcion.toString(),
                                    "precio" to texto_precio.toString()
                            ) as Map<String, Any>
                    )

                }

            }
            doc_data.update(
                    hashMapOf("nombre" to texto_nombre_cosa.toString(),
                            "estado" to "disponible",
                            "descripcion" to texto_descripcion.toString(),
                            "precio" to texto_precio.toString()) as Map<String,Any>
            )

            data_general.update(
                    hashMapOf(texto_nombre_cosa.toString() to email.toString()) as Map<String,Any>
            )




            onBackPressed()
        }
    }




}