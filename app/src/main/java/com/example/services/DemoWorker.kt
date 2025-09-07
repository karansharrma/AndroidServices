package com.example.services

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class DemoWorker(context: Context,parameters: WorkerParameters):Worker(context,parameters) {
    override fun doWork(): Result {
       performWork()
        /*we can also return Result.success or Result.failure or Result.retry
      if we use
       Result.retry-> Worker will restart after some time
       Result.success-> will return a success result of worker


       Note:- We can also set the retry time to retry some request,where we are calling worker
       (MainActivity here)
       */
        return Result.success()
    }
    fun performWork(){
        Thread.sleep(2000)
        Log.d("WORKERISON","TASK COMPLETED")
    }
}