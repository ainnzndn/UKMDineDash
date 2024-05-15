package com.example.ukmdinedash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.ukmdinedash.databinding.TrackOrderBinding

class TrackOrder : AppCompatActivity() {
    private val binding: TrackOrderBinding by lazy {
        TrackOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showGIF()

        binding.btnOrdercomplete.setOnClickListener {
            val intent = Intent(this, Cust_MainMenu::class.java)
            Toast.makeText(this,"Redirecting to Main Menu", Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }

        val countdownTimer = findViewById<TextView>(R.id.timer)
        val timer = object : CountDownTimer(120000, 1000) { // 2 minutes countdown
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                countdownTimer.text = "$minutes:${String.format("%02d", seconds)}"
            }

            override fun onFinish() {
                countdownTimer.text = "00:00"
            }
        }
        timer.start()
    }

    fun showGIF() {
        val imageView:ImageView = findViewById(R.id.hourglass_gif)
        Glide.with(this).load(R.drawable.hourglass).into(imageView)
    }
}
