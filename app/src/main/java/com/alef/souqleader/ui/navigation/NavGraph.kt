package com.alef.souqleader.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.alef.souqleader.R
import com.alef.souqleader.ui.presentation.addlead.AddLead
import com.alef.souqleader.ui.presentation.addlead.AddLeadScreen
import com.alef.souqleader.ui.presentation.allLeads.AllLeadsScreen
import com.alef.souqleader.ui.presentation.cancellationsReport.CancellationsReportScreen
import com.alef.souqleader.ui.presentation.crmSystem.CRMScreen
import com.alef.souqleader.ui.presentation.filter.FilterScreen
import com.alef.souqleader.ui.presentation.filter2.Filter2Screen
import com.alef.souqleader.ui.presentation.gymDetailsScreen.DetailsGymScreen
import com.alef.souqleader.ui.presentation.gymScreen.DashboardScreen
import com.alef.souqleader.ui.presentation.leadUpdate.LeadUpdateScreen
import com.alef.souqleader.ui.presentation.login.LoginScreen
import com.alef.souqleader.ui.presentation.mainScreen.MyApp
import com.alef.souqleader.ui.presentation.meetingReport.MeetingScreen
import com.alef.souqleader.ui.presentation.paymentPlans.PaymentPlansScreen
import com.alef.souqleader.ui.presentation.profile.ProfileScreen
import com.alef.souqleader.ui.presentation.projectDetails.ProjectDetailsScreen
import com.alef.souqleader.ui.presentation.rolesPremissions.RolesPermissionsScreen
import com.alef.souqleader.ui.presentation.salesProfileReport.SalesProfileReportScreen
import com.alef.souqleader.ui.presentation.simplifyWorkflow.SimplifyScreen
import com.alef.souqleader.ui.presentation.timeline.TimelineScreen
import com.google.gson.Gson

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier? = null
) {
    NavHost(navController, startDestination = Screen.SimplifyWorkFlowScreen.route) {
        composable(Screen.SimplifyWorkFlowScreen.route) {
            modifier?.let { it1 ->
//                GymScreen(it1) {
//                    navController.navigate(Screen.Gym.route)
////                    val gson = Gson()
////                    val json = Uri.encode(gson.toJson(it))
////                    navController.navigate( "${Screen.Gym.route}/$json")
//                }

                DashboardScreen(
                    navController
                )
            }
        }


        composable(Screen.LoginScreen.route) {
            modifier?.let { it1 ->
                LoginScreen(
                    navController, modifier
                )
            }
        }

        composable(Screen.DashboardScreen.route) {
            modifier?.let { it1 ->
                DashboardScreen(
                    navController = navController)
            }
        }

        composable(Screen.AllLeadsScreen.route) {
            modifier?.let { it1 ->
                AllLeadsScreen(
                    navController, modifier
                )
            }
        }
        composable(Screen.LeadUpdateScreen.route) {
            modifier?.let { it1 ->
                LeadUpdateScreen(
                    navController, modifier
                )
            }
        }

        composable(Screen.AddLeadScreen.route) {
            modifier?.let { it1 ->
                AddLeadScreen(
                    modifier
                )
            }
        }


        composable(Screen.Timeline.route) {
            modifier?.let { it1 ->
                TimelineScreen(
                    modifier
                )
            }
        }

        composable(Screen.SalesProfileReportScreen.route) {
            modifier?.let { it1 ->
                SalesProfileReportScreen(
                    modifier
                )
            }
        }
        composable(Screen.InventoryScreen.route) {
            modifier?.let { it1 ->
                ProfileScreen(
                    modifier
                )
            }
        }
        composable(Screen.ReportsScreen.route) {
            modifier?.let { it1 ->
                MeetingScreen(
                    modifier
                )
            }
        }


        composable(Screen.PaymentPlansScreen.route) {
            modifier?.let { it1 ->
                PaymentPlansScreen(
                    modifier
                )
            }
        }
        composable(Screen.ProfileScreen.route) {
            modifier?.let { it1 ->
                ProfileScreen(
                    modifier
                )
            }
        }
        composable(Screen.RoleScreen.route) {
            modifier?.let { it1 ->
                RolesPermissionsScreen(
                    modifier
                )
            }
        }

//        composable(
//            Screen.Gym.route.plus("/{gym_id}"), arguments =
//            listOf(navArgument("gym_id") {
//                type = NavType.StringType
//            })
//        ) {
//            if (modifier != null) {
//                DetailsGymScreen(
//                    modifier
//                )
//            }
//
//        }

    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.SimplifyWorkFlowScreen.route -> stringResource(id = R.string.app_name)
        Screen.LoginScreen.route -> stringResource(id = R.string.app_name)
        else -> {
            ""
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
