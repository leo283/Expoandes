package com.example.expoandes.ui.home

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.expoandes.InfoConfirmar
import com.example.expoandes.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("ResourceType")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val barra=activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        //Después del 0xFF es cuando se pone el color en hexadecimal
        val color=0xFF97BDDE.toInt()
        barra?.itemBackground= ColorDrawable(color)
        val bundle = activity?.intent?.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        //Guardado de datos
        val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)?.edit()
        prefs?.putString("email", email)
        prefs?.putString("provider", provider)
        prefs?.apply()

        //botones
        var categoriaa="nada"

        val cat_libros=root.findViewById<RadioButton>(R.id.libros_home)
        val cat_electronicos=root.findViewById<RadioButton>(R.id.electronico_home)
        val cat_material_escolar=root.findViewById<RadioButton>(R.id.material_escolar_home)
        val grupo_botones=root.findViewById<RadioGroup>(R.id.btn_group_home)
        val todo_btn=root.findViewById<RadioButton>(R.id.btn_todo)





        val doc_data = db.collection("Cosas_disponibles").document("cosas")
        val data_profile = db.collection("Mingle_users").document(email.toString()).collection("prestamos_actuales").document("cosas")
        categoriaa="electronicos"


        fun mostrar_todo(){doc_data.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")


                val datos = (snapshot.data)
                if (datos != null) {
                    val layout = root.findViewById<LinearLayout>(R.id.layout)
                    layout.removeAllViews()
                    for (i in datos) {

                        val nuevo_i=i.toString()
                        val lista=nuevo_i.split("=")
                        val email_owner=lista[1]

                        if(email.toString()!=email_owner ){

                            if (activity!=null){


                                val cosa = TextView(activity?.application)

                                cosa.textSize = 16f
                                cosa.text = lista[0]
                                cosa.height = 200


                                val color_texto=0xFF000000.toInt()
                                cosa.setTextColor(color_texto)
                                cosa.gravity = Gravity.CENTER
                                cosa.setBackgroundResource(R.drawable.border)



                                val espacio=Space(activity)
                                espacio.minimumHeight=100
                                val mini_espacio=Space(activity)
                                mini_espacio.minimumHeight=12


                                if (layout.isEmpty()){layout.addView(espacio)}

                                layout.addView(cosa)
                                layout.addView(mini_espacio)



                                cosa.setOnClickListener(){
                                    val ir_a_info = Intent(activity,InfoConfirmar::class.java).apply{
                                        putExtra("elemento",cosa.text)
                                        putExtra("correo",lista[1].toString())
                                    }
                                    startActivity(ir_a_info)

                                    // db.collection("Mingle_users").document()
                    }
                }else{println("No se que paso")}}

                    //println(i)

                    }
                }

            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }}
        fun select(boton:Button,cadena:String){
        boton.setOnClickListener(){
            categoriaa=cadena
            doc_data.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")


                    val datos = (snapshot.data)
                    if (datos != null) {
                        val layout = root.findViewById<LinearLayout>(R.id.layout)
                        layout.removeAllViews()
                        for (i in datos) {

                            val nuevo_i=i.toString()
                            val lista=nuevo_i.split("=")
                            val email_owner=lista[1]
                            val elemento=i.key
                            val elemento_firebase=db.collection("Mingle_users").document(email_owner).collection("mis_prestamos").document(elemento)
                            var categoria="nada"

                            elemento_firebase.addSnapshotListener{snapshot,e->
                                if (e != null) {
                                    Log.w(ContentValues.TAG, "Listen failed.", e)
                                    return@addSnapshotListener
                                }

                                if (snapshot != null && snapshot.exists()) {
                                    val info=snapshot.data
                                    if (info!=null){


                                        if(email.toString()!=email_owner ){

                                            if (activity!=null){

                                                categoria=info["categoria"].toString()
                                                println(categoria)

                                                val cosa = TextView(activity?.application)

                                                cosa.textSize = 16f
                                                cosa.text = lista[0]
                                                cosa.height = 200


                                                val color_texto=0xFF000000.toInt()
                                                cosa.setTextColor(color_texto)
                                                cosa.gravity = Gravity.CENTER
                                                cosa.setBackgroundResource(R.drawable.border)



                                                val espacio=Space(activity)
                                                espacio.minimumHeight=100
                                                val mini_espacio=Space(activity)
                                                mini_espacio.minimumHeight=12


                                                if (layout.isEmpty()){layout.addView(espacio)}
                                                if(categoria==categoriaa) {
                                                    layout.addView(cosa)
                                                    layout.addView(mini_espacio)
                                                }


                                                cosa.setOnClickListener(){
                                                    val ir_a_info = Intent(activity,InfoConfirmar::class.java).apply{
                                                        putExtra("elemento",cosa.text)
                                                        putExtra("correo",lista[1].toString())
                                                    }
                                                    startActivity(ir_a_info)


                                                    // db.collection("Mingle_users").document()
                                                }}}
                                    }
                                }else{println("No se que paso")}}




                            //println(i)


                        }
                    }

                } else {
                    Log.d(ContentValues.TAG, "Current data: null")
                }
            }
        }}


        todo_btn.setOnClickListener(){
            mostrar_todo()
        }
        mostrar_todo()
        select(cat_libros,"libros")
        select(cat_electronicos,"electronicos")
        select(cat_material_escolar,"material_escolar")




            return root
        }


}