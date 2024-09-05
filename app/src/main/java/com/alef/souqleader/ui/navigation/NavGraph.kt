package com.alef.souqleader.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.data.remote.dto.Property
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.fromJson
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.addlead.AddLeadScreen
import com.alef.souqleader.ui.presentation.allLeads.AllLeadsScreen
import com.alef.souqleader.ui.presentation.cancellationsReport.CancellationsReport
import com.alef.souqleader.ui.presentation.changePassword.ChangePasswordScreen
import com.alef.souqleader.ui.presentation.channelReport.ChannelReport
import com.alef.souqleader.ui.presentation.crmSystem.CRMScreen
import com.alef.souqleader.ui.presentation.dashboardScreen.DashboardScreen
import com.alef.souqleader.ui.presentation.delaysReports.DelaysReports
import com.alef.souqleader.ui.presentation.filter.FilterScreen
import com.alef.souqleader.ui.presentation.filter2.Filter2Screen
import com.alef.souqleader.ui.presentation.filterResult.FilterResultScreen
import com.alef.souqleader.ui.presentation.forgetPassword.checkCode.CheckCodeScreen
import com.alef.souqleader.ui.presentation.forgetPassword.forgetPassword.ForgetPasswordScreen
import com.alef.souqleader.ui.presentation.forgetPassword.resetPassword.ResetPasswordScreen
import com.alef.souqleader.ui.presentation.projects.ProjectsScreen
import com.alef.souqleader.ui.presentation.leadUpdate.LeadUpdateScreen
import com.alef.souqleader.ui.presentation.login.LoginScreen
import com.alef.souqleader.ui.presentation.mainScreen.CustomModalDrawer
import com.alef.souqleader.ui.presentation.meetingReport.MeetingScreen
import com.alef.souqleader.ui.presentation.paymentPlans.PaymentPlansScreen
import com.alef.souqleader.ui.presentation.profile.ProfileScreen
import com.alef.souqleader.ui.presentation.projectDetails.ProjectDetailsScreen
import com.alef.souqleader.ui.presentation.projectReports.ProjectReport
import com.alef.souqleader.ui.presentation.property.PropertyScreen
import com.alef.souqleader.ui.presentation.proprtiesDetails.PropertyDetailsScreen
import com.alef.souqleader.ui.presentation.rolesPremissions.RolesPermissionsScreen
import com.alef.souqleader.ui.presentation.salesProfileReport.SalesProfileReportScreen
import com.alef.souqleader.ui.presentation.simplifyWorkflow.SimplifyScreen
import com.alef.souqleader.ui.presentation.timeline.TimelineScreen
import org.json.JSONObject

@Composable
fun Navigation1(
    navController: NavHostController, modifier: Modifier? = null,
    ssd: String, viewModel: SharedViewModel, mainViewModel: MainViewModel
) {
    //  Navigation(navController, Modifier.padding(it), genreList.value)
    NavHost(navController, startDestination = ssd) {

        composable(Screen.SimplifyWorkFlowScreen.route) {
            modifier?.let { it1 ->
                SimplifyScreen(
                    modifier, mainViewModel, navController = navController
                )
            }
        }


        composable(Screen.LoginScreen.route) {
            modifier?.let { it1 ->
                LoginScreen(
                    modifier = modifier,
                    navController = navController, viewModel, mainViewModel
                )
            }
        }


        composable(Screen.ForgetPasswordScreen.route) {
            modifier?.let { it1 ->
                ForgetPasswordScreen(

                )
            }
        }

        composable(Screen.ResetPasswordScreen.route) {
            modifier?.let { it1 ->
                ResetPasswordScreen(

                )
            }
        }
        composable(Screen.CheckCodeScreen.route) {
            modifier?.let { it1 ->
                CheckCodeScreen(

                )
            }
        }

        composable(Screen.CheckCodeScreen.route) {
            modifier?.let { it1 ->
                CheckCodeScreen(

                )
            }
        }
        composable(Screen.MainScreen2.route) {
            modifier?.let { it1 ->
                CustomModalDrawer(
                   modifier, navController = navController, viewModel,allLead= SnapshotStateList(), mainViewModel = mainViewModel
                )
            }
        }
    }
}

@Composable
fun navigationTitle1(navController: NavController, title: String): String {

    return when (currentRoute(navController)) {

        Screen.ForgetPasswordScreen.route -> {
            stringResource(id = R.string.forgot_password)
        }

        Screen.ResetPasswordScreen.route -> {
            stringResource(R.string.reset_password)
        }

        Screen.CheckCodeScreen.route -> {
            stringResource(R.string.verify_code)
        }


        else -> {
            title
        }
    }
}
