package com.example.expoandes

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class DevolverActivity : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devolver)
        devolver()
    }

    fun devolver(){
        val color=(0xFF000000).toInt()
        val bundle=this.intent?.extras
        val correo_duenio=bundle?.getString("email")
        val elemento=bundle?.getString("elemento")
        val btnDevolver=findViewById<Button>(R.id.DevolverBtn_dev)
        val email_owner=findViewById<TextView>(R.id.text_owner_dev)
        val elementoTxt=findViewById<TextView>(R.id.texto_nombre_dev)
        val descripcionTxt=findViewById<TextView>(R.id.txtDescripcion_dev)
        val precioTxt=findViewById<TextView>(R.id.txtPrecio_dev)

        val prefs=this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val correo=prefs.getString("email",null)
        email_owner.text="Pertenece a: $correo_duenio"
        email_owner.setTextColor(color)
        email_owner.textSize=18f
        val data_general=db.collection("Cosas_disponibles").document("cosas")
        val duenio_coleccion=db.collection("Mingle_users").document(correo_duenio!!).collection("mis_prestamos").document(elemento!!)
        val user_coleccion=db.collection("Mingle_users").document(correo!!).collection("prestamos_actuales").document("cosas")

        elementoTxt.text=elemento
        elementoTxt.textSize=25f
        elementoTxt.setTextColor(color)


        duenio_coleccion.addSnapshotListener{snapshot,e->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val info=snapshot.data
                if (info!=null){
                    val descripcion=info["descripcion"].toString()
                    val precio=info["precio"].toString()


                    precioTxt.setTextColor(color)
                    precioTxt.textSize=18f
                    precioTxt.text="Precio: $$precio"
                    descripcionTxt.text=descripcion
                    descripcionTxt.setTextColor(color)
                    descripcionTxt.textSize=18f
                        }

            }else{println("No se que paso")}}

        btnDevolver.setOnClickListener(){
            duenio_coleccion.update(
            hashMapOf(
                "estado" to "disponible"
            ) as Map<String, Any>
        )
            user_coleccion.update(
                hashMapOf(
                    elemento.toString() to FieldValue.delete()
                ) as Map<String, Any>
            )

            data_general.update(
                hashMapOf(elemento.toString() to correo_duenio.toString()) as Map<String, Any>
            )

            onBackPressed()
        }
    }



}