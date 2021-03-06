package com.example.expoandes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.airbnb.lottie.LottieAnimationView


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //getSupportActionBar()?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setTheme(R.style.Theme_Expoandes_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashh()
        Handler().postDelayed({
            val intent = Intent(this,AuthActivity::class.java)
            startActivity(intent)
            finish()
        } , 3500)

    }
    fun splashh(){
        val uniandes_animacion = findViewById<LottieAnimationView>(R.id.animacion_splash)
        animation(uniandes_animacion, R.raw.splash_facultad)

    }
    fun animation(imageView:LottieAnimationView,animation:Int){
        imageView.setAnimation(animation)
        imageView.playAnimation()
    }
    }


