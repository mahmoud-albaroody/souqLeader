package com.alef.souqleader.ui.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.Service.START_STICKY
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.alef.souqleader.R

class CallMonitorService : Service() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, "calls")
            .setContentTitle("Monitoring Calls")
            .setContentText("Listening for phone call events")
            .setSmallIcon(R.drawable.souq_leader_logo_2__1_)
            .build()

//        // Start as foreground service
//        startForeground(1, notification)
//
//        // Start listening for call state
//        val telephony = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//        telephony.listen(MyPhoneStateListener(this), PhoneStateListener.LISTEN_CALL_STATE)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "calls",
                "Call Monitoring",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}