package com.alef.souqleader.ui.presentation.activityAndAction


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.SalesReportModel
import com.alef.souqleader.data.remote.dto.UserDate
import com.alef.souqleader.data.remote.dto.UserDetailsResponse
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.userDetails.ActivityItem
import com.alef.souqleader.ui.presentation.userDetails.ProfileItem
import com.alef.souqleader.ui.presentation.userDetails.UserDetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun ActivityScreen(navController: NavController, userId: String?, mainViewModel: MainViewModel) {
    val userDetailsViewModel: UserDetailsViewModel = hiltViewModel()
    var userData by remember { mutableStateOf(UserDate()) }
    val actionSales = remember { mutableStateListOf<SalesReportModel>() }
    val activitiesSales = remember { mutableStateListOf<SalesReportModel>() }
    var page by remember { mutableIntStateOf(1) }
    var activityPage by remember { mutableIntStateOf(1) }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        page = 1
        activityPage = 1
        userId?.let {
            userDetailsViewModel.userDate(
                it, page = page, activityPage = activityPage
            )
        }
        userDetailsViewModel.viewModelScope.launch {
            userDetailsViewModel.userDate.collect {
                it.data?.let {
                    userData = it

                    if ( Screen.ActivityScreen.title==context.getString(R.string.activities)) {
                        if (activityPage == 1) {
                            actionSales.clear()
                        }
                        userData.activities?.forEach { activity ->
                            activitiesSales.add(
                                SalesReportModel(
                                    time = activity.created_at,
                                    image = activity.activity_by?.photo,
                                    name = activity.activity_by?.name,
                                    action = activity.getDescriptions()
                                )
                            )
                        }
                    } else {
                        if (page == 1) {
                            actionSales.clear()
                        }
                        userData.actions?.forEach { action ->
                            actionSales.add(
                                SalesReportModel(
                                    time = action.getDate(),
                                    name = action.sales,
                                    status = action.status,
                                    comment = action.note,
                                )
                            )
                        }

                    }
                }
            }
        }
    }
    Column {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            if (Screen.ActivityScreen.title == context.getString(R.string.activities)) {
                items(activitiesSales) { actionSalesItem ->
                    ActivityItem(actionSalesItem, Screen.ActivityScreen.title)
                }
                if (userData.activities_pagination?.pages != null) if (userData.activities_pagination?.pages!! > activityPage && activitiesSales.size > 10) {
                    item {
                        Row(
                            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(16.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                        userId?.let {
                            userDetailsViewModel.userDate(
                                it, activityPage = ++page, page = 1
                            )
                        }
                    }
                }

            } else {
                items(actionSales) { actionSalesItem ->
                    ActivityItem(actionSalesItem, Screen.ActivityScreen.title)
                }
                if (userData.actions_pagination?.pages != null) if (userData.actions_pagination?.pages!! > page && actionSales.size > 10) {
                    item {
                        Row(
                            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(16.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }

                        userId?.let {
                            userDetailsViewModel.userDate(
                                it, activityPage = 1, page = ++page
                            )
                        }
                    }
                }

            }

        }

    }
}