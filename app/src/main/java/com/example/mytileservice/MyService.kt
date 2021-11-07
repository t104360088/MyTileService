package com.example.mytileservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyService : Service() {
    private lateinit var nm: NotificationManagerCompat

    override fun onBind(intent: Intent) = null

    override fun onCreate() {
        super.onCreate()

        nm = NotificationManagerCompat.from(this)

        createChannel()
        val notification = NotificationCompat.Builder(this, "Vibrator")
            .setSmallIcon(R.drawable.ic_vibration)
            .setContentTitle("Vibrating")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Go to the quick settings dashboard to turn off the vibrator")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        startForeground(100, notification)
        vibrate(true)
    }

    override fun onDestroy() {
        vibrate(false)
        super.onDestroy()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Vibrator", name, importance)
            nm.createNotificationChannel(channel)
        }
    }

    private fun vibrate(open: Boolean) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =  getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (open) {
            val frequency = longArrayOf(1000, 500)
            if(Build.VERSION.SDK_INT >= 26)
                vibrator.vibrate(VibrationEffect.createWaveform(frequency, 0))
            else
                vibrator.vibrate(frequency, 0)
        } else {
            vibrator.cancel()
        }
    }
}