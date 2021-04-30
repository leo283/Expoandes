package com.example.expoandes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class   AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }
}