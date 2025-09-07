package com.example.services

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val demoWorker = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        doWork()

        //accessing permission for request 
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
            200
        )

        findViewById<Button>(R.id.startService).setOnClickListener {
            startForegroundService(Intent(this, AndroidServices::class.java))
        }

        findViewById<Button>(R.id.stopService).setOnClickListener {
            stopService(Intent(this, AndroidServices::class.java))
        }


        findViewById<Button>(R.id.ShowOfferButton).setOnClickListener {
            startForegroundService(Intent(this, OfferService::class.java))
        }
        findViewById<Button>(R.id.IntentToWebView).setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        findViewById<Button>(R.id.ImageEncoder).setOnClickListener {
            startActivity(Intent(this, ImageEncodeActivity::class.java))
        }
    }

    private fun doWork() {

        /* val request= PeriodicWorkRequest.Builder(DemoWorker::class.java,15,TimeUnit.MINUTES for requests which is  periodic
        *  For periodic work manager call we have to set minimum interval of 15 minutes else it will show error
        *
        **/
        val request = OneTimeWorkRequest.Builder(DemoWorker::class.java)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            /* setBackoffCriteria s use to set retry time of calling worker*/
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
            .build()
        demoWorker.enqueue(request)
        /*demoWorker.beginWith(request).then(request).then(request) use to call worker one by one as per our request*/
        demoWorker.beginWith(request).then(request).then(request)



        demoWorker.getWorkInfoByIdLiveData(request.id).observe(this) {
            if (it != null) {
                printStats(it.state.name);
            }
        }
    }

    fun printStats(name: String) {
        Log.d("STATUS_OF_WORK_MANAGER", name)
    }
}