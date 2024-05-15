package com.example.ukmdinedash.adapters

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.widget.Toast

class TimerService: Service() {
    private lateinit var timer: CountDownTimer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Toast.makeText(this, "Countdown stopped", Toast.LENGTH_SHORT).show()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(600000, 1000) { // 10 minutes countdown
            override fun onTick(millisUntilFinished: Long) {
                // Implement any action you want to perform on each tick
            }

            override fun onFinish() {
                // Implement any action you want to perform when the countdown finishes
            }
        }

        timer.start()
        Toast.makeText(this, "Countdown started", Toast.LENGTH_SHORT).show()
    }
}