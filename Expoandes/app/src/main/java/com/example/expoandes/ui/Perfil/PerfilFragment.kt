package com.example.expoandes.ui.Perfil

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.expoandes.AddActivity
import com.example.expoandes.MainActivity
import com.example.expoandes.R
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class PerfilFragment : Fragment() {

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


        return root
    }
}