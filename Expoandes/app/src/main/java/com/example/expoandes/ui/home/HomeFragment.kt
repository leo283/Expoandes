package com.example.expoandes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.expoandes.R
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val boton_prueba=root.findViewById<Button>(R.id.boton_prueba)

        boton_prueba.setOnClickListener(){
            println("hola")
            db.collection("Mingle_users").document("prueba").update(
                hashMapOf("qiiii" to "hola") as Map<String, Any>
            ) 
        }
        return root
    }
}