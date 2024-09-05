package com.alef.souqleader.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                //   MyApp(modifier = modifier,navController,mainViewModel)
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
