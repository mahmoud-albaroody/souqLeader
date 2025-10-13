package com.alef.souqleader.ui.presentation.channelReport


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.ChannelReport
import com.alef.souqleader.data.remote.dto.Chart
import com.alef.souqleader.data.remote.dto.ProjectChart
import com.alef.souqleader.ui.presentation.meetingReport.MeetingLeads
import com.alef.souqleader.ui.presentation.meetingReport.MyBarChart
import com.alef.souqleader.ui.presentation.projectReports.ProjectLead
import com.alef.souqleader.ui.theme.Blue


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
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 24.dp),
            content = {
                item {       ChannelTotal(channelReportList) }
                item {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)

                    ) {
                        MyBarChart(channelReportList, stringResource(R.string.channel_report))
                    }
                }
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 8.dp),
                        text = stringResource(id = R.string.channel_report),
                        fontSize = 18.sp, color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }

                items(channelReportList) { lead ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 4.dp
                            ),
                        shape = RoundedCornerShape(10.dp),

                    ) {
                        ChannelLead(lead)

                    }
                }
            })
    }




@Composable
fun ChannelTotal(channelReportList: ArrayList<Chart>) {

    var totalCancel = 0.0
    var totalLead = 0.0
    var totalFresh = 0.0
    channelReportList.forEach {
        totalCancel += it.total
        totalLead += it.total_leads?:0f
        totalFresh += it.fresh_leads
    }
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
                text = totalCancel.toInt().toString(),
                style = TextStyle(
                    fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold
                ),
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.total_of_cancellation),
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
                        text = stringResource(R.string.all_leads),
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                    Text(
                        text = totalLead.toInt().toString(),
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
                        text = stringResource(R.string.fresh_lead),
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                    Text(
                        text = totalFresh.toInt().toString(),
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                }
            }

        }
    }
}

@Composable
fun ChannelLead(channelLeadChart: Chart) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()

    ) {
        androidx.compose.material3.Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = channelLeadChart.channel_title.toString(),
            fontSize = 13.sp,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center
        )
        androidx.compose.material3.Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = channelLeadChart.total_leads?.toInt().toString(),
            fontSize = 12.sp, color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center
        )
    }
}