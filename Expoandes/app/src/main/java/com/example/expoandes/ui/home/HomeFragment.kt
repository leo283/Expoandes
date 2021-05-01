package com.example.expoandes.ui.home

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        val color=0xFFB59F96.toInt()
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



        val doc_data = db.collection("Cosas_disponibles").document("cosas")
        val data_profile = db.collection("Mingle_users").document(email.toString()).collection("prestamos_actuales").document("cosas")

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
                    for (i in datos-1) {

                        println(context)

                        val nuevo_i=i.toString()
                        val lista=nuevo_i.split("=")
                        val email_owner=lista[1]
                        val variable=lista[0]


                        if(email.toString()!=email_owner){
                        if (activity!=null){
                        val cosa = TextView(activity?.application)


                        cosa.textSize = 16f
                        cosa.text = lista[0]
                        cosa.height = 200




                        val color_texto=0xFF000000.toInt()
                        cosa.setTextColor(color_texto)
                        cosa.gravity = Gravity.CENTER_HORIZONTAL



                        val espacio=Space(activity)
                        espacio.minimumHeight=100


                        if (layout.isEmpty()){layout.addView(espacio)}
                        layout.addView(cosa)





                        cosa.setOnClickListener(){
                            val data_owner=db.collection("Mingle_users").document(email_owner).collection("mis_prestamos").document("cosas")

                            data_profile.addSnapshotListener{snapshot,e->
                                if (e != null) {
                                    Log.w(ContentValues.TAG, "Listen failed.", e)
                                    return@addSnapshotListener
                                }

                                if (snapshot != null && snapshot.exists()) {
                                    data_profile.update(
                                            hashMapOf(
                                                    variable to ""
                                            ) as Map<String, Any>
                                    )

                                }
                                else {
                                    data_profile.set(
                                            hashMapOf(variable to "") as Map<String,Any>
                                    )

                                }
                            }
                            println(variable)


                            val updates = hashMapOf<String, Any>(
                                    variable to FieldValue.delete()
                            )

                            doc_data.update(updates)
                            data_owner.update(updates)
                           // db.collection("Mingle_users").document()
                        }}}
                        //println(i)


                    }
                }

            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }


            return root
        }
}