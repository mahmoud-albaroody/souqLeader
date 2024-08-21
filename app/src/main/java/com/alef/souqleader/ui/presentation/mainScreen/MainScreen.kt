package com.alef.souqleader.ui.presentation.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.SideMenuItem
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.appbar.HomeAppBar
import com.alef.souqleader.ui.navigation.Navigation
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.navigation.currentRoute
import com.alef.souqleader.ui.navigation.navigationTitle
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.appbar.AppBarWithArrow
import kotlinx.coroutines.launch

@Composable
fun MyApp(modifier: Modifier) {
    val viewModel: SharedViewModel = hiltViewModel()
    val allLead = remember { mutableStateListOf<AllLeadStatus>() }
    LaunchedEffect(key1 = true) {
        viewModel.getLeads()
        viewModel.viewModelScope.launch {
            viewModel.allLead.collect {
                allLead.add(AllLeadStatus(title_ar = "Add Lead", title_en = "Add Lead"))
                allLead.addAll(it)
                allLead.add(
                    AllLeadStatus(
                        title_ar = "Duplicated Leads",
                        title_en = "Duplicated Leads", id = 100
                    )
                )
                allLead.add(
                    AllLeadStatus(
                        title_ar = "Delay Leads", title_en = "Delay Leads",
                        id = 200
                    )
                )
            }
        }
    }
    val navController = rememberNavController()
    CustomModalDrawer(modifier, navController, viewModel, allLead)
}

