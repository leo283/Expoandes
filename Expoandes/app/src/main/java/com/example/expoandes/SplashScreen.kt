package com.example.expoandes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        setTheme(R.style.Theme_Expoandes)
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


