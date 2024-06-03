package com.alef.souqleader.ui.presentation.allLeads

import androidx.compose.foundation.Image
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
import com.alef.souqleader.ui.theme.LightGrey
import com.alef.souqleader.ui.theme.White

@Composable
fun AllLeadsScreen(modifier: Modifier) {
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
            AllLeadsItem()
//                modifier, listOfGym[it]) { gym ->
////                viewModel.toggleFav(gym)
//                onclick(gym)
//            }
        }
    }
}

@Preview
@Composable
fun AllLeadsItem() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3f)
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    painterResource(R.drawable.client_placeholder),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 8.dp)
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .padding(top = 6.dp)
                ) {
                    Text(
                        text = "Mahmoud Ali", style = TextStyle(
                            fontSize = 16.sp, color = Blue
                        )
                    )
                    Text(
                        text = "Admin", style = TextStyle(
                            fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                        )
                    )
                }

            }

            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.sales_name_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "2Sales Name",
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painterResource(R.drawable.project_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Project Name",
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painterResource(R.drawable.coin),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Text(
                        text = "\$248,67",
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }


            Row(verticalAlignment = Alignment.Top) {
                Image(
                    painterResource(R.drawable.notes_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consec tetur ad ipisici elit, sed do eiusmod tempor",
                    style = TextStyle(
                        fontSize = 11.sp, color = Grey
                    ),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painterResource(R.drawable.call_icon),
                    contentDescription = "",
                    Modifier.size(60.dp, 40.dp)
                )
                Image(
                    painterResource(R.drawable.sms_icon),
                    contentDescription = "",
                    Modifier.size(60.dp, 40.dp)
                )
                Image(
                    painterResource(R.drawable.mail_icon),
                    contentDescription = "",
                    Modifier.size(60.dp, 40.dp)
                )
                Image(
                    painterResource(R.drawable.whats_icon),
                    contentDescription = "",
                    Modifier.size(60.dp, 40.dp)
                )
            }
        }
    }
}