package com.example.expoandes.ui.notifications

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.expoandes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val barra=activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        //Después del 0xFF es cuando se pone el color en hexadecimal
        val color=0xFFA4A1C0.toInt()
        barra?.itemBackground= ColorDrawable(color)

        fun animation(imageView:LottieAnimationView,animation:Int){
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }
        val animacion_esperar = root.findViewById<LottieAnimationView>(R.id.animacion_esperar)
        animation(animacion_esperar, R.raw.mexican_fella)
        animacion_esperar.setRepeatCount(LottieDrawable.INFINITE)

        return root
    }
}