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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alef.souqleader.R
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.White


@Composable
fun SimplifyScreen(modifier: Modifier) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    SimplifyItem()
}

@Preview
@Composable
fun SimplifyItem() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),

        ) {
        Row(
            Modifier
                .align(Alignment.End)
                .padding(end = 32.dp, top = 32.dp)
        ) {
            Text(
                text = "Skip",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 15.sp, fontWeight = FontWeight.Bold
                ),
            )
        }

        Text(
            text = "Simplify your sales workflow", style = androidx.compose.ui.text.TextStyle(
                fontSize = 25.sp, color = Blue, fontWeight = FontWeight.Bold
            ), modifier = Modifier.padding(horizontal = 38.dp, vertical = 24.dp)
        )
        Image(
            painterResource(R.drawable.test_icon),
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
            onClick = { /*TODO*/ }) {
            Text(text = "NEXT", Modifier.padding(vertical = 8.dp))
        }

    }
}