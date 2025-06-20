package com.example.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


//TO be shown for some sec
class OfferService : Service() {
    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler
    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()
        startOfferForegroundService()
        handlerThread = HandlerThread("OfferService")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.post {
            trackSeconds()
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quitSafely()
    }


    fun startOfferForegroundService() {
        notificationBuilder = createNotification()
        createNotificationChannel()
        startForeground(111, notificationBuilder.build())
    }

    fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    //channel id in notificationchannel and createNotification shld be same to show notification
    fun createNotificationChannel(): NotificationChannel {
        val channel =
            NotificationChannel("channelId", "KAANCODES", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return channel
    }

//    fun createNotification(): NotificationCompat.Builder {
//        val notificationBuilder = NotificationCompat.Builder(this, "channelId")
//            .setContentText("Offer You Can't Refuse")
//            .setContentTitle("LIMITED OFFER ON SELECTED ITEMS!!!")
//            .setContentIntent(getPendingIntent())
//            .setOngoing(true)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .build()
//        return notificationBuilder
//    }
    fun createNotification(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, "channelId")
            .setContentText("Offer You Can't Refuse")
            .setContentTitle("LIMITED OFFER ON SELECTED ITEMS!!!")
            .setContentIntent(getPendingIntent())
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
    }


    fun trackSeconds() {
        for (i in 10 downTo 0) {
            Thread.sleep(1000)
            //Notification Update
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationBuilder.setContentText("$i to reedeem offer")
                .setSilent(true)
            notificationManager.notify(111, notificationBuilder.build())
        }
    }
}
