package com.alef.souqleader.ui.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.NotificationData
import com.alef.souqleader.ui.MainActivity
import org.json.JSONObject

fun MyFirebaseMessagingService.sendNotification(messageBody: String?,title: String?,data: Map<String, String>) {
    val channelId = "default_channel_id"
    val channelName = "Default Channel"
    val notificationId = 1

//    val intent = Intent(this, MainActivity::class.java).apply {
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        putExtra("notification_title", title)
//        putExtra("notification_body", messageBody)
//        putExtra("custom_data", JSONObject(data).toString()) // serialize map
//    }
//    val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent,
//        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//    )
    val jsonData = JSONObject(data).toString()
    val deepLink = Uri.parse("myapp://details/$jsonData")
    val intent = Intent(Intent.ACTION_VIEW, deepLink).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    val pendingIntent = PendingIntent.getActivity(
        this,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.souq_leader_logo_new_logo_same_size)

    val builder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.souq_leader_logo_new_logo_same_size)
        .setLargeIcon(largeIconBitmap)
        .setContentTitle(title)
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)


        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)


    with(NotificationManagerCompat.from(this)) {
        if (ActivityCompat.checkSelfPermission(
                this@sendNotification,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(notificationId, builder.build())
    }
}