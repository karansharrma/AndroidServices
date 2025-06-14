package com.example.services

import android.app.Service
import android.content.Intent
import android.os.IBinder


//TO be shown for some sec
class OfferService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}