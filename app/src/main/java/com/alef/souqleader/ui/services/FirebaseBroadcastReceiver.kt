package com.alef.souqleader.ui.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class FirebaseBroadcastReceiver : BroadcastReceiver() {
    var count = 0
    val TAG: String = FirebaseBroadcastReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        val dataBundle = intent.extras
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //Log.e("ddd",dataBundle)

//        if (dataBundle?.getString("EntityTypeId") == "100" ||
//            dataBundle?.getString("EntityTypeId") == "9" ||
//            dataBundle?.getString("EntityTypeId") == "101"
//        ) {
//
//            count++
//            val editor: SharedPreferences.Editor =
//                context.getSharedPreferences("co", Context.MODE_PRIVATE).edit()
//            editor.putInt("count", count)
//            editor.apply()
//        }

//        if (dataBundle != null)
//            if (Foreback.isApplicationInTheBackground() &&
//                dataBundle.getString("EntityTypeId") != "18"
//            ) {
//                val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//                v.vibrate(1500)
//                // v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
////                    )
//                try {
//                    val r =
//                        RingtoneManager.getRingtone(
//                            context,
//                            Uri.parse("android.resource://" + context.packageName + "/" + R.raw.iphone_ding)
//                        )
//                    r.play()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//                return
//            }


        //  val remoteMessage = RemoteMessage(dataBundle)


    }
}