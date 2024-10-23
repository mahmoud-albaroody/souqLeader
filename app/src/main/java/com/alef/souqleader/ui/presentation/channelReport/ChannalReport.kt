package com.alef.souqleader.ui.presentation.channelReport


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Chart
import com.alef.souqleader.ui.presentation.meetingReport.MyBarChart


@Composable
fun ChannelReport(navController: NavController, modifier: Modifier) {
    val viewModel: ChannelReportViewModel = hiltViewModel()
    val channelReportList = remember { mutableStateListOf<Chart>() }
    LaunchedEffect(key1 = true) {
        viewModel.channelReport()
//        viewModel.viewModelScope.launch {
//            viewModel.channelReports.collect {
//                it.data?.let { it1 -> channelReportList.addAll(it1) }
//            }
//        }
    }

    viewModel.channelReports?.data?.let { Channel(it) }
}

@Composable
fun Channel(channelReportList: ArrayList<Chart>) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
//        Channel(channelReportList)
        Card(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)

        ) {
            MyBarChart(channelReportList, stringResource(R.string.channel_report))
        }
//            Card(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp, top = 8.dp),
//            ) {
//                Column(
//                    Modifier
//                        .fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//
//                ) {
//                    Text(
//                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
//                        text = "Cancellation Leads",
//                        fontSize = 18.sp, color = Color.Black,
//                        fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
//                    )
//                    LazyColumn(
//                        modifier = Modifier
//                            .heightIn(200.dp, 500.dp), content = {
//                            items(it.leads) { lead ->
//                                MeetingLeads(lead)
//                            }
//                        })
//                }
//            }

    }
}


//@Composable
//fun Channel(channelReportList: ChannelReport) {
//    Card(
//        Modifier
//            .fillMaxWidth()
//            .height(140.dp)
//    ) {
//        Column(
//            Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                text = channelReportList.total_canceled,
//                style = TextStyle(
//                    fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold
//                ),
//            )
//            Text(
//                modifier = Modifier.padding(top = 4.dp),
//                text = stringResource(R.string.total_of_cancellation),
//                style = TextStyle(
//                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold
//                ),
//            )
//            Column(
//                Modifier.padding(top = 24.dp), verticalArrangement = Arrangement.Bottom
//            ) {
//                Row(
//                    Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = stringResource(R.string.total_activities),
//                        style = TextStyle(
//                            fontSize = 13.sp
//                        ),
//                    )
//                    Text(
//                        text = channelReportList.total_activity,
//                        style = TextStyle(
//                            fontSize = 13.sp
//                        ),
//                    )
//                }
//                Row(
//                    Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//
//                    ) {
//                    Text(
//                        text = stringResource(R.string.total_actions),
//                        style = TextStyle(
//                            fontSize = 13.sp
//                        ),
//                    )
//                    Text(
//                        text = channelReportList.total_actions,
//                        style = TextStyle(
//                            fontSize = 13.sp
//                        ),
//                    )
//                }
//            }
//
//        }
//    }
//}