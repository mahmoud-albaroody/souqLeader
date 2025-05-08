package com.alef.souqleader.ui.presentation.userDetails

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.Permission
import com.alef.souqleader.data.remote.dto.SalesReportModel
import com.alef.souqleader.data.remote.dto.UserDate
import com.alef.souqleader.data.remote.dto.UserDetails
import com.alef.souqleader.data.remote.dto.UserDetailsResponse
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.salesProfileReport.SalesProfileReportItem
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserDetailsScreen(navController: NavController, userId: String?, mainViewModel: MainViewModel) {
    val userDetailsViewModel: UserDetailsViewModel = hiltViewModel()
    var userData by remember { mutableStateOf(UserDate()) }
    var userDetails by remember { mutableStateOf(UserDetailsResponse()) }

    var type by remember { mutableStateOf("activity") }
    val actionSales = remember { mutableStateListOf<SalesReportModel>() }
    val activitiesSales = remember { mutableStateListOf<SalesReportModel>() }
    var page by remember { mutableIntStateOf(1) }
    var activityPage by remember { mutableIntStateOf(1) }
    LaunchedEffect(key1 = true) {
        userId?.let { userDetailsViewModel.userDetails(it) }

        userDetailsViewModel.viewModelScope.launch {
            userDetailsViewModel.userDate.collect {
                it.data?.let {
                    userData = it

                    if (type == "activity") {
                        if (activityPage == 1) {
                            actionSales.clear()
                        }
                        userData.activities?.forEach { activity ->
                            activitiesSales.add(
                                SalesReportModel(
                                    time = activity.getDate(),
                                    image = activity.activity_by?.photo?:"",
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
        userDetailsViewModel.viewModelScope.launch {
            userDetailsViewModel.userDetailsState.collect {
                when (it) {
                    is Resource.Success -> {
                        page = 1
                        activityPage = 1
                        userId?.let {
                            userDetailsViewModel.userDate(
                                it, page = page, activityPage = activityPage
                            )
                        }
                        userDetails = it.data!!
                        mainViewModel.showLoader = false
                    }

                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        mainViewModel.showLoader = false
                    }

                    else -> {}
                }
            }
        }
    }
    Column {
        userDetails.data?.let {
            ProfileItem(it, onActionCountClick = {
                type = "action"
                userId?.let { userDetailsViewModel.userDetails(it) }
            }, onActivityCountClick = {
                type = "activity"
                userId?.let { userDetailsViewModel.userDetails(it) }
            }, onSalesReport = {
                userId?.let {
                    navController.navigate(
                        Screen.SalesProfileReportScreen.route.plus(
                            "/${it}"
                        )
                    )
                }

            })
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            if (type == "activity") {
                items(activitiesSales) { actionSalesItem ->
                    ActivityItem(actionSalesItem, type)
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
                    ActivityItem(actionSalesItem, type)
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


@Composable
fun ProfileItem(
    userDetails: UserDetails,
    onActionCountClick: () -> Unit,
    onActivityCountClick: () -> Unit,
    onSalesReport: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .padding(vertical = 8.dp, horizontal = 24.dp),
    ) {

        Card(
            Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        if (userDetails.photo.isNotEmpty()) {
                            if (userDetails.photo.isNotEmpty()) {
                                AccountData.BASE_URL + userDetails.photo
                            } else {
                                R.drawable.user_profile_placehoder
                            }
                        } else {

                        }
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = userDetails.name,
                    style = TextStyle(
                        fontSize = 18.sp, color = colorResource(id = R.color.blue)
                    ),
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = userDetails.role_name,
                        style = TextStyle(
                            fontSize = 14.sp
                        ),
                    )
                    Text(
                        text = userDetails.email, style = TextStyle(
                            fontSize = 15.sp, color = colorResource(id = R.color.blue)
                        )
                    )
                }

            }
        }



        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)

        ) {
            Card(
                Modifier
                    .weight(1f)
                    .height(100.dp)
                    .clickable {
                        onActivityCountClick()
                    }) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = userDetails.activity_count ?: "0", style = TextStyle(
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.blue),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.activity_count),
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                }
            }
            Card(
                Modifier
                    .weight(1f)
                    .height(100.dp)
                    .clickable {
                        onActionCountClick()
                    }) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = userDetails.actions_count ?: "0", style = TextStyle(
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.blue),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.actions_count),
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )

                }
            }
//            if (AccountData.permissionList.find { it.module_name == "sales_report" && it.permissions.read } != null)
//                Card(
//                Modifier
//                    .weight(1f)
//                    .height(100.dp)
//                    .clickable {
//                        onSalesReport()
//                    }) {
//                Column(Modifier.padding(16.dp)) {
//                    Text(
//                        text = userDetails.sales_report_count ?: "", style = TextStyle(
//                            fontSize = 20.sp,
//                            color = colorResource(id = R.color.blue),
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                    Text(
//                        modifier = Modifier.padding(top = 8.dp),
//                        text = stringResource(R.string.sales_report_count),
//                        style = TextStyle(
//                            fontSize = 15.sp
//                        )
//                    )
//
//                }
//            }
        }
    }
}


@Composable
fun ActivityItem(actions: SalesReportModel, type: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

            .background(colorResource(id = R.color.white)),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
                .background(colorResource(id = R.color.white)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (type == stringResource(id = R.string.activities)) {
                Image(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(
                        if (actions.image?.isNotEmpty() == true) {
                            AccountData.BASE_URL + actions.image
                        } else {
                            R.drawable.user_profile_placehoder
                        }

                    ),

                    contentDescription = ""
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                actions.time?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (type == stringResource(id = R.string.activities)) {
                    actions.name?.let {
                        Text(
                            modifier = Modifier.wrapContentWidth(),
                            text = it,
                            fontSize = 11.sp,
                        )
                    }
                    actions.action?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            fontSize = 11.sp,
                        )
                    }
                } else {
                    Row(Modifier.fillMaxWidth()) {
                        actions.name?.let {
                            Text(
                                modifier = Modifier.wrapContentWidth(),
                                text = it,
                                fontSize = 11.sp,
                            )
                        }
                        Text(
                            modifier = Modifier.wrapContentWidth(),
                            text = stringResource(R.string.change_lead_to),
                            fontSize = 11.sp,
                        )
                        actions.status?.let {
                            Text(
                                modifier = Modifier.wrapContentWidth(),
                                text = it,
                                fontSize = 11.sp,
                            )
                        }
                    }

                    actions.comment?.let {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.notes_icon),
                                contentDescription = ""
                            )

                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = it,
                                fontSize = 11.sp,
                            )
                        }
                    }
                }
            }

        }
        Divider(
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth() // Set the thickness of the vertical line
                .background(colorResource(id = R.color.lightGray))  // Set the color of the vertical line
        )
    }
}