package com.alef.souqleader.ui.presentation.paymentPlans

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alef.souqleader.R
import com.alef.souqleader.ui.presentation.gymScreen.GymViewModel
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.Grey
import com.alef.souqleader.ui.theme.White

@Composable
fun PaymentPlansScreen(modifier: Modifier) {
    val viewModel: GymViewModel = viewModel()

//    LaunchedEffect(key1 = true) {
//        // viewModel.getGym()
//    }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        items(6) {
            PaymentPlansItem()
//                modifier, listOfGym[it]) { gym ->
////                viewModel.toggleFav(gym)
//                onclick(gym)
//            }
        }
    }
}

@Preview
@Composable
fun PaymentPlansItem() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 4.8f)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .weight(2f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(2f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 16.dp,
                            horizontal = 16.dp)
                ) {
                    Text(
                        text = "499.99 / year", style = TextStyle(
                            fontSize = 18.sp, color = Blue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "All-Access Pass", style = TextStyle(
                            fontSize = 13.sp, fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Image(
                    painterResource(R.drawable.select_box),
                    contentDescription = "",
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "You will get unlimit access to every module you want, 50-100 user",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Grey
                    )

                )

            }
        }
    }
}
