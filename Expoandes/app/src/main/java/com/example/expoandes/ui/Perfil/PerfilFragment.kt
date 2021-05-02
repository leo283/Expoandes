package com.example.expoandes.ui.Perfil

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.expoandes.*
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class PerfilFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()

    private lateinit var perfilViewModel: PerfilViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val bundle = activity?.intent?.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)?.edit()
        prefs?.putString("email", email)
        prefs?.putString("provider", provider)
        prefs?.apply()



        val barra=activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        //Después del 0xFF es cuando se pone el color en hexadecimal
        val color=0xFF9AD2CE.toInt()
        barra?.itemBackground=ColorDrawable(color)

        perfilViewModel =
                ViewModelProvider(this).get(PerfilViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_perfil, container, false)

        val addBtn = root.findViewById<Button>(R.id.addBtn)
        val logoutBtn = root.findViewById<Button>(R.id.logOutBtn)
        val correo = root.findViewById<TextView>(R.id.correo)


        correo.text="  "+email.toString()+"  "

        addBtn.setOnClickListener(){
            val cambio_pantalla = Intent(activity,AddActivity::class.java)
            startActivity(cambio_pantalla)
        }

        logoutBtn.setOnClickListener() {

            FirebaseAuth.getInstance().signOut()
            prefs?.clear()
            prefs?.apply()

            activity?.finish()
        }

        val data_prestamos = db.collection("Mingle_users").document(email.toString()).collection("prestamos_actuales").document("cosas")
        val data_cosas_propias=db.collection("Mingle_users").document(email.toString()).collection("mis_prestamos").document("cosas")
        val layout_cosas_propias=root.findViewById<LinearLayout>(R.id.mis_cosas)
        val layout_prestamos=root.findViewById<LinearLayout>(R.id.prestamos)
        data_prestamos.addSnapshotListener{snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")
                val datos=snapshot.data
                if (datos!=null) {
                    var a=1
                    layout_prestamos.removeAllViews()
                    for (cosa in datos) {
                        if (a<=5) {
                            if (activity!=null) {
                                val texto_add = TextView(activity?.application)
                                texto_add.textSize = 16f
                                texto_add.text = cosa.key.toString().capitalize(Locale.ROOT)
                                texto_add.gravity = Gravity.CENTER_HORIZONTAL
                                val color=(0xFF000000).toInt()
                                texto_add.setTextColor(color)
                                val espacio= Space(activity?.application)
                                espacio.minimumHeight=10



                                layout_prestamos.addView(espacio)
                                layout_prestamos.addView(texto_add)
                                println(cosa.key)
                                a++
                            }
                        }
                    }
                }

            }

        }
        data_cosas_propias.addSnapshotListener{snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")
                val datos=snapshot.data
                if (datos!=null) {

                    layout_cosas_propias.removeAllViews()
                    var a=1
                    for (cosa in datos) {

                        if (a<=5) {
                            if (activity!=null) {
                                val texto_add = TextView(activity?.application)
                                val espacio= Space(activity?.application)
                                espacio.minimumHeight=10
                                texto_add.textSize = 15f
                                texto_add.text = cosa.key.toString()
                                texto_add.gravity = Gravity.CENTER_HORIZONTAL
                                val color=(0xFF000000).toInt()
                                texto_add.setTextColor(color)

                                layout_cosas_propias.addView(espacio)
                                layout_cosas_propias.addView(texto_add)
                                println(cosa.key)
                                a++
                            }
                        }
                    }
                }

            }

        }



        layout_cosas_propias.setOnClickListener(){
            val ver_cosas_propias = Intent(activity, CosasPropiasView::class.java)
            startActivity(ver_cosas_propias)
        }

        layout_prestamos.setOnClickListener(){
            val ver_prestamos=Intent(activity, PrestamosView::class.java)
            startActivity(ver_prestamos)
        }

        return root
    }
}