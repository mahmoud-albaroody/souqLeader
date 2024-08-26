package com.alef.souqleader.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import com.alef.souqleader.ui.presentation.mainScreen.MyApp
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Determine the layout direction based on the current locale
        val layoutDirection = if (AccountData.lang == "ar") {
            LayoutDirection.Rtl
        } else {
            LayoutDirection.Ltr
        }
        this.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
        updateLocale(this, Locale(AccountData.lang))
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setContent {
            AndroidCookiesTheme {
                // A surface container using the 'background' color from the theme
                val modifier = Modifier.fillMaxSize()
                Surface(
                    modifier = modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                        MyApp(modifier = modifier)
                    }
                }
            }
        }
    }
//        override fun attachBaseContext(newBase: Context) {
//        super.attachBaseContext(
//            LocaleHelper.setLocale(newBase, AccountData.lang)
//        )
//    }

}

//@Composable
//private fun GymsAroundApp(modifier: Modifier) {
//    val navController = rememberNavController()
//    Navigation(navController = navController, modifier = modifier)
//}
//
