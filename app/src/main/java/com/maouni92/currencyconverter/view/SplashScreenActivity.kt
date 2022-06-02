package com.maouni92.currencyconverter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.maouni92.currencyconverter.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Hide status bar
       window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

       Handler(Looper.getMainLooper()).postDelayed({
           val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
           finish()
       },3000)

    }
}