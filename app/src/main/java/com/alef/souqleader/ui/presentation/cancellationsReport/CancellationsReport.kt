package com.alef.souqleader.ui.presentation.cancellationsReport

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.CancelationReport
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.ui.presentation.dashboardScreen.DashboardViewModel
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.White

@Composable
fun CancellationsReport(navController: NavController, modifier: Modifier) {
    val viewModel: CancellationReportViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.getCancellationReport("5")
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        viewModel.cancellationStatus?.let {
            Cancellations(it)
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                items(it.leads) { lead ->
                    CancellationsReportItem(lead)
//                modifier, listOfGym[it]) { gym ->
////                viewModel.toggleFav(gym)
//                onclick(gym)
//            }
                }
            }
        }
    }

}

@Composable
fun CancellationsReportItem(lead: Lead) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 5.2f)
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            Modifier
                .weight(4f)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1.8f)
                    .padding(
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    painterResource(R.drawable.user_profile_placehoder),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(end = 8.dp)
                        .size(50.dp)
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = lead.name, style = TextStyle(
                            fontSize = 15.sp, color = Blue
                        )
                    )
                    Text(
                        text = lead.phone, style = TextStyle(
                            fontSize = 13.sp,
                        )
                    )
                }

            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(horizontal = 3.dp),
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
                        text = lead.sales_name,
                        style = TextStyle(
                            fontSize = 12.sp,
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painterResource(R.drawable.project_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = lead.project_name,
                        style = TextStyle(
                            fontSize = 12.sp,
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Image(
                        painterResource(R.drawable.vuesax_linear_calendar),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(start = 4.dp)
                    )
                    Text(
                        text = lead.action_date,
                        style = TextStyle(
                            fontSize = 12.sp,
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 6.dp, top = 4.dp)
                        .padding(start = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Blue2),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 4.dp),
                            text = "PERMISSION", style = TextStyle(
                                fontSize = 11.sp,
                                color = White
                            )
                        )
                        Image(
                            painterResource(R.drawable.drop_menu_icon),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Cancellations(cancelationReport: CancelationReport) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = cancelationReport.total_canceled,
                style = TextStyle(
                    fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold
                ),
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.total_of_meeting),
                style = TextStyle(
                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                ),
            )
            Column(
                Modifier.padding(top = 24.dp), verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.total_activities),
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                    Text(
                        text = cancelationReport.total_activity,
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Text(
                        text = stringResource(R.string.total_actions),
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                    Text(
                        text = cancelationReport.total_actions,
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                }
            }

        }
    }
}