package com.alef.souqleader.ui.presentation.timeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alef.souqleader.R
import com.alef.souqleader.ui.theme.Blue


@Composable
fun TimelineScreen(modifier: Modifier) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()


//    Column (modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally){
//        CardICon(modifier.weight(0.10f), gym = viewModel.gym)
//        CardDetails(modifier.weight(0.80f), gym = viewModel.gym,Arrangement.Top)
//    }
    LazyColumn() {
        items(5) {
            TimelineItem()
        }
    }


}

@Preview
@Composable
fun TimelineItem() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 2.5f)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
                .weight(6f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.weight(1f)
                    .padding(top=12.dp),
                text = "CRM system and how to management clients and leads",
                style = TextStyle(
                    color = Blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Image(
                painterResource(R.drawable.test_icon),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(percent = 10))
                    .padding(vertical = 4.dp).weight(3.7f),
            )

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .weight(0.8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.like),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = "3 Like", style = TextStyle(
                            fontSize = 13.sp,
                        )
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.message_text),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .padding(end = 8.dp),
                        text = "8 Comment", style = TextStyle(
                            fontSize = 13.sp,
                        )
                    )
                }
            }
        }
    }
}