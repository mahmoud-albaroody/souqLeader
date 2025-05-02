package com.alef.souqleader.ui.services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.presentation.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date


class CallReceiver : PhoneCallReceiver() {

    override fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?) {
        incoming_number = number
        val intent = Intent("com.testing.firewall.CALL_RECEIVED")
        intent.putExtra("number", number)
        ctx?.sendBroadcast(intent) // Use sendBroadcast() for global, or LocalBroadcastManager for local
    }

    companion object {
        private const val TAG = "com.testing.firewall"
        var incoming_number: String? = null
    }
}