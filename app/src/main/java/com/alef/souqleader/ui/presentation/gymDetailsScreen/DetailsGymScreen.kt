package com.alef.souqleader.ui.presentation.gymDetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alef.souqleader.R
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue1
import com.alef.souqleader.ui.theme.OffWhite
import com.alef.souqleader.ui.theme.White

@Composable
fun DetailsGymScreen(modifier: Modifier) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()


//    Column (modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally){
//        CardICon(modifier.weight(0.10f), gym = viewModel.gym)
//        CardDetails(modifier.weight(0.80f), gym = viewModel.gym,Arrangement.Top)
//    }
    LazyColumn() {
        items(5) {
            ProjectsItem()
        }
    }


}

@Preview
@Composable
fun ProjectsItem() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3.2f)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
                .weight(4f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(3.4f)) {
                Image(
                    painterResource(R.drawable.test_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(percent = 10))
                )
            }

            Row(
                Modifier
                    .fillMaxSize()
                    .weight(0.9f),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Project Name",
                        style = TextStyle(
                            color = Blue,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Nozha - New Cairo", style = TextStyle(
                            fontSize = 13.sp,
                        )
                    )
                }
                Text(
                    text = "250.000 EGP", style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}