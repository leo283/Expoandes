package com.example.expoandes

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isEmpty
import com.google.firebase.firestore.FirebaseFirestore

class CosasPropiasView : AppCompatActivity()  {
    private val db= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cosas_propias_view)
        mostrar_objetos()
    }

    fun mostrar_objetos() {
        val prefs = this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val layout_objetos = findViewById<LinearLayout>(R.id.cosas_propias_full)
        val objetos_prestamo = db.collection("Mingle_users").document(email.toString()).collection("mis_prestamos").document("cosas")
        val hola = db.collection("Mingle_users").document(email.toString()).collection("mis_prestamos").get()
        hola.addOnSuccessListener { resultado->
            if (resultado!=null){
                layout_objetos.removeAllViews()
                for (documento in resultado){
                    val data=documento.data
                    val estado=data["estado"].toString()
                    val nombre= data["nombre"].toString()
                    val texto_nombre = TextView(this)
                    val espacio = Space(this)
                    espacio.minimumHeight=80
                    texto_nombre.text=nombre
                    texto_nombre.textSize=16f
                    texto_nombre.gravity=Gravity.CENTER
                    var colorr=(0xFF000000).toInt()
                    if (estado=="disponible"){colorr=(0xFF51E05D).toInt()}else if(estado=="no disponible"){colorr=(0xFFE13737).toInt()}
                    texto_nombre.setTextColor(colorr)
                    texto_nombre.setTextColor(colorr)

                    layout_objetos.addView(espacio)
                    layout_objetos.addView(texto_nombre)

                    println(nombre)
                }
            }
        }
    }
}