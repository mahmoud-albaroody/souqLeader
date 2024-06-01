package com.alef.souqleader.ui

import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import com.alef.souqleader.ui.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCookiesTheme {
                // A surface container using the 'background' color from the theme
                val modifier = Modifier.fillMaxSize()
                Surface(
                    modifier = modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    // GymScreen(modifier)
//                    DetailsGymScreen(
//                        modifier,
//                        Gym(1, "3232", Icons.Filled.LocationOn, "ererwe", false)
//                    )
                    GymsAroundApp(modifier = modifier)
                }
            }
        }
    }
}



@Composable
private fun GymsAroundApp(modifier:Modifier){
    val navController = rememberNavController()
    Navigation(navController =  navController,modifier = modifier)
}

