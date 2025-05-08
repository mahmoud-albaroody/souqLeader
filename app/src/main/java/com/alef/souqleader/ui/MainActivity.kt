package com.alef.souqleader.ui

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.allLeads.AddCallDetailsDialog
import com.alef.souqleader.ui.presentation.mainScreen.CustomModalDrawer
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.presentation.mainScreen.SplashScreen
import com.alef.souqleader.ui.services.CallReceiver
import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var receiver: CallReceiver? = null
     var addContactLauncher: ActivityResultLauncher<Intent>? = null
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions()
          addContactLauncher =
              registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && viewModel.isCall) {
                val u = Uri.parse(
                    "tel:" + viewModel.selectedLead
                )

                // Create the intent and set the data for the
                // intent as the phone number.
                val i = Intent(Intent.ACTION_DIAL, u)
                try {
                    // Launch the Phone app's dialer with a phone
                    // number to dial a call.
                    this.startActivity(i)
                } catch (s: SecurityException) {

                    // show() method display the toast with
                    // exception message.
                    Toast
                        .makeText(this, "An error occurred", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Log.d("AddContact", "User canceled contact insert.")
            }
        }

        window.requestFeature(android.view.Window.FEATURE_NO_TITLE)
//        val i = Intent(this@MainActivity, CallMonitorService::class.java)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(i)
//        } else {
//            startService(i)
//        }
//        val telephony = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        telephony.listen(MyPhoneStateListener(this), PhoneStateListener.LISTEN_CALL_STATE)


        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
        }
        updateLocale(this, Locale(AccountData.lang))
        setContent {
            Start()
        }
    }


    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                Toast.makeText(this, "Permissions Granted.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show()
            }
        }

    // Function to check and request
    // necessary permissions
    private fun checkAndRequestPermissions() {
        val requiredPermissions = mutableListOf<String>()

        // Request WRITE_EXTERNAL_STORAGE only
        // for Android 9 and below
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
            requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        // Request permissions if any are needed
        if (requiredPermissions.isNotEmpty()) {
            requestPermissionsLauncher.launch(requiredPermissions.toTypedArray())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        unregisterReceiver(callReceiver)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        val phoneReadStatePermission = applicationContext.checkSelfPermission("READ_PHONE_STATE")
        val readCallLogPermission = applicationContext.checkSelfPermission("READ_CALL_LOG")
        val hasPhoneReadStatePermission =
            phoneReadStatePermission == PackageManager.PERMISSION_GRANTED
        val hasReadCallLogPermission = readCallLogPermission == PackageManager.PERMISSION_GRANTED
        if (!hasPhoneReadStatePermission || !hasReadCallLogPermission) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.READ_PHONE_STATE
                ),
                1
            )
            this.registerPhoneReceiver()
            val filter = IntentFilter("com.testing.firewall.CALL_RECEIVED")
            registerReceiver(callReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            this.registerPhoneReceiver()
            val filter = IntentFilter("com.testing.firewall.CALL_RECEIVED")
            registerReceiver(callReceiver, filter, RECEIVER_NOT_EXPORTED)
        }
    }
    private fun registerPhoneReceiver() {
        val handler = CallReceiver()
        val filter = IntentFilter()
        filter.addAction("android.intent.action.PHONE_STATE")
        registerReceiver(handler, filter)
    }
    private val callReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
         viewModel.showDialog = true
        }
    }
}

@Composable
fun Start(splash: String? = null) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val viewModel: SharedViewModel = hiltViewModel()
    val ctx = LocalContext.current
    var showDialog by remember { mutableStateOf(false)}
    var isVisible by remember { mutableStateOf(false) }
    val layoutDirection = if (AccountData.lang == "ar") {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }

    AndroidCookiesTheme {
        // A surface container using the 'background' color from the theme
        val modifier = Modifier.fillMaxSize()
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                if (AccountData.auth_token == null) {
                    MainScreen(modifier, navController, viewModel, mainViewModel)
                } else {

                    AddCallDetailsDialog(
                        showDialog = mainViewModel.showDialog,
                        onDismiss = {
                            mainViewModel.showDialog = false

                        },
                        onConfirm = {
                           mainViewModel.showDialog = false
                            navController.navigate(
                                Screen.AddCallLogScreen.route
                                    .plus("?" + Screen.AddCallLogScreen.objectName )
                            )

                        }
                    )

                    if (isVisible) {
                        CustomModalDrawer(
                            modifier, navController, viewModel,
                            SnapshotStateList(), mainViewModel
                        )
                    }
                    else {
                        if (splash.isNullOrEmpty()) {
                            SplashScreen(Modifier.fillMaxSize(),
                                navController,
                                sharedViewModel = viewModel,
                                mainViewModel,
                                onSplashEndedValid = {
                                    isVisible = true
                                })

                        } else {
                            CustomModalDrawer(
                                modifier, navController, viewModel,
                                SnapshotStateList(), mainViewModel
                            )
                        }

                    }
                }

            }
        }
    }
}

//class MyPhoneStateListener(val context: Context) : PhoneStateListener() {
//    @Deprecated("Deprecated in Java")
//    override fun onCallStateChanged(state: Int, incomingNumber: String?) {
//        super.onCallStateChanged(state, incomingNumber)
//        when (state) {
//            TelephonyManager.CALL_STATE_RINGING -> {
//                Log.e("sssssee", "Incoming call: $incomingNumber")
//            }
//            TelephonyManager.CALL_STATE_OFFHOOK -> {
//                Log.e("ssssseeeee", "Call answered or outgoing")
//            }
//            TelephonyManager.CALL_STATE_IDLE -> {
//                Log.e("ssssseee", "Call ended or idle")
//            }
//        }
//    }
//}
