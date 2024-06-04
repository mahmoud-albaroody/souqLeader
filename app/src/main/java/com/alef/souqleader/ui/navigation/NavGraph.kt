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
                MyApp()
//                SimplifyScreen(
//                    navController, modifier
//                )
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
                    navController = navController,
                    modifier
                ) {

                }
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
