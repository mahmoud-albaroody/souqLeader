package com.alef.souqleader.ui.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import com.alef.souqleader.R
import java.util.concurrent.TimeUnit

class TimerBroadCast : Service() {
    internal var bi = Intent(COUNTDOWN_BR)
    private var cdt: CountDownTimer? = null
    private var mContext: Context? = null
    override fun onCreate() {
        super.onCreate()
        mContext = this
        Log.i(TAG, "Starting timer...")
        cdt = object : CountDownTimer((30 * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                @SuppressLint("DefaultLocale") val s = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                )
                Log.e("onTick: ", s)
                bi.putExtra("countdown", s)
                sendBroadcast(bi)
            }

            override fun onFinish() {
                Log.i(TAG, "Timer finished")
               // bi.putExtra("countdown", getString(R.string.try_again))
                mContext!!.stopService(Intent(mContext, TimerBroadCast::class.java))
                sendBroadcast(bi)
            }
        }
        cdt!!.start()
    }


    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    companion object {
        private val TAG = "BroadcastService"
        val COUNTDOWN_BR = "your_package_name.countdown_br"

    }

}
