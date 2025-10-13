package com.alef.souqleader.ui.presentation.dashboard1

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.Dashboard
import com.alef.souqleader.data.remote.dto.TopAgent
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.presentation.meetingReport.CancellationReasonsList
import com.alef.souqleader.ui.presentation.meetingReport.LeadSourcesLineChart
import com.alef.souqleader.ui.presentation.meetingReport.MonthlyInventoryChartMP
import com.alef.souqleader.ui.presentation.meetingReport.MyBarChart1
import com.alef.souqleader.ui.presentation.meetingReport.MyBarChartDashboard

import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen1(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    mainViewModel: MainViewModel
) {
    val viewModel: DashboardViewModel1 = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    var dashboard: Dashboard? by remember { mutableStateOf(null) }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getDashboard()
        viewModel.viewModelScope.launch {
            viewModel.dashboard.collect {
                when (it) {
                    is Resource.Success -> {
                        dashboard = it.data?.data
                        refreshing = false
                        mainViewModel.showLoader = false
                        Log.e("ggggg", dashboard.toString())
                    }

                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        refreshing = false
                        if (it.errorCode == 401 || it.errorCode == 403) {
                            AccountData.clear()
                            (context as MainActivity).setContent {
                                AndroidCookiesTheme {
                                    MainScreen(
                                        Modifier,
                                        navController,
                                        sharedViewModel,
                                        mainViewModel
                                    )
                                }
                            }
                        }
                        mainViewModel.showLoader = false
                    }
                }
            }
        }
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
            coroutineScope.launch {

                viewModel.getDashboard()
            }
        }
    ) {}

    DashBoardView(dashboard)
}

@Composable
fun DashBoardView(dashboard: Dashboard?) {

    val context = LocalContext.current
    val defaultOption = context.getString(R.string.primary)

    var selectedOption by remember { mutableStateOf(defaultOption) }
    var selectedOption2 by remember { mutableStateOf(defaultOption) }
    val items: ArrayList<StatItem> = arrayListOf()
    dashboard?.let {
        items.add(
            StatItem(
                it.totalHotActiveLead,
                stringResource(R.string.hot_leads), Icons.Default.Bolt, Color(0xFFFD184A)
            ),
        )
        items.add(
            StatItem(
                it.total_active_lead,
                stringResource(R.string.active_leads), Icons.Default.Group, Color(0xFF1F5AF8)
            ),
        )
        items.add(
            StatItem(
                it.fresh_leads,
                stringResource(R.string.fresh_leads), Icons.Default.Phone, Color(0xFF00C59D)
            ),
        )
        items.add(
            StatItem(
                it.activeInventory,
                stringResource(R.string.active_inventory),
                Icons.Default.Inventory2,
                Color(0xFFB000EF)
            ),
        )
        items.add(
            StatItem(
                it.active_delayed_leads,
                stringResource(R.string.delayed_leads),
                Icons.Default.Timelapse,
                Color(0xFFFDC73D)
            ),
        )
        items.add(
            StatItem(
                it.online_users,
                stringResource(R.string.active_users),
                Icons.Default.AccountCircle,
                Color(0xFF8A00EA)
            ),
        )
        items.add(
            StatItem(
                it.conversion_rate.toString() + "%",
                stringResource(R.string.conversion_rate),
                Icons.Default.Assessment,
                Color(0xFF00B663)
            )
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA)),
        content = {

            item {
                DashboardScreen(items)
            }
            item {
                Column(
                    Modifier
                        .padding(horizontal = 18.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text =context.getString(R.string.active_leads),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 12.sp,
                            modifier = Modifier
                                .padding(vertical = 0.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedOption == context.getString(R.string.primary),
                                onClick = { selectedOption = context.getString(R.string.primary) },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF020F3C))
                            )
                            Text(
                                context.getString(R.string.primary), fontSize = 10.sp,
                                lineHeight = 10.sp,
                                modifier = Modifier
                                    .padding(vertical = 0.dp)
                                    .padding(end = 8.dp)
                            )
                            RadioButton(
                                selected = selectedOption == context.getString(R.string.secondary),
                                onClick = { selectedOption = context.getString(R.string.secondary) },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF020F3C))
                            )
                            Text(
                                stringResource(R.string.secondary), fontSize = 10.sp,
                                lineHeight = 10.sp,
                                modifier = Modifier
                                    .padding(vertical = 0.dp)
                            )
                        }
                    }
                    Text(
                        text = stringResource(R.string.distribution_across_different_stages),
                        color = Color.Gray,
                        fontSize = 9.sp,
                        lineHeight = 9.sp,
                        modifier = Modifier
                            .padding(vertical = 0.dp)
                    )
                }
            }
            item {
                if(selectedOption==stringResource(R.string.secondary)){
                    dashboard?.active_lead_chart?.secondary?.let {
                        MyBarChartDashboard(it)
                    }
                }else {
                    dashboard?.active_lead_chart?.primary?.let {
                        MyBarChartDashboard(it)
                    }
                }
            }
            item {
                Column {
                    Text(
                        text = stringResource(R.string.lead_sources_over_time),
                        Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 18.sp, color = colorResource(id = R.color.black),
                            fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
                        )
                    )
                    dashboard?.lead_source?.let {
                        LeadSourcesLineChart(it)
                    }
                }

            }
            item {
                Column(
                    Modifier
                        .padding(horizontal = 18.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text =context.getString(R.string.active_leads),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 12.sp,
                            modifier = Modifier
                                .padding(vertical = 0.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedOption2 == context.getString(R.string.primary),
                                onClick = { selectedOption2 = context.getString(R.string.primary) },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF020F3C))
                            )
                            Text(context.getString(R.string.primary)
                                , fontSize = 10.sp,
                                lineHeight = 10.sp,
                                modifier = Modifier
                                    .padding(vertical = 0.dp)
                                    .padding(end = 8.dp)
                            )
                            RadioButton(
                                selected = selectedOption2 ==context.getString(R.string.secondary),
                                onClick = { selectedOption2 = context.getString(R.string.secondary) },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF020F3C))
                            )
                            Text(
                                stringResource(R.string.secondary), fontSize = 10.sp,
                                lineHeight = 10.sp,
                                modifier = Modifier
                                    .padding(vertical = 0.dp)
                            )
                        }
                    }
                    Text(
                        text = context.getString(R.string.distribution_across_different_stages),
                        color = Color.Gray,
                        fontSize = 9.sp,
                        lineHeight = 9.sp,
                        modifier = Modifier
                            .padding(vertical = 0.dp)
                    )
                }
            }

            item {

                if(selectedOption2==stringResource(R.string.secondary)){
                    dashboard?.stage_delay?.secondary?.let {
                        MyBarChart1(it)
                    }
                }else {
                    dashboard?.stage_delay?.primary?.let {
                        MyBarChart1(it)
                    }
                }
            }


            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 16.dp)
                ) {
                    Text(
                        text =context.getString(R.string.active_leads),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = context.getString(R.string.distribution_across_different_stages),
                        color = Color.Gray,
                        fontSize = 9.sp
                    )
                }
                dashboard?.inventoryChart?.let {
                    MonthlyInventoryChartMP(it)
                }
            }
            item {

                    dashboard?.reasons_chart?.let {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp, vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.cancellation_reasons),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                val total = it.sumOf { chart -> chart.getCount().toDouble() }.toFloat()
                                Text(
                                    text = stringResource(R.string.total, total),
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        CancellationReasonsList(it)
                    }

                }

            }


            item {
                dashboard?.top_agents?.let {
                    TopAgentsSection(it)
                }
            }


        })
}

