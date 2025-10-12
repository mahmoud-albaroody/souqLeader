package com.alef.souqleader.ui.presentation.dashboard1

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.Chart
import com.alef.souqleader.data.remote.dto.Dashboard
import com.alef.souqleader.data.remote.dto.LeadDetailsResponse
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.navNotification
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.channelReport.ChannelLead
import com.alef.souqleader.ui.presentation.channelReport.ChannelTotal
import com.alef.souqleader.ui.presentation.dashboardScreen.DashboardViewModel
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.presentation.meetingReport.CancellationReasonsList
import com.alef.souqleader.ui.presentation.meetingReport.LeadSourcesLineChart
import com.alef.souqleader.ui.presentation.meetingReport.MonthlyInventoryChartMP
import com.alef.souqleader.ui.presentation.meetingReport.MyBarChart
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
    var selectedOption by remember { mutableStateOf("Primary") }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA)),
        content = {

            item {
                DashboardScreen()
            }
            item {
                dashboard?.inventoryChart?.let {
                    MyBarChartDashboard(it, "")
                }
            }
            item {
                dashboard?.inventoryChart?.let {
                    LeadSourcesLineChart(it, "Lead Sources Over Time")
                }

            }
            item {
                Column (Modifier.padding(horizontal = 16.dp)){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Active Leads",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedOption == "Primary",
                                onClick = { selectedOption = "Primary" },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF3B82F6))
                            )
                            Text("Primary", fontSize = 10.sp, modifier = Modifier.padding(end = 8.dp))
                            RadioButton(
                                selected = selectedOption == "Secondary",
                                onClick = { selectedOption = "Secondary" },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF3B82F6))
                            )
                            Text("Secondary", fontSize = 10.sp)
                        }
                    }
                    Text(
                        text = "Distribution across different stages",
                        color = Color.Gray,
                        fontSize = 8.sp
                    )
                }
            }
            item {

                    dashboard?.inventoryChart?.let {
                        MyBarChart1(it, "")
                    }
                }


            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {   Text(
                    text = "Active Leads",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                    Text(
                        text = "Distribution across different stages",
                        color = Color.Gray,
                        fontSize = 8.sp
                    ) }

                    MonthlyInventoryChartMP()

            }
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Cancellation Reasons",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Total: 12",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                    CancellationReasonsList()
                }

            }


            item { TopAgentsSection() }


        })
}

data class StatItem(
    val value: String,
    val title: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun DashboardScreen() {
    val items = listOf(
        StatItem("193", "Hot Leads", Icons.Default.Bolt, Color(0xFFFF4B4B)),
        StatItem("376", "Active Leads", Icons.Default.Group, Color(0xFF3B82F6)),
        StatItem("133", "Fresh Leads", Icons.Default.Phone, Color(0xFF22C55E)),
        StatItem("65", "Active Inventory", Icons.Default.Inventory2, Color(0xFFA855F7)),
        StatItem("193", "Delayed Leads", Icons.Default.Timelapse, Color(0xFFFACC15)),
        StatItem("4", "Active Users", Icons.Default.AccountCircle, Color(0xFF8B5CF6)),
        StatItem("3.76%", "Conversion Rate", Icons.Default.Assessment, Color(0xFF10B981))
    )

    var selectedOption by remember { mutableStateOf("Primary") }

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
                StatCard(items[0])
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
                StatCard1(items[0])
            }
            Box(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                StatCard1(items[0])
            }
            Box(
                Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {
                StatCard1(items[0])
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
                StatCard(items[0])
            }
            Box(
                Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {
                StatCard(items[0])
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Active Leads",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption == "Primary",
                    onClick = { selectedOption = "Primary" },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF3B82F6))
                )
                Text("Primary", fontSize = 10.sp, modifier = Modifier.padding(end = 8.dp))
                RadioButton(
                    selected = selectedOption == "Secondary",
                    onClick = { selectedOption = "Secondary" },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF3B82F6))
                )
                Text("Secondary", fontSize = 10.sp)
            }
        }
        Text(
            text = "Distribution across different stages",
            color = Color.Gray,
            fontSize = 8.sp
        )
    }

}


@Composable
fun StatCard(item: StatItem) {
    Card(
        modifier = Modifier
            .height(70.dp)
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
                    .background(Color(0xFF3B82F6)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = "Hot Leads",
                    tint = item.color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = item.value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = item.title,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun StatCard1(item: StatItem) {
    Card(
        modifier = Modifier
            .height(110.dp),
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
                    tint = Color(0xFFFF4B4B),
                    modifier = Modifier.size(24.dp)
                )
            }



            Column(Modifier.padding(top = 8.dp)) {
                Text(
                    text = item.value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = item.title,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

data class AgentCard(
    val name: String,
    val deals: String
)

@Preview
@Composable
fun TopAgentsSection() {
    val agents = listOf(
        AgentCard("Company admin", "8 deals closed"),
        AgentCard("a5", "3 deals closed")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F8FC))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        // ---- Header ----

        Text(
            text = "Top Performing Agents",
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
                    text = "🏆 Target Achieved",
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

