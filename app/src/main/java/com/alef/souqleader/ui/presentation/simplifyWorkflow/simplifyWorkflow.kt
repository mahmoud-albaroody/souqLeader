package com.alef.souqleader.ui.presentation.simplifyWorkflow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alef.souqleader.R
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.White


@Composable
fun SimplifyScreen(navController: NavController, modifier: Modifier) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    SimplifyItem(navController)
}


@Composable
fun SimplifyItem(navController: NavController) {
    var stat by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var cs = "Simplify your sales workflow"
    var skip = "Skip"
    var image = R.drawable.walkthrow1
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),

        ) {
        if (stat) {
            skip = ""
            cs = "Stay ahead of the competition one click"
            image = R.drawable.walkthrow2
        }
        Row(
            Modifier
                .align(Alignment.End)
                .padding(end = 32.dp, top = 32.dp)
        ) {
            Text(
                text = skip,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 15.sp, fontWeight = FontWeight.Bold
                ),
            )
        }
        Text(
            text = cs, style = androidx.compose.ui.text.TextStyle(
                fontSize = 25.sp, color = Blue, fontWeight = FontWeight.Bold
            ), modifier = Modifier.padding(horizontal = 38.dp, vertical = 24.dp)
        )
        Image(
            painterResource(image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2.9f)
        )
        Text(
            text = "Effortlessly manage sales leads and streamline your sales process with our powerful Souq Leader.",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 15.sp
            ),
            modifier = Modifier.padding(horizontal = 38.dp, vertical = 32.dp)
        )
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 38.dp, vertical = 40.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(Blue),
            onClick = {
                if (stat) {
                    navController.navigate(Screen.LoginScreen.route)
                } else {
                    stat = true
                }

            }) {
            Text(text = "NEXT", Modifier.padding(vertical = 8.dp))
        }

    }
}