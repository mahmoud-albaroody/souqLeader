package com.alef.souqleader.ui.presentation.dashboardScreen

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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.data.remote.dto.LeadStatus
import com.alef.souqleader.ui.constants.Constants.BASE_URL
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.login.SampleNameProvider
import com.alef.souqleader.ui.theme.Blue1
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.White

@Composable
fun DashboardScreen(navController: NavController) {
    val viewModel: DashboardViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.getLeads()
    }

    LazyVerticalGrid(
        GridCells.Fixed(2),
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        items(viewModel.stateListOfLeads) {
            MyCardItem(it) {
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

//@Preview
@Composable
fun MyCardItem(
    leadStatus: LeadStatus,
    @PreviewParameter(SampleNameProvider::class) onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

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
                        colors = listOf(Blue1, Blue2), // Gradient colors
                        startY = 0f, // Starting Y position of the gradient
                        endY = 450f // Ending Y position of the gradient
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
                        text = leadStatus.leads_count.toString(),
                        Modifier.padding(bottom = 8.dp),
                        style = TextStyle(
                            fontSize = 24.sp, color = White, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = leadStatus.getTitle(), style = TextStyle(color = White)
                    )
                }
                Column(Modifier.padding(16.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(BASE_URL + leadStatus.icon),
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                }
            }
            Row {
                LinearProgressIndicator(
                    progress = { leadStatus.getPer() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp, vertical = 12.dp
                        )
                        .padding(bottom = 11.dp),
                    color = White,
                    trackColor = Blue1

                )
            }
        }
    }
}
