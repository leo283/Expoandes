package com.example.expoandes

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class   AddActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        agregar()
    }





    fun agregar(){
        val prefs = this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email=prefs.getString("email",null)
        println(email)
        val nombre=findViewById<EditText>(R.id.articulo_nombre)
        val texto_nombre_cosa=nombre!!.text
        val addbtn=findViewById<Button>(R.id.agregarBtn)
        val doc_data=db.collection("Mingle_users").document(email.toString()).collection("mis_prestamos").document("cosas")
        val data_general=db.collection("Cosas_disponibles").document("cosas")


        addbtn.setOnClickListener(){



            doc_data.addSnapshotListener{snapshot,e->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    doc_data.update(
                            hashMapOf(
                                   texto_nombre_cosa.toString()  to ""
                            ) as Map<String, Any>
                    )

                }
                else {
                    doc_data.set(
                            hashMapOf(texto_nombre_cosa.toString() to "") as Map<String,Any>
                    )

                }
            }

            data_general.addSnapshotListener{snapshot,e->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    data_general.update(
                            hashMapOf(
                                    texto_nombre_cosa.toString() to email
                            ) as Map<String, Any>
                    )

                }
                else {
                    data_general.set(
                            hashMapOf(texto_nombre_cosa.toString() to email) as Map<String,Any>
                    )

                }
            }

            onBackPressed()
        }
    }




}