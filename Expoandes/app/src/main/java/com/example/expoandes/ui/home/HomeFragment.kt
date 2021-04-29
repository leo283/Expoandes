package com.example.expoandes.ui.home

import android.content.Context
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val bundle = activity?.intent?.extras
        val email= bundle?.getString("email")
        val provider = bundle?.getString("provider")
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)




        //Guardado de datos
        val prefs =activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)?.edit()
        prefs?.putString("email",email)
        prefs?.putString("provider",provider)
        prefs?.apply()

        //botones

        val logoutBtn = root.findViewById<Button>(R.id.logOutBtn)



        logoutBtn.setOnClickListener() {

            FirebaseAuth.getInstance().signOut()
            prefs?.clear()
            prefs?.apply()

            activity?.finish()
        }
        return root
    }
}