package com.example.expoandes

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class ContactActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactar)
        setup()
    }


    fun setup(){
        val bundle = this.intent?.extras
        val telefono = bundle!!.getString("telefono")
        val elemento = bundle.getString("elemento")
        val chat_wsp=findViewById<WebView>(R.id.chatview)
        chat_wsp.loadUrl("https://wa.me/57$telefono?text=Hola!, vengo de Mingle y me interesa este producto: $elemento")

    }
}