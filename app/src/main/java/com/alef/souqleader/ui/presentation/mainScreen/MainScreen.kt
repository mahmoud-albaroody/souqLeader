package com.alef.souqleader.ui.presentation.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Snackbar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.SideMenuItem
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.CircularIndeterminateProgressBar
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.Start
import com.alef.souqleader.ui.appbar.HomeAppBar
import com.alef.souqleader.ui.navigation.Navigation
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.navigation.currentRoute
import com.alef.souqleader.ui.navigation.navigationTitle
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.theme.*
import com.alef.souqleader.ui.appbar.AppBarWithArrow
import com.alef.souqleader.ui.navigation.Navigation1
import com.alef.souqleader.ui.networkconnection.ConnectionState
import com.alef.souqleader.ui.networkconnection.connectivityState
import com.alef.souqleader.ui.presentation.login.LoginViewModel
import com.alef.souqleader.ui.updateLocale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: SharedViewModel,
    mainViewModel: MainViewModel
) {

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
        if (isConnected.not()) {
            Snackbar(
                action = {}, modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.there_is_no_internet), color = colorResource(
                        id = R.color.white
                    )
                )
            }
        }
    }, content = { innerPadding ->
        Box {
            if (AccountData.isFirstTime && AccountData.auth_token == null) {
                Navigation1(
                    navController = navController,
                    modifier = modifier,
                    Screen.SimplifyWorkFlowScreen.route,
                    viewModel,
                    mainViewModel = mainViewModel
                )
            } else if (AccountData.auth_token == null) {
                Navigation1(
                    navController = navController,
                    modifier = modifier,
                    Screen.LoginScreen.route,
                    viewModel,
                    mainViewModel = mainViewModel
                )
            }
            CircularIndeterminateProgressBar(
                isDisplayed = mainViewModel.showLoader, 0.4f, Color.Black.copy(alpha = 0.5f)
            )
        }
    })
}

