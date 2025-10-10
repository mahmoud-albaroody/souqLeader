package com.alef.souqleader.ui.presentation.dashboard1

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.alef.souqleader.ui.presentation.meetingReport.MonthlyInventoryChartMP

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
                        Log.e("ggggg",dashboard.toString())
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
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp),
        content = {

            item {
                CancellationReasonsList()
//                Card(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(top = 8.dp)
//
//                ) {
//                    dashboard?.inventoryChart?.let {  }
//                }
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

        })
}