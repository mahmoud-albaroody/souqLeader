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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.allLeads.AddCallDetailsDialog
import com.alef.souqleader.ui.presentation.mainScreen.CustomModalDrawer
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.presentation.mainScreen.SplashScreen
import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject
import retrofit2.http.Body
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    private var receiver: CallReceiver? = null
    var addContactLauncher: ActivityResultLauncher<Intent>? = null
    private val viewModel: MainViewModel by viewModels()

    var title: String? = null
    var body: String? = null
    var dataJson: String? = null
    private val deepLinkUri = mutableStateOf<Uri?>(null)

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("sssfffffy", intent?.data.toString())
        //  handleNotificationIntent(intent)
        deepLinkUri.value = intent?.data


        val startDestinationArgs = intent?.extras
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

        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
        }
        updateLocale(this, Locale(AccountData.lang))
        setContent {
            val navController = rememberNavController()

            LaunchedEffect(deepLinkUri.value) {
                deepLinkUri.value?.let { uri ->

                    handleDeepLinkUri(navController, uri)
                    deepLinkUri.value = null
                }
            }
            Start(navController =  navController)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val uri = intent.data
        Log.e("sssfffff", intent.data.toString())
        Log.e("DeepLink", "Intent data = $uri")
        deepLinkUri.value = intent.data

        //handleNotificationIntent(intent)

    }

     private fun handleDeepLinkUri(navController: NavController, uri: Uri) {
        if (uri.scheme == "myapp" && uri.host == "details") {
            val jsonData = uri.pathSegments.joinToString("/")
            jsonData.let {
                try {
                    val jsonObject = JSONObject(it)
                    val value1 = jsonObject.getString("page_name")
                    val value2 = jsonObject.getString("page_id")
                    Log.e("DeeplinkData", "value1=$value1, value2=$value2")
                    when (value1) {
                        "lead-view" -> {
                            navController.navigate(
                                Screen.LeadDetailsScreen.route.plus("/$value2")
                            )
                        }
                        "post" -> {
                            Screen.CRMScreen.title = "Timeline"
                            navController.navigate(
                                Screen.CRMScreen.route
                                    .plus("?" + Screen.CRMScreen.objectName + "=${value2}")
                            )
                        }
                        else -> {
                            Screen.CRMScreen.title = "companyTimeline"
                            navController.navigate(
                                Screen.CRMScreen.route
                                    .plus("?" + Screen.CRMScreen.objectName + "=${value2}")
                            )
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    //    private fun handleNotificationIntent(intent: Intent?) {
//         title = intent?.getStringExtra("notification_title")
//         body = intent?.getStringExtra("notification_body")
//         dataJson = intent?.getStringExtra("custom_data")
//
//    }
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        // Request permissions if any are needed
        if (requiredPermissions.isNotEmpty()) {
            requestPermissionsLauncher.launch(requiredPermissions.toTypedArray())
        }
    }


}

@Composable
fun Start(splash: String? = null,navController: NavHostController) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val viewModel: SharedViewModel = hiltViewModel()
    val ctx = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
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
                                    .plus("?" + Screen.AddCallLogScreen.objectName)
                            )

                        }
                    )

                    if (isVisible) {
                        CustomModalDrawer(
                            modifier, navController, viewModel,
                            SnapshotStateList(), mainViewModel
                        )
                    } else {
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
