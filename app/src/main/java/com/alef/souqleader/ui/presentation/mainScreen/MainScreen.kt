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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alef.souqleader.R
import com.alef.souqleader.data.SideMenuItem
import com.alef.souqleader.ui.appbar.HomeAppBar
import com.alef.souqleader.ui.navigation.Navigation
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.navigation.currentRoute
import com.alef.souqleader.ui.navigation.navigationTitle
import com.alef.souqleader.ui.theme.Blue
import com.bitaqaty.reseller.ui.component.appbar.AppBarWithArrow
import kotlinx.coroutines.launch

@Composable
fun MyApp(modifier: Modifier) {
    val navController = rememberNavController()
    CustomModalDrawer(modifier, navController)
}

@Composable
fun CustomModalDrawer(modifier: Modifier, navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val isAppBarVisible = remember { mutableStateOf(true) }
    var title = ""
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerState = drawerState,
        scrimColor = Transparent,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape,
                drawerContainerColor = Transparent
            ) {
                DrawerContent(navController, modifier) { position, s ->
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
                            navController.navigate(Screen.AddLeadScreen.route) {
                                launchSingleTop = true
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
                            navController.navigate(Screen.ReportsScreen.route) {
                                launchSingleTop = true
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
                            navController.navigate(Screen.CancellationsReportScreen.route) {
                                launchSingleTop = true
                            }
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
                        Screen.AddLeadScreen.route,
                        Screen.SalesProfileReportScreen.route,
                        Screen.ReportsScreen.route,
                        Screen.PaymentPlansScreen.route,
                        Screen.ProfileScreen.route,
                        Screen.RoleScreen.route -> {
                            if (isAppBarVisible.value) {
                                val appTitle: String =
                                    if (currentRoute(navController) == Screen.DashboardScreen.route)
                                        stringResource(R.string.dashboard)
                                    else if (currentRoute(navController) == Screen.Timeline.route)
                                        stringResource(R.string.timeline)
                                    else if (currentRoute(navController) == Screen.AddLeadScreen.route)
                                        stringResource(R.string.add_lead)
                                    else if (currentRoute(navController) == Screen.SalesProfileReportScreen.route)
                                        stringResource(R.string.sales_profile_report)
                                    else if (currentRoute(navController) == Screen.ReportsScreen.route)
                                        stringResource(R.string.reports)
                                    else if (currentRoute(navController) == Screen.PaymentPlansScreen.route)
                                        stringResource(R.string.payment_plans)
                                    else if (currentRoute(navController) == Screen.ProfileScreen.route)
                                        stringResource(R.string.profile)
                                    else if (currentRoute(navController) == Screen.RoleScreen.route)
                                        stringResource(R.string.roles_premmisions)
                                    else if (currentRoute(navController) == Screen.ProjectsDetailsScreen.route)
                                        stringResource(R.string.project_details)
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
                        }

                        else -> {
                            AppBarWithArrow(navigationTitle(navController, title)) {
                                navController.popBackStack()
                            }
                        }
                    }
                },
            )
            { paddingValues ->
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                ) {
                    Navigation(navController = navController, modifier = modifier)
                }

            }
        })
}

@Composable
fun DrawerContent(
    navController: NavController,
    modifier: Modifier,
    onItemClick: (Int, String?) -> Unit
) {
    val sideMenuItem: ArrayList<SideMenuItem> = arrayListOf()
    sideMenuItem.add(SideMenuItem(R.drawable.element_1, stringResource(R.string.dashboard)))
    sideMenuItem.add(SideMenuItem(R.drawable.timeline_menu_icon, stringResource(R.string.timeline)))
    sideMenuItem.add(SideMenuItem(R.drawable.project_icon, stringResource(R.string.leads)))
    sideMenuItem.add(
        SideMenuItem(
            R.drawable.sales_name_icon,
            stringResource(R.string.sales_profile_report)
        )
    )
    sideMenuItem.add(
        SideMenuItem(
            R.drawable.inventory_menu_icon,
            stringResource(R.string.inventory)
        )
    )
    sideMenuItem.add(SideMenuItem(R.drawable.repots_menu_icon, stringResource(R.string.reports)))
    sideMenuItem.add(
        SideMenuItem(
            R.drawable.payment_menu_icon,
            stringResource(R.string.payment_plans)
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
                    painter = painterResource(R.drawable.user_profile),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Mahmoud Ali",
                    color = White,
                    style = TextStyle(
                        fontSize = 16.sp, color = Blue,
                    ),
                )
                Text(
                    text = "Sales Director",
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
    onItemClick: (s: String?) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier
                .fillMaxWidth()
                .height(45.dp)
                .clickable {
                    if (position == 4) {
                        isVisible = !isVisible
                    } else {
                        onItemClick(null)
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

        if (position == 4 && isVisible) {
            Row(
                modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clickable {
                        onItemClick("Projects")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    stringResource(id = R.string.projects), fontSize = 14.sp, style = TextStyle(
                        textAlign = TextAlign.Start,
                    ), modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clickable {
                        onItemClick("Properties")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    stringResource(R.string.properties), fontSize = 14.sp, style = TextStyle(
                        textAlign = TextAlign.Start,
                    ), modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
            }
        }
    }
}

