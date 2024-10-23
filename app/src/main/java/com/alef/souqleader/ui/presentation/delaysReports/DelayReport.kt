package com.alef.souqleader.ui.presentation.delaysReports


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Chart
import com.alef.souqleader.data.remote.dto.DelayActions
import com.alef.souqleader.ui.presentation.addlead.DynamicSelectTextField
import com.alef.souqleader.ui.presentation.meetingReport.MyBarChart
import com.alef.souqleader.ui.theme.*

@Composable
fun DelaysReports(navController: NavController, modifier: Modifier) {
    val viewModel: DelayReportViewModel = hiltViewModel()
    val delayActionsList = remember { mutableStateListOf<DelayActions>() }
    val delayActions = remember { mutableStateListOf<String>() }
    val chartActionsList = remember { mutableStateListOf<Chart>() }
    LaunchedEffect(key1 = true) {
        viewModel.delayReport()
    }
    viewModel.delayReport?.let { delayReportList ->
        delayActionsList.addAll(delayReportList[0].delay_actions_list)
        delayReportList.forEach {
            delayActions.add(it.getTitle())
            chartActionsList.add(
                Chart(
                    actions_count = it.delay_actions_count,
                    title_ar = it.title_ar, title_en = it.title_en,
                    status_id = it.status_id
                )
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            content = {
                item {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp),
                    ) {
                        MyBarChart(chartActionsList, stringResource(R.string.delay_report))
                    }
                }
                item {
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 8.dp)
                            .padding(horizontal = 16.dp),
                        text = stringResource(R.string.select_a_status_to_view_the_delay_actions),
                        fontSize = 12.sp, color = colorResource(id = R.color.gray)
                    )

                    Box(
                        Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        DynamicSelectTextField(delayActions) { delayActionsName ->
                            delayActionsList.clear()
                            delayReportList.find { it.getTitle() == delayActionsName }?.delay_actions_list?.let { it1 ->
                                delayActionsList.addAll(
                                    it1
                                )
                            }
                        }
                    }
                }

                items(delayActionsList) { delayReport ->
                    DelayReport(delayReport)
                }
            })
    }

}


@Composable
fun DelayReport(delayActions: DelayActions) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp),
    ) {
        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp
                    )
                    .padding(end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    painterResource(R.drawable.client_placeholder),
                    contentDescription = "",
                    modifier = Modifier.padding(end = 8.dp)
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .padding(top = 6.dp)
                ) {
                    Text(
                        text = delayActions.lead_name ?: "", style = TextStyle(
                            fontSize = 16.sp, color = colorResource(id = R.color.blue)
                        )
                    )

                }

            }

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                delayActions.sales?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painterResource(R.drawable.sales_name_icon),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = delayActions.sales, style = TextStyle(
                                fontSize = 14.sp,
                            ),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                delayActions.status?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painterResource(R.drawable.all_leads_icon_gray),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = delayActions.status, style = TextStyle(
                                fontSize = 14.sp,
                            ), modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                delayActions.created_at?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painterResource(R.drawable.vuesax_linear_calendar),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = delayActions.created_at, style = TextStyle(
                                fontSize = 14.sp,
                            ), modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }


                delayActions.reminder_time?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painterResource(R.drawable.vuesax_linear_calendar_red),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(20.dp)
                        )

                        Text(
                            text = delayActions.reminder_time ?: "", style = TextStyle(
                                fontSize = 14.sp, color = colorResource(id = R.color.red)
                            ), modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

            }


            delayActions.note?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.notes_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = delayActions.note, style = TextStyle(
                            fontSize = 12.sp, color = colorResource(id = R.color.gray)
                        ), modifier = Modifier.padding(start = 4.dp)
                    )

                }
            }
        }
    }
}

