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

class PrestamosView : AppCompatActivity(){
    private val db=FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestamos_view)
        mostrar_objetos()
    }

    fun mostrar_objetos() {
        val prefs = this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val layout_objetos = findViewById<LinearLayout>(R.id.prestamos_full_view)
        val objetos_prestamo = db.collection("Mingle_users").document(email.toString()).collection("prestamos_actuales").document("cosas")

        objetos_prestamo.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")


                val datos = (snapshot.data)
                if (datos != null) {
                    layout_objetos.removeAllViews()
                    for (i in datos) {


                        val texto=i.key


                        val cosa = TextView(this.application)

                        cosa.textSize = 16f
                        cosa.text = texto
                        cosa.height = 200

                        val color_texto = 0xFF000000.toInt()
                        cosa.setTextColor(color_texto)
                        cosa.gravity = Gravity.CENTER_HORIZONTAL


                        val espacio = Space(this)
                        espacio.minimumHeight = 100


                        if (layout_objetos.isEmpty()) {
                            layout_objetos.addView(espacio)
                        }
                        layout_objetos.addView(cosa)
                    }
                }
            }
        }
    }


}