package com.example.noteapp.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import java.util.Timer
import kotlin.concurrent.timerTask


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            },3000
        )

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}