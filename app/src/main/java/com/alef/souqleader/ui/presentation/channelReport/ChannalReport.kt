package com.alef.souqleader.ui.presentation.channelReport

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.CancelationReport
import com.alef.souqleader.data.remote.dto.ChannelReport
import com.alef.souqleader.data.remote.dto.Chart
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.domain.model.Channel
import com.alef.souqleader.ui.presentation.cancellationsReport.Cancellations
import com.alef.souqleader.ui.presentation.meetingReport.MeetingLeads
import com.alef.souqleader.ui.presentation.meetingReport.MyBarChart
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.White
import kotlinx.coroutines.launch

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
            MyBarChart(channelReportList, stringResource(R.string.cancellation_report))
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