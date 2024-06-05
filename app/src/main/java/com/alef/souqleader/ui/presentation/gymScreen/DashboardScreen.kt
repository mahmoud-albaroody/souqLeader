package com.alef.souqleader.ui.presentation.gymScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.domain.model.Gym
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.login.SampleNameProvider
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue1
import com.alef.souqleader.ui.theme.OffWhite
import com.alef.souqleader.ui.theme.White

@Composable
fun DashboardScreen(navController: NavController) {
    val viewModel: DashboardViewModel = viewModel()

//    LaunchedEffect(key1 = true) {
//        // viewModel.getGym()
//    }

    LazyVerticalGrid(
        GridCells.Fixed(2),
        Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        items(6) {
            MyCardItem(){
                navController.navigate(Screen.AllLeadsScreen.route)
            }
//                modifier, listOfGym[it]) { gym ->
////                viewModel.toggleFav(gym)
//                onclick(gym)
//            }
        }
    }
}

class SampleNameProvider(override val values: Sequence<Unit>) :
    PreviewParameterProvider<Unit> {

}

@Preview
@Composable
fun MyCardItem(@PreviewParameter(SampleNameProvider::class) onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val progress by remember { mutableStateOf(0.5f) }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 4.6f)
            .padding(6.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Blue1, Blue), // Gradient colors
                        startY = 0f, // Starting Y position of the gradient
                        endY = 420f // Ending Y position of the gradient
                    )
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = "225", Modifier.padding(bottom = 8.dp), style = TextStyle(
                            fontSize = 24.sp, color = White, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "All Leads", style = TextStyle(color = White)
                    )
                }
                Column(Modifier.padding(16.dp)) {
                    Image(
                        painterResource(R.drawable.all_leads_icon),
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                }
            }
            Row() {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp, vertical = 12.dp
                        )
                        .padding(bottom = 11.dp),
                    color = White,
                    trackColor = OffWhite

                )
            }
        }
    }
}