data class StatItem(
    val value: String,
    val title: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun DashboardScreen(items: ArrayList<StatItem>) {


    if (items.isNotEmpty())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F6FA))
                .padding(horizontal = 16.dp, vertical = 16.dp),

            ) {
            Row(Modifier.fillMaxWidth()) {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    StatCard(items[0])
                }
                Box(
                    Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    StatCard(items[1])
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    StatCard1(items[2])
                }
                Box(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    StatCard1(items[3])
                }
                Box(
                    Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    StatCard1(items[4])
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    StatCard(items[5])
                }
                Box(
                    Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    StatCard(items[6])
                }
            }


        }
}


@Composable
fun StatCard(item: StatItem) {
    Card(
        modifier = Modifier
            .height(65.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(item.color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = "Hot Leads",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    lineHeight = 15.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.dp)
                )

                Text(
                    text = item.title,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 10.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.dp)
                )
            }
        }
    }
}

@Composable
fun StatCard1(item: StatItem) {
    Card(
        modifier = Modifier
            .height(100.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),

            ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(item.color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = "Hot Leads",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }



            Column(Modifier.padding(top = 8.dp)) {
                Text(
                    text = item.value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    lineHeight = 15.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.dp)
                )

                Text(
                    text = item.title,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 10.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.dp)
                )
            }
        }
    }
}

data class AgentCard(
    val name: String,
    val deals: String
)


@Composable
fun TopAgentsSection(topAgent: List<TopAgent>) {

    val agents = topAgent.mapIndexed { index, agent ->
        AgentCard(
            agent.user.name.toString(),
            agent.action_count.toString() + stringResource(R.string.deals_closed)
        )
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F8FC))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        // ---- Header ----

        Text(
            text = stringResource(R.string.top_performing_agents),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )



        Spacer(modifier = Modifier.height(14.dp))

        // ---- Agents Cards ----
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(agents.size) { index ->
                AgentCardItem(agent = agents[index])
            }
        }
    }
}

@Composable
fun AgentCardItem(agent: AgentCard) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(190.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Icon
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3B82F6)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Agent Name
            Text(
                text = agent.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )

            // Deals Text
            Text(
                text = agent.deals,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Green Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFFD1FAE5))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.target_achieved),
                    color = Color(0xFF16A34A),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun Test(){
//    Row(Modifier.fillMaxWidth()) {
//        Box(Modifier.weight(1f).padding(end = 4.dp)) {
//            StatCard1()
//        }
//        Box(Modifier.weight(1f).padding(start = 4.dp)) {
//            StatCard1()
//        }
//        Box(Modifier.weight(1f).padding(start = 4.dp)) {
//            StatCard1()
//        }
//
//
//    }
//
//}

