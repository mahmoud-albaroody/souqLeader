package com.alef.souqleader.ui

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.mainScreen.CustomModalDrawer
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.presentation.mainScreen.MyApp
import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Determine the layout direction based on the current locale
        //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        checkAndRequestPermissions()
        window.requestFeature(android.view.Window.FEATURE_NO_TITLE)

//        this.window.setSoftInputMode(
//            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
//        )

        updateLocale(this, Locale(AccountData.lang))


        //     setForceDarkAllowed()
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setContent {
            Start()
        }
    }
//        override fun attachBaseContext(newBase: Context) {
//        super.attachBaseContext(
//            LocaleHelper.setLocale(newBase, AccountData.lang)
//        )
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
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
            requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        // Request permissions if any are needed
        if (requiredPermissions.isNotEmpty()) {
            requestPermissionsLauncher.launch(requiredPermissions.toTypedArray())
        }
    }
}

@Composable
fun Start() {
    val mainViewModel: MainViewModel = hiltViewModel()
    val viewModel: SharedViewModel = hiltViewModel()
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
                    CustomModalDrawer(
                        modifier, navController, viewModel,
                        SnapshotStateList(), mainViewModel
                    )
                }

            }
        }
    }
}

//@Composable
//private fun GymsAroundApp(modifier: Modifier) {
//    val navController = rememberNavController()
//    Navigation(navController = navController, modifier = modifier)
//}
//
