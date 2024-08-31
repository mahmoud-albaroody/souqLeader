package com.alef.souqleader.ui.navigation

import android.util.Log
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
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.data.remote.dto.Property
import com.alef.souqleader.domain.model.AccountData
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
fun Navigation(
    navController: NavHostController, modifier: Modifier? = null,
    ssd: String, viewModel: SharedViewModel
) {
    //  Navigation(navController, Modifier.padding(it), genreList.value)
    NavHost(navController, startDestination = ssd) {

        composable(Screen.SimplifyWorkFlowScreen.route) {
            modifier?.let { it1 ->
                SimplifyScreen(
                    modifier, navController = navController
                )
            }
        }

        composable(Screen.DashboardScreen.route) {
            modifier?.let { it1 ->
                DashboardScreen(
                    navController = navController, viewModel
                )
            }
        }
        composable(Screen.LoginScreen.route) {
            modifier?.let { it1 ->
                LoginScreen(
                    modifier = modifier,
                    navController = navController, viewModel
                )
            }
        }

        composable(
            Screen.AllLeadsScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            var leadId = backStackEntry.arguments?.getString(Screen.ProjectsScreen.objectName)
            leadId.let {
                if (modifier != null) {
                    AllLeadsScreen(
                        navController, modifier, leadId = leadId
                    )
                    leadId = null
                }
            }
        }

        composable(
            Screen.FilterResultScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            var obj = backStackEntry.arguments?.getString(Screen.ProjectsScreen.objectName)

            if (modifier != null) {
                FilterResultScreen(
                    navController = navController,
                    modifier = modifier, obj = JSONObject(obj)
                )
            }
            backStackEntry.savedStateHandle.remove<String>("filterObject")
        }





        composable(
            Screen.LeadUpdateScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) {

            val joinedString = it.arguments?.getString(Screen.LeadUpdateScreen.objectName)
            val stringArray = joinedString?.split(",")?.toTypedArray()
            stringArray?.let {
                if (modifier != null) {
                    LeadUpdateScreen(
                        navController, modifier, leadIds = stringArray
                    )
                }

            }
        }

        composable(Screen.AddLeadScreen.route) {
            modifier?.let { it1 ->
                AddLeadScreen(
                    modifier
                )
            }
        }

        composable(Screen.ChangePasswordScreen.route) {
            modifier?.let { it1 ->
                ChangePasswordScreen(

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


        composable(Screen.Timeline.route) {
            modifier?.let { it1 ->
                TimelineScreen(
                    navController, modifier
                )
            }
        }

        composable(
            Screen.CRMScreen.route.plus("?" + Screen.CRMScreen.objectName + "={CRMS}"),
            arguments = listOf(navArgument(Screen.CRMScreen.objectName) {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString(Screen.CRMScreen.objectName)?.let { jsonString ->
                val post = jsonString.fromJson<Post>()
                modifier?.let { it1 ->
                    CRMScreen(
                        navController, modifier, post = post
                    )

                }
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
                    modifier, navController
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
                    modifier, navController
                )
            }
        }

        composable(Screen.FilterScreen.route) {
            modifier?.let { it1 ->
                FilterScreen(
                    navController,
                    modifier
                )
            }
        }

        composable(Screen.Filter2Screen.route) {
            modifier?.let { it1 ->
                Filter2Screen(
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

        composable(
            Screen.ProjectsDetailsScreen.route.plus("?" + Screen.ProjectsDetailsScreen.objectName + "={product}"),
            arguments = listOf(navArgument(Screen.ProjectsDetailsScreen.objectName) {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString(Screen.ProjectsDetailsScreen.objectName)?.let { jsonString ->
                val project = jsonString.fromJson<Project>()
                modifier?.let { it1 ->
                    ProjectDetailsScreen(
                        navController, modifier, project = project
                    )

                }
            }
        }

        composable(
            Screen.PropertyDetailsScreen.route.plus("?" + Screen.PropertyDetailsScreen.objectName + "={property}"),
            arguments = listOf(navArgument(Screen.PropertyDetailsScreen.objectName) {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString(Screen.PropertyDetailsScreen.objectName)?.let { jsonString ->
                val property = jsonString.fromJson<Property>()
                modifier?.let { it1 ->
                    PropertyDetailsScreen(
                        navController, modifier, property = property
                    )

                }
            }
        }

        composable(Screen.CancellationsReportScreen.route) {
            modifier?.let { it1 ->
                CancellationsReport(
                    navController, modifier
                )
            }
        }
        composable(Screen.ProjectReport.route) {
            modifier?.let { it1 ->
                ProjectReport(
                    navController, modifier
                )
            }
        }
        composable(Screen.DelayReport.route) {
            modifier?.let { it1 ->
                DelaysReports(
                    navController, modifier
                )
            }
        }

        composable(Screen.ChannelReport.route) {
            modifier?.let { it1 ->
                ChannelReport(
                    navController, modifier
                )
            }
        }

        composable(Screen.PropertyScreen.route) {
            modifier?.let { it1 ->
                PropertyScreen(
                    navController, modifier
                )
            }
        }

        composable(
            Screen.ProjectsScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStack ->
            val typeScreen = backStack.arguments?.getString(Screen.ProjectsScreen.objectName)
            typeScreen?.let {
                if (modifier != null) {
                    ProjectsScreen(
                        navController, modifier
                    )
                }

            }
        }

    }
}

@Composable
fun navigationTitle(navController: NavController, title: String): String {

    return when (currentRoute(navController)) {
        Screen.AddLeadScreen.route -> {
            stringResource(id = R.string.add_lead)
        }

        Screen.LeadUpdateScreen.route -> {
            stringResource(R.string.lead_update)
        }

        Screen.AllLeadsScreen.route -> {
            title
        }


        Screen.CRMScreen.route.plus("?" + Screen.CRMScreen.objectName + "={CRMS}") -> {
            ""
        }

        Screen.FilterResultScreen.route -> {
            stringResource(id = R.string.filter_result)
        }

        Screen.ChangePasswordScreen.route -> {
            stringResource(id = R.string.change_password)
        }

        Screen.ForgetPasswordScreen.route -> {
            stringResource(id = R.string.forgot_password)
        }

        Screen.ResetPasswordScreen.route -> {
            stringResource(R.string.reset_password)
        }
        Screen.CheckCodeScreen.route -> {
            stringResource(R.string.verify_code)
        }


        Screen.FilterScreen.route -> {
            stringResource(id = R.string.filter)
        }

        Screen.ProjectsDetailsScreen.route.plus("?" + Screen.ProjectsDetailsScreen.objectName + "={product}") -> {
            stringResource(R.string.project_details)
        }

        Screen.PropertyDetailsScreen.route.plus("?" + Screen.PropertyDetailsScreen.objectName + "={property}") -> {
            stringResource(R.string.property_details)
        }

        Screen.PropertyScreen.route -> {
            stringResource(R.string.properties)
        }

        Screen.SalesProfileReportScreen.route -> {
            stringResource(R.string.sales_profile_report)
        }

        Screen.ProjectsScreen.route -> {
            title
        }


        else -> {
            title
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