@Composable
fun CustomModalDrawer(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: SharedViewModel,
    allLead: SnapshotStateList<AllLeadStatus>,
    mainViewModel: MainViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val isAppBarVisible = remember { mutableStateOf(true) }
    var title = ""
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val allLead = remember { mutableStateListOf<AllLeadStatus>() }
    allLead.add(AllLeadStatus(title_en = "Add new lead", title_ar = "إضافة عميل", id = null))
    allLead.add(AllLeadStatus(title_en = "Fresh", title_ar = "جديد", id = 1))
    allLead.add(AllLeadStatus(title_en = "Cold", title_ar = "قديم", id = 2))
    allLead.add(AllLeadStatus(title_en = "Delay", title_ar = "تأخير", id = 100))
    allLead.add(AllLeadStatus(title_en = "Following", title_ar = "متابعة", id = 7))
    allLead.add(AllLeadStatus(title_en = "Duplicated", title_ar = "مكرر", id = 200))
    allLead.add(AllLeadStatus(title_en = "No Answer", title_ar = "بدون رد", id = 3))
    allLead.add(AllLeadStatus(title_en = "Answer Leads", title_ar = "تم الرد", id = 12))
    allLead.add(AllLeadStatus(title_en = "Reservation/Requests", title_ar = "الحجز/الطلبات", id = 22))
    allLead.add(AllLeadStatus(title_en = "Arrange Meeting", title_ar = "ترتيب اجتماعات", id = 6))
    allLead.add(AllLeadStatus(title_en = "Current Meetings", title_ar = "اجتماعات حاليه", id = 4))
    allLead.add(AllLeadStatus(title_en = "Following After Meeting", title_ar = "متابعة بعد الاجتماع", id = 5))
    allLead.add(AllLeadStatus(title_en = "Archive", title_ar = "الأرشيف", id = 10))
    allLead.add(AllLeadStatus(title_en = "Cancelled", title_ar = "تم الإلغاء", id = 8))
    allLead.add(AllLeadStatus(title_en = "Done Deal", title_ar = "الصفقات المتجزة", id = 9))

    Scaffold(
        modifier = Modifier,
        snackbarHost = {
            if (isConnected.not()) {
                Snackbar(
                    action = {}, modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.there_is_no_internet), color = colorResource(
                            id = R.color.white
                        )
                    )
                }
            }
        },
        content = { innerPadding ->
            innerPadding
//            LaunchedEffect(key1 = true) {
//                viewModel.getLeads()
//                viewModel.viewModelScope.launch {
//                    viewModel.allLead.collect {
//                        when (it) {
//                            is Resource.Success -> {
//                                allLead.clear()
//                                allLead.add(
//                                    AllLeadStatus(
//                                        title_ar = context.getString(R.string.add_lead),
//                                        title_en = context.getString(R.string.add_lead)
//                                    )
//                                )
//                                it.data?.data?.let { it1 ->
//                                    allLead.addAll(it1)
//                                }
//                                allLead.add(
//                                    AllLeadStatus(
//                                        id = 200,
//                                        title_ar = context.getString(R.string.duplicated_lead),
//                                        title_en = context.getString(R.string.duplicated_lead),
//                                    )
//                                )
//                                allLead.add(
//                                    AllLeadStatus(
//                                        id = 100,
//                                        title_ar = context.getString(R.string.delay_lead),
//                                        title_en = context.getString(R.string.delay_lead),
//                                    )
//                                )
//                                mainViewModel.showLoader = false
//                            }
//
//
//                            is Resource.Loading -> {
//                                mainViewModel.showLoader = true
//                            }
//
//                            is Resource.DataError -> {
//                                if (it.errorCode == 401) {
//                                    AccountData.clear()
//                                    // Start()
//                                }
//                                mainViewModel.showLoader = false
//                            }
//                        }
//                    }
//                }
//            }

            ModalNavigationDrawer(drawerState = drawerState,
                gesturesEnabled = mainViewModel.isGesturesEnabled,
                scrimColor = colorResource(id = R.color.transparent),
                drawerContent = {
                    ModalDrawerSheet(
                        drawerShape = RectangleShape,
                        drawerContainerColor = colorResource(id = R.color.transparent)
                    ) {
                        DrawerContent(navController, modifier, viewModel, allLead) { position, s ->

                            title = s.toString()
                            scope.launch {
                                drawerState.close()
                            }
                            if (title == context.getString(R.string.dashboard)) {
                                navController.navigate(Screen.DashboardScreen.route) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.timeline)) {
                                navController.navigate(Screen.Timeline.route) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.company_timeline)) {
                                navController.navigate(Screen.CompanyTimelineScreen.route) {
                                    launchSingleTop = true
                                }
                            } else if (title == context.getString(R.string.sales_profile_report)) {
                                navController.navigate(
                                    Screen.SalesProfileReportScreen.route.plus(
                                        "/${AccountData.userId}"
                                    )
                                ) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.add_lead)) {
                                navController.navigate(Screen.AddLeadScreen.route) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.projects)) {
                                navController.navigate(
                                    Screen.ProjectsScreen.route.plus(
                                        "/${s}"
                                    )
                                ) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.jobs_posts)) {
                                navController.navigate(
                                    Screen.JobPostScreen.route
                                ) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.properties)) {
                                navController.navigate(Screen.PropertyScreen.route) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.meeting_report)) {
                                navController.navigate(Screen.ReportsScreen.route) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.cancellation_report)) {
                                navController.navigate(Screen.CancellationsReportScreen.route) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.project_report)) {
                                navController.navigate(Screen.ProjectReport.route) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.delay_report)) {
                                navController.navigate(Screen.DelayReport.route) {
                                    launchSingleTop = true
                                }
                            } else if (s == context.getString(R.string.channel_report)) {
                                navController.navigate(Screen.ChannelReport.route) {
                                    launchSingleTop = true
                                }
                            } else if (title == context.getString(R.string.users)) {
                                navController.navigate(Screen.PaymentPlansScreen.route) {
                                    launchSingleTop = true
                                }
                            } else if (title == context.getString(R.string.profile)) {
                                navController.navigate(Screen.ProfileScreen.route) {
                                    launchSingleTop = true
                                }
                            } else if (title == context.getString(R.string.logout)) {
                                val lang = AccountData.lang
                                AccountData.clear()
                                AccountData.lang = lang
                                updateLocale(context, Locale(AccountData.lang))
                                (context as MainActivity).setContent {
                                    Start()
                                }
                            } else {
                                navController.navigate(
                                    Screen.AllLeadsScreen.route.plus("/${allLead.find { it.getTitle() == s }?.id}")
                                ) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                },
                content = {
                    Scaffold(
                        topBar = {
                            when (currentRoute(navController)) {
                                Screen.DashboardScreen.route,
                                Screen.Timeline.route,
                                Screen.CompanyTimelineScreen.route,
                                Screen.SalesProfileReportScreen.route,
                                Screen.PaymentPlansScreen.route,
                                Screen.ProfileScreen.route,
                                Screen.RoleScreen.route, Screen.AddLeadScreen.route,
                                Screen.AllLeadsScreen.route,
                                Screen.PropertyScreen.route,
                                Screen.ReportsScreen.route,
                                Screen.CancellationsReportScreen.route,
                                Screen.ChannelReport.route,
                                Screen.ProjectReport.route,
                                Screen.DelayReport.route,
                                Screen.ProjectsScreen.route -> {
                                    mainViewModel.isGesturesEnabled = true
                                    val appTitle: String =
                                        if (currentRoute(navController) == Screen.DashboardScreen.route) stringResource(
                                            R.string.dashboard
                                        )
                                        else if (currentRoute(navController) == Screen.Timeline.route) stringResource(
                                            R.string.timeline
                                        )
                                        else if (currentRoute(navController) == Screen.CompanyTimelineScreen.route) stringResource(
                                            R.string.company_timeline
                                        )
                                        else if (currentRoute(navController) == Screen.AllLeadsScreen.route) Screen.AllLeadsScreen.title
                                        else if (currentRoute(navController) == Screen.AddLeadScreen.route) title
                                        else if (currentRoute(navController) == Screen.ProjectsScreen.route) title
                                        else if (currentRoute(navController) == Screen.PropertyScreen.route) title
                                        else if (currentRoute(navController) == Screen.ReportsScreen.route) title
                                        else if (currentRoute(navController) == Screen.CancellationsReportScreen.route) title
                                        else if (currentRoute(navController) == Screen.ChannelReport.route) title
                                        else if (currentRoute(navController) == Screen.ProjectReport.route) title
                                        else if (currentRoute(navController) == Screen.DelayReport.route) title
                                        else if (currentRoute(navController) == Screen.ChangePasswordScreen.route) {
                                            mainViewModel.isGesturesEnabled = false
                                            stringResource(
                                                R.string.change_password
                                            )

                                        } else if (currentRoute(navController) == Screen.ForgetPasswordScreen.route) {
                                            mainViewModel.isGesturesEnabled = false
                                            stringResource(
                                                R.string.forgot_password
                                            )
                                        } else if (currentRoute(navController) == Screen.ResetPasswordScreen.route) {
                                            mainViewModel.isGesturesEnabled = false
                                            stringResource(
                                                R.string.reset_password
                                            )
                                        } else if (currentRoute(navController) == Screen.CheckCodeScreen.route) {
                                            mainViewModel.isGesturesEnabled = false
                                            stringResource(
                                                R.string.verify_code
                                            )
                                        } else if (currentRoute(navController) == Screen.FilterResultScreen.route) stringResource(
                                            R.string.filter_result
                                        )
                                        else if (currentRoute(navController) == Screen.FilterScreen.route) stringResource(
                                            R.string.filter
                                        )
                                        else if (currentRoute(navController) == Screen.SalesProfileReportScreen.route) stringResource(
                                            R.string.sales_profile_report
                                        )
                                        else if (currentRoute(navController) == Screen.ReportsScreen.route) stringResource(
                                            R.string.reports
                                        )
                                        else if (currentRoute(navController) == Screen.PaymentPlansScreen.route) stringResource(
                                            R.string.users
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
                                        else if (currentRoute(navController) == Screen.AllLeadsScreen.route) title
                                        else if (currentRoute(navController) == Screen.CRMScreen.route) stringResource(
                                            R.string.timeline
                                        )
                                        else stringResource(R.string.dashboard)

                                    HomeAppBar(title = appTitle, openDrawer = {
                                        scope.launch {
                                            if (drawerState.isClosed) {
                                                drawerState.open()
                                            }
                                        }
                                    }, mainViewModel = mainViewModel
                                    ) {
                                        navController.popBackStack()
                                    }
                                }

                                Screen.LoginScreen.route -> {

                                }

                                Screen.SimplifyWorkFlowScreen.route -> {

                                }


                                else -> {
                                    if (currentRoute(navController) ==
                                        Screen.LeadDetailsScreen.route
                                    ) {
                                        title = stringResource(
                                            R.string.lead_details
                                        )
                                    } else if (currentRoute(navController) ==
                                        Screen.UserDetailsScreen.route
                                    ) {
                                        title = stringResource(R.string.user_details)
                                    } else if (currentRoute(navController) ==
                                        Screen.MapScreen.route
                                    ) {
                                        title = stringResource(R.string.leads)
                                    } else if (currentRoute(navController) ==
                                        Screen.ProductFilterResultScreen.route.plus("?s={s}")
                                    ) {
                                        title = stringResource(R.string.filter_result)
                                    } else if (currentRoute(navController) ==
                                        Screen.ProductFilterResultScreen.route.plus("?s={s}")
                                    ) {
                                        title = stringResource(R.string.filter_result)
                                    } else if (currentRoute(navController) ==
                                        Screen.JobPostScreen.route
                                    ) {
                                        title = stringResource(R.string.jobs_posts)
                                    } else if (currentRoute(navController) ==
                                        Screen.JobApplicationScreen.route
                                    ) {
                                        title = stringResource(R.string.job_applications)
                                    } else if (currentRoute(navController) ==
                                        Screen.JobApplicationDetailsScreen.route.plus("?s={jobApplicationDetailsScreen}")
                                    ) {
                                        title = stringResource(R.string.job_applications)
                                    }
                                    mainViewModel.isGesturesEnabled = false
                                    AppBarWithArrow(
                                        navigationTitle(navController, title),
                                        mainViewModel = mainViewModel
                                    ) {
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
                            Navigation(
                                navController = navController,
                                modifier = modifier,
                                Screen.DashboardScreen.route,
                                viewModel,
                                mainViewModel = mainViewModel
                            )


                            CircularIndeterminateProgressBar(
                                isDisplayed = mainViewModel.showLoader,
                                0.4f,
                                Color.Transparent
                            )
                        }
                    }
                })

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


    if (AccountData.permissionList.find { it.module_name == "lead" && it.permissions.read } != null) sideMenuItem.add(
        SideMenuItem(R.drawable.project_icon, stringResource(R.string.leads))
    )
    if (AccountData.permissionList.find { it.module_name == "sales_report" && it.permissions.read } != null) sideMenuItem.add(
        SideMenuItem(
            R.drawable.sales_name_icon, stringResource(R.string.sales_profile_report)
        )
    )
    if (AccountData.permissionList.find { it.module_name == "inventory" && it.permissions.read } != null)
        sideMenuItem.add(
            SideMenuItem(
                R.drawable.inventory_menu_icon, stringResource(R.string.inventory)
            )
        )
    if (AccountData.permissionList.find { it.module_name == "reports" && it.permissions.read } != null)
        sideMenuItem.add(
            SideMenuItem(
                R.drawable.repots_menu_icon,
                stringResource(R.string.reports)
            )
        )
    if (AccountData.permissionList.find { it.module_name == "job" && it.permissions.read } != null)
        sideMenuItem.add(
            SideMenuItem(
                R.drawable.book,
                stringResource(R.string.jobs_posts)
            )
        )

    if (AccountData.permissionList.find { it.module_name == "Users" && it.permissions.read } != null)
        sideMenuItem.add(
            SideMenuItem(
                R.drawable.profile_menu_icon, stringResource(R.string.users)
            )
        )
    sideMenuItem.add(SideMenuItem(R.drawable.profile_menu_icon, stringResource(R.string.profile)))
    // sideMenuItem.add(SideMenuItem(R.drawable.book, stringResource(R.string.roles_premmisions)))
    sideMenuItem.add(SideMenuItem(R.drawable.sign_out_icon, stringResource(R.string.logout)))

    Column(
        Modifier
            .width(260.dp)
            .background(colorResource(id = R.color.white))
    ) {

        Box(
            Modifier
                .width(260.dp)
                .background(colorResource(id = R.color.blue))
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
                    color = colorResource(id = R.color.white),
                    style = TextStyle(
                        fontSize = 16.sp, color = colorResource(id = R.color.blue),
                    ),
                )
                Text(
                    text = salesNameState,
                    color = colorResource(id = R.color.white),
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
                .background(colorResource(id = R.color.white))
        ) {
            items(sideMenuItem.size) { position ->
                Item(
                    sideMenuItem[position].image,
                    sideMenuItem[position].title,
                    Modifier,
                    position,
                    allLead
                ) {
                    Screen.AllLeadsScreen.title = it.toString()
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
    val context = LocalContext.current
    Column {
        Row(
            modifier
                .fillMaxWidth()
                .height(45.dp)
                .clickable {
                    if (text == context.getString(R.string.timeline) ||
                        text == context.getString(R.string.leads) ||
                        text == context.getString(R.string.inventory) ||
                        text == context.getString(R.string.reports)
                    ) {
                        isVisible = !isVisible
                    } else {
                        onItemClick(text)
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
        if (text == stringResource(id = R.string.timeline) && isVisible) {

            Row(
                modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 60.dp, end = 16.dp)
                    .clickable {
                        onItemClick(context.getString(R.string.timeline))
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    stringResource(id = R.string.timeline), fontSize = 14.sp, style = TextStyle(
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
                        onItemClick(context.getString(R.string.company_timeline))
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    stringResource(R.string.company_timeline), fontSize = 14.sp, style = TextStyle(
                        textAlign = TextAlign.Start,
                    ), modifier = modifier.fillMaxWidth()
                )
            }

        }
        if (text == stringResource(id = R.string.leads) && isVisible) {
            allLead.forEach {
                //Fresh
                when {
                    it.id == 1 &&
                            AccountData.permissionList.find
                            {
                                it.module_name == "fresh_lead" &&
                                        it.permissions.read
                            } == null -> {
                    }

                    it.id == 2 &&
                            AccountData.permissionList.find
                            {
                                it.module_name == "cold_lead" &&
                                        it.permissions.read
                            } == null -> {

                    }

                    else -> {

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
                }
            }

        }

        if (text == stringResource(id = R.string.inventory) && isVisible) {

            Row(
                modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 60.dp, end = 16.dp)
                    .clickable {
                        onItemClick(context.getString(R.string.projects))
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
                        onItemClick(context.getString(R.string.properties))
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
        if (text == stringResource(id = R.string.reports) && isVisible) {
            if (AccountData.permissionList.find { it.module_name == "meeting_report" && it.permissions.read } != null)

                Row(
                    modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = 60.dp, end = 16.dp)
                        .clickable {
                            onItemClick(context.getString(R.string.meeting_report))
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        stringResource(R.string.meeting_report),
                        fontSize = 14.sp,
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                        ),
                        modifier = modifier.fillMaxWidth()
                    )
                }
            if (AccountData.permissionList.find { it.module_name == "cancelation_report" && it.permissions.read } != null)

                Row(
                    modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = 60.dp, end = 16.dp)
                        .clickable {
                            onItemClick(context.getString(R.string.cancellation_report))
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
            if (AccountData.permissionList.find { it.module_name == "channel_report" && it.permissions.read } != null)

                Row(
                    modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = 60.dp, end = 16.dp)
                        .clickable {
                            onItemClick(context.getString(R.string.channel_report))
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
            if (AccountData.permissionList.find { it.module_name == "project_report" && it.permissions.read } != null)

                Row(
                    modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = 60.dp, end = 16.dp)
                        .clickable {
                            onItemClick(context.getString(R.string.project_report))
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(R.string.project_report),
                        fontSize = 14.sp,
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                        ),
                        modifier = modifier.fillMaxWidth()
                    )
                }
            if (AccountData.permissionList.find { it.module_name == "delay_report" && it.permissions.read } != null)

                Row(
                    modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = 60.dp, end = 16.dp)
                        .clickable {
                            onItemClick(context.getString(R.string.delay_report))
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(id = R.string.delay_report),
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

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    mainViewModel: MainViewModel,
    onSplashEndedValid: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current
    viewModel.updateBaseUrl(AccountData.BASE_URL)
    LaunchedEffect(key1 = true) {
        delay(500)
        viewModel.getUserByToken()
        viewModel.viewModelScope.launch {
            viewModel.loginState.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data?.status == true) {
                            it.data.data?.let {
                                if (!it.access_token.isNullOrEmpty()) {
                                    AccountData.auth_token = "Bearer " + it.access_token
                                    AccountData.name = it.name.toString()
                                    AccountData.role_name = it.role_name.toString()
                                    AccountData.role_id = it.role_id ?: 0
                                    AccountData.userId = it.id ?: 0
                                    AccountData.photo = it.photo.toString()
                                    AccountData.email = it.email.toString()
                                    it.permissions?.let {
                                        AccountData.permissionList = it
                                    }
                                    AccountData.firebase_token?.let { it1 ->
                                        viewModel.updateFcmToken(
                                            it1
                                        )
                                    }
                                    sharedViewModel.updateSalesNameState(it.role_name.toString())
                                    sharedViewModel.updatePhotoState(it.photo.toString())
                                    sharedViewModel.updateNameState(it.name.toString())
                                    onSplashEndedValid()
                                }

//                                if (AccountData.auth_token != null)
//                                    (context as MainActivity).setContent {
//                                        Start()
//                                    }
                            }
                        } else {
                            Toast.makeText(context, it.data?.message.toString(), Toast.LENGTH_LONG)
                                .show()
                        }
                        mainViewModel.showLoader = false
                    }


                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        if (it.errorCode == 401) {
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
                        if (it.errorCode == 500) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.something_error), Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        mainViewModel.showLoader = false
                    }
                }

            }

        }
    }




    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blue)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.souq_leader_logo_3),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)

        )
    }
}