@Composable
fun CustomModalDrawer(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: SharedViewModel,
    allLead: SnapshotStateList<AllLeadStatus>
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val isAppBarVisible = remember { mutableStateOf(true) }
    var title = ""
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState, scrimColor = Transparent, drawerContent = {
        ModalDrawerSheet(
            drawerShape = RectangleShape, drawerContainerColor = Transparent
        ) {
            DrawerContent(navController, modifier, viewModel, allLead) { position, s ->
                title = s.toString()

                scope.launch {
                    drawerState.close()
                }
                when (position) {
                    0 -> {
                        navController.navigate(Screen.DashboardScreen.route) {
                            launchSingleTop = true
                        }
                    }

                    1 -> {
                        navController.navigate(Screen.Timeline.route) {
                            launchSingleTop = true
                        }
                    }

                    2 -> {
                        if (s == "Add Lead") {
                            navController.navigate(Screen.AddLeadScreen.route) {
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(
                                Screen.AllLeadsScreen.route
                                    .plus("/${allLead.find { it.getTitle() == s }?.id}")
                            ) {
                                launchSingleTop = true
                            }
                        }
                    }

                    3 -> {
                        navController.navigate(Screen.SalesProfileReportScreen.route) {
                            launchSingleTop = true
                        }
                    }

                    4 -> {
                        if (s == "Projects") {
                            navController.navigate(Screen.ProjectsScreen.route.plus("/${s}")) {
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(Screen.PropertyScreen.route) {
                                launchSingleTop = true
                            }
                        }
                    }

                    5 -> {
                        if (s == "meetingReport") {
                            navController.navigate(Screen.ReportsScreen.route) {
                                launchSingleTop = true
                            }
                        }
                        else if (s == "CancellationReport") {
                            navController.navigate(Screen.CancellationsReportScreen.route) {
                                launchSingleTop = true
                            }
                        }
                        else if (s == "ProjectReport") {
                            navController.navigate(Screen.ProjectReport.route) {
                                launchSingleTop = true
                            }
                        }
                        else if (s == "DelayReport") {
                            navController.navigate(Screen.DelayReport.route) {
                                launchSingleTop = true
                            }
                        }

                        else {
                            navController.navigate(Screen.ChannelReport.route) {
                                launchSingleTop = true
                            }
                        }

                    }

                    6 -> {
                        navController.navigate(Screen.PaymentPlansScreen.route) {
                            launchSingleTop = true
                        }
                    }

                    7 -> {
                        navController.navigate(Screen.ProfileScreen.route) {
                            launchSingleTop = true
                        }
                    }

                    8 -> {
                        navController.navigate(Screen.RoleScreen.route) {
                            launchSingleTop = true
                        }
                    }

                    9 -> {
                        navController.navigate(Screen.LoginScreen.route) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }, content = {
        Scaffold(
            topBar = {
                when (currentRoute(navController)) {
                    Screen.DashboardScreen.route, Screen.Timeline.route, Screen.SalesProfileReportScreen.route, Screen.PaymentPlansScreen.route, Screen.ProfileScreen.route, Screen.RoleScreen.route -> {
//                            if (isAppBarVisible.value) {
                        val appTitle: String =
                            if (currentRoute(navController) == Screen.DashboardScreen.route) stringResource(
                                R.string.dashboard
                            )
                            else if (currentRoute(navController) == Screen.Timeline.route) stringResource(
                                R.string.timeline
                            )
                            else if (currentRoute(navController) == Screen.AddLeadScreen.route) stringResource(
                                R.string.add_lead
                            )
                            else if (currentRoute(navController) == Screen.SalesProfileReportScreen.route) stringResource(
                                R.string.sales_profile_report
                            )
                            else if (currentRoute(navController) == Screen.ReportsScreen.route) stringResource(
                                R.string.reports
                            )
                            else if (currentRoute(navController) == Screen.PaymentPlansScreen.route) stringResource(
                                R.string.payment_plans
                            )
                            else if (currentRoute(navController) == Screen.ProfileScreen.route) stringResource(
                                R.string.profile
                            )
                            else if (currentRoute(navController) == Screen.RoleScreen.route) stringResource(
                                R.string.roles_premmisions
                            )
                            else if (currentRoute(navController) == Screen.ProjectsDetailsScreen.route) stringResource(
                                R.string.project_details
                            )
                            else stringResource(R.string.dashboard)
                        HomeAppBar(title = appTitle, openDrawer = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                }
                            }
                        }, openFilters = {
                            isAppBarVisible.value = false
                        })
                    }
//                        }

                    Screen.LoginScreen.route -> {

                    }

                    Screen.SimplifyWorkFlowScreen.route -> {

                    }

                    else -> {
                        AppBarWithArrow(navigationTitle(navController, title)) {
                            navController.popBackStack()
                        }
                    }
                }
            },
        ) { paddingValues ->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                if (AccountData.isFirstTime && AccountData.auth_token == null) {
                    Navigation(
                        navController = navController,
                        modifier = modifier,
                        Screen.SimplifyWorkFlowScreen.route,
                        viewModel
                    )
                    //    AccountData.isFirstTime = false
                } else if (AccountData.auth_token == null) {
                    Navigation(
                        navController = navController,
                        modifier = modifier,
                        Screen.LoginScreen.route,
                        viewModel
                    )
                } else {
                    Navigation(
                        navController = navController,
                        modifier = modifier,
                        Screen.DashboardScreen.route,
                        viewModel
                    )
                }
            }

        }
    })
}

@Composable
fun DrawerContent(
    navController: NavController,
    modifier: Modifier,
    viewModel: SharedViewModel,
    allLead: SnapshotStateList<AllLeadStatus>,
    onItemClick: (Int, String?) -> Unit
) {
    val nameState by viewModel.nameState.collectAsState()
    val photoState by viewModel.photoState.collectAsState()
    val salesNameState by viewModel.salesNameState

    val sideMenuItem: ArrayList<SideMenuItem> = arrayListOf()
    sideMenuItem.add(SideMenuItem(R.drawable.element_1, stringResource(R.string.dashboard)))
    sideMenuItem.add(SideMenuItem(R.drawable.timeline_menu_icon, stringResource(R.string.timeline)))
    sideMenuItem.add(SideMenuItem(R.drawable.project_icon, stringResource(R.string.leads)))
    sideMenuItem.add(
        SideMenuItem(
            R.drawable.sales_name_icon, stringResource(R.string.sales_profile_report)
        )
    )
    sideMenuItem.add(
        SideMenuItem(
            R.drawable.inventory_menu_icon, stringResource(R.string.inventory)
        )
    )
    sideMenuItem.add(SideMenuItem(R.drawable.repots_menu_icon, stringResource(R.string.reports)))
    sideMenuItem.add(
        SideMenuItem(
            R.drawable.payment_menu_icon, stringResource(R.string.payment_plans)
        )
    )
    sideMenuItem.add(SideMenuItem(R.drawable.profile_menu_icon, stringResource(R.string.profile)))
    sideMenuItem.add(SideMenuItem(R.drawable.book, stringResource(R.string.roles_premmisions)))
    sideMenuItem.add(SideMenuItem(R.drawable.sign_out_icon, stringResource(R.string.logout)))

    Column(
        Modifier
            .width(260.dp)
            .background(White)
    ) {

        Box(
            Modifier
                .width(260.dp)
                .background(Blue)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .padding(vertical = 24.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        if (photoState.isNotEmpty()) {
                            AccountData.BASE_URL + photoState
                        } else {
                            R.drawable.user_profile_placehoder
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
                    text = nameState,
                    color = White,
                    style = TextStyle(
                        fontSize = 16.sp, color = Blue,
                    ),
                )
                Text(
                    text = salesNameState,
                    color = White,
                    style = TextStyle(
                        fontSize = 13.sp
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .width(260.dp)
                .background(White)
        ) {
            items(sideMenuItem.size) { position ->
                Item(
                    sideMenuItem[position].image,
                    sideMenuItem[position].title,
                    Modifier,
                    position,
                    allLead
                ) {
                    onItemClick(position, it)
                }
            }
        }
    }

}


@Composable
fun Item(
    image: Int,
    text: String,
    modifier: Modifier,
    position: Int,
    allLead: SnapshotStateList<AllLeadStatus>,
    onItemClick: (s: String?) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier
                .fillMaxWidth()
                .height(45.dp)
                .clickable {
                    when (position) {
                        2 -> {
                            isVisible = !isVisible
                        }

                        4 -> {
                            isVisible = !isVisible
                        }

                        5 -> {
                            isVisible = !isVisible
                        }

                        else -> {
                            onItemClick(null)
                        }
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "",
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Text(
                text, fontSize = 16.sp, style = TextStyle(
                    textAlign = TextAlign.Start,
                ), modifier = modifier
                    .fillMaxWidth()
                    .weight(3f)
            )
            Image(
                painter = painterResource(R.drawable.next_menu_icon),
                contentDescription = "",
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
        if (position == 2 && isVisible) {
            allLead.forEach {
                Row(
                    modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = 60.dp, end = 16.dp)
                        .clickable {
                            onItemClick(it.getTitle())
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        it.getTitle(), fontSize = 14.sp, style = TextStyle(
                            textAlign = TextAlign.Start,
                        ), modifier = modifier.fillMaxWidth()
                    )
                }
            }
//            Row(
//                modifier
//                    .fillMaxWidth()
//                    .height(45.dp)
//                    .clickable {
//                        onItemClick("Projects")
//                    },
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Text(
//                    stringResource(id = R.string.projects), fontSize = 14.sp, style = TextStyle(
//                        textAlign = TextAlign.Start,
//                    ), modifier = modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp)
//                )
//            }
//            Row(
//                modifier
//                    .fillMaxWidth()
//                    .height(45.dp)
//                    .clickable {
//                        onItemClick("Properties")
//                    },
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Text(
//                    stringResource(R.string.properties), fontSize = 14.sp, style = TextStyle(
//                        textAlign = TextAlign.Start,
//                    ), modifier = modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp)
//                )
//            }
        }
        if (position == 4 && isVisible) {
            Row(
                modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 60.dp, end = 16.dp)
                    .clickable {
                        onItemClick("Projects")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    stringResource(id = R.string.projects), fontSize = 14.sp, style = TextStyle(
                        textAlign = TextAlign.Start,
                    ), modifier = modifier.fillMaxWidth()
                )
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 60.dp, end = 16.dp)
                    .clickable {
                        onItemClick("Properties")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    stringResource(R.string.properties), fontSize = 14.sp, style = TextStyle(
                        textAlign = TextAlign.Start,
                    ), modifier = modifier.fillMaxWidth()
                )
            }
        }
        if (position == 5 && isVisible) {
            Row(
                modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 60.dp, end = 16.dp)
                    .clickable {
                        onItemClick("meetingReport")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    stringResource(R.string.meeting_report), fontSize = 14.sp, style = TextStyle(
                        textAlign = TextAlign.Start,
                    ), modifier = modifier.fillMaxWidth()
                )
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 60.dp, end = 16.dp)
                    .clickable {
                        onItemClick("CancellationReport")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.cancellation_report),
                    fontSize = 14.sp,
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                    ),
                    modifier = modifier.fillMaxWidth()
                )
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 60.dp, end = 16.dp)
                    .clickable {
                        onItemClick("ChannelReport")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.channel_report),
                    fontSize = 14.sp,
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                    ),
                    modifier = modifier.fillMaxWidth()
                )
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 60.dp, end = 16.dp)
                    .clickable {
                        onItemClick("ProjectReport")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Project Report",
                    fontSize = 14.sp,
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                    ),
                    modifier = modifier.fillMaxWidth()
                )
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 60.dp, end = 16.dp)
                    .clickable {
                        onItemClick("DelayReport")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Delay Report",
                    fontSize = 14.sp,
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                    ),
                    modifier = modifier.fillMaxWidth()
                )
            }


        }
    }
}

