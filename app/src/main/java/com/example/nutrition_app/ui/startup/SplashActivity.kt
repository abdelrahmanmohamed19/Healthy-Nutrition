package com.example.nutrition_app.ui.startup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrition_app.R
import com.example.nutrition_app.ui.MainActivity

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)
        Handler().postDelayed(
            {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            },1500)
    }



}