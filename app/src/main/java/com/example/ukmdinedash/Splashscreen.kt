package com.example.ukmdinedash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@Suppress("DEPRECATION")
class Splashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Login_Customer::class.java)
            startActivity(intent)
            finish()
        },3000)

    }
}