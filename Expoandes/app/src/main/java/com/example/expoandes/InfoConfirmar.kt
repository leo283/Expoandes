package com.example.expoandes

import android.content.ContentValues
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class InfoConfirmar:AppCompatActivity() {
    private val db=FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_confirmar)

        val prefs = this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email=prefs.getString("email",null)


        val bundle=this.intent?.extras
        val elemento=bundle?.get("elemento").toString()
        val correo_duenio=bundle?.get("correo").toString()
        val texto=findViewById<TextView>(R.id.texto_nombre)
        val text_owner=findViewById<TextView>(R.id.text_owner)
        val precioTxt=findViewById<TextView>(R.id.txtPrecio)
        val descripcionTxt=findViewById<TextView>(R.id.txtDescripcion)
        val boton_prestar=findViewById<Button>(R.id.PrestarBtn)
        val elemento_firebase=db.collection("Mingle_users").document(correo_duenio).collection("mis_prestamos").document(elemento)
        val data_profile = db.collection("Mingle_users").document(email.toString()).collection("prestamos_actuales").document("cosas")
        val doc_data = db.collection("Cosas_disponibles").document("cosas")
        val color=0xFF000000.toInt()
        texto.text=elemento
        texto.gravity=Gravity.CENTER_HORIZONTAL
        text_owner.text="Pertenece a:  "+correo_duenio
        text_owner.setTextColor(color)
        text_owner.textSize=18f
        texto.setTypeface(Typeface.DEFAULT_BOLD)
        texto.textSize=25f

        texto.setTextColor(color)

        elemento_firebase.addSnapshotListener{snapshot,e->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val info=snapshot.data
                if (info!=null){
                    val descripcion=info["descripcion"]
                    val precio=info["precio"].toString()
                    precioTxt.setTextColor(color)
                    precioTxt.textSize=18f
                    precioTxt.text="Precio: $$precio"
                    descripcionTxt.setTextColor(color)
                    descripcionTxt.textSize=18f
                    descripcionTxt.text=descripcion.toString()}

            }else{println("No se que paso")}}


        boton_prestar.setOnClickListener(){
            println(email)
             val data_owner=db.collection("Mingle_users").document(correo_duenio).collection("mis_prestamos").document(elemento)

                            data_profile.addSnapshotListener{snapshot,e->
                                if (e != null) {
                                    Log.w(ContentValues.TAG, "Listen failed.", e)
                                    return@addSnapshotListener
                                }

                                if (snapshot != null && !snapshot.exists()) {
                                    data_profile.set(
                                            hashMapOf(
                                                    elemento to ""
                                            ) as Map<String, Any>
                                    )

                                }


                            }

                            data_profile.update(
                                    hashMapOf(elemento to "") as Map<String,Any>
                            )

                            println(elemento)


                            val updates = hashMapOf<String, Any>(
                                    elemento to FieldValue.delete()
                            )

                            doc_data.update(updates)
                            data_owner.update(
                                    hashMapOf(
                                            "estado" to "no disponible"
                                    )as Map<String,Any>
                            )

            onBackPressed()
        }
    }
}