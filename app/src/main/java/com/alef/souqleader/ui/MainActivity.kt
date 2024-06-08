package com.alef.souqleader.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import com.alef.souqleader.ui.navigation.Navigation
import com.alef.souqleader.ui.presentation.login.LoginScreen
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
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        updateLocale(this, Locale(AccountData.lang))

        setContent {
            AndroidCookiesTheme {
                // A surface container using the 'background' color from the theme
                val modifier = Modifier.fillMaxSize()
                Surface(
                    modifier = modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                        if (AccountData.isFirstTime) {
                            LoginScreen(modifier = modifier)
                            AccountData.isFirstTime = false
                        } else {
                            MyApp(modifier = modifier)
                        }
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

@Composable
private fun GymsAroundApp(modifier: Modifier) {
    val navController = rememberNavController()
    Navigation(navController = navController, modifier = modifier)
}

