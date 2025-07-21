package com.alef.souqleader.ui.services

import android.util.Log
import com.alef.souqleader.domain.model.AccountData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle FCM messages here.
        Log.d("FCM", "From: ${remoteMessage.from}")
        // Check if message contains a data payload.
        remoteMessage.data.isNotEmpty().let {
            Log.d("FCM", "Message data payload: " + remoteMessage.data)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("FCM", "Message Notification Body: ${it.body}")
        }

        // Handle message and show notification
        sendNotification(remoteMessage.notification?.body,
            remoteMessage.notification?.title,remoteMessage.data)
    }

    override fun onNewToken(token: String) {
        Log.e("FCM", token.toString())
        AccountData.firebase_token = token
        // Send token to server if necessary
    }


}
