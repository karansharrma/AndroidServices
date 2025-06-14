package com.example.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.security.Provider.Service
import kotlin.concurrent.thread

class AndroidServices : android.app.Service() {
    override fun onCreate() {
        super.onCreate()
        Log.d("SERVICELOG", "OnCreate started of android services")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }


    override fun onDestroy() {
        Log.d("SERVICELOG", "OnDestroy called of android services")
        super.onDestroy()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("SERVICELOG", "OnStartCommand called of android services")
//        the below method will stop the service by self
//        stopSelf()

        thread(start = true) {
            while (true) {
                Log.d("SERVICELOG", "LOGGING MESSAGE EVEN AFTER APP IS CLOSED")
                Thread.sleep(2000)

            }
        }
        startForegroundService()
        return super.onStartCommand(intent, flags, startId)
    }


    fun startForegroundService() {
        createNotificationChannel()
        val notification = createNotification()
        startForeground(111, notification)
    }

    fun getpendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }


    fun createNotificationChannel(): NotificationChannel {
        val channel =
            NotificationChannel("ID", "KARANCODES", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return channel
    }

    fun createNotification(): Notification {
        val notification = NotificationCompat.Builder(this, "ID")
            .setContentText("Foreground Services Notification")
            .setContentTitle("KARAN CODES")
            .setContentIntent(getpendingIntent())
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        return notification
    }


}