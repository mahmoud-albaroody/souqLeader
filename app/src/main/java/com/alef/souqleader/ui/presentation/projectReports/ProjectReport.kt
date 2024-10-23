package com.alef.souqleader.ui.presentation.projectReports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import com.alef.souqleader.data.remote.dto.MeetingReport
import com.alef.souqleader.data.remote.dto.ProjectChart
import com.alef.souqleader.ui.presentation.meetingReport.PieChartData
import com.alef.souqleader.ui.presentation.meetingReport.PieChartView


@Composable
fun ProjectReport(navController: NavController, modifier: Modifier) {
    val viewModel: ProjectReportViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        viewModel.projectsReport()
    }
    viewModel.projectsReport?.let {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                item {
                    ProjectTotals(it)
                }
                item {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.grey100))
                    ) {
                        val arr1: ArrayList<PieChartData> = arrayListOf()
                        it.projectChart.forEachIndexed { index, projectChart ->
                            arr1.add(PieChartData(projectChart.title, projectChart.lead_percentage))
                        }
                        PieChartView(
                            arr1,
                            stringResource(R.string.projects)
                        )
                    }
                }
                item {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.grey1))
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                                text = stringResource(id = R.string.projects),
                                fontSize = 13.sp, color = colorResource(id = R.color.black), textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                                text = stringResource(id = R.string.leads),
                                fontSize = 12.sp, color = colorResource(id = R.color.black),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                items(it.projectChart) { lead ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 4.dp
                            ),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.offWhite))
                    ) {
                        ProjectLead(lead)
                    }
                }
            })
    }
}

@Composable
fun ProjectLead(projectChart: ProjectChart) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = projectChart.title,
            fontSize = 13.sp, color = colorResource(id = R.color.black), textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = projectChart.leads ?: "",
            fontSize = 12.sp, color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun ProjectTotals(projectsReport: MeetingReport) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .height(140.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.grey100))
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.projects),
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
                        text = stringResource(R.string.all_projects),
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                    Text(
                        text = projectsReport.projects_count,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        ),
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Text(
                        text = stringResource(id = R.string.leads),
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                    Text(
                        text = projectsReport.leads_count,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        ),
                    )
                }
            }

        }
    }
}
