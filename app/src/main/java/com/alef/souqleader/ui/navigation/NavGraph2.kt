package com.alef.souqleader.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.alef.souqleader.data.remote.dto.Jobapps
import com.alef.souqleader.data.remote.dto.JopPersion
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.data.remote.dto.ProjectFilterRequest
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyObject
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.fromJson
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.activityAndAction.ActivityScreen
import com.alef.souqleader.ui.presentation.addlead.AddLeadScreen
import com.alef.souqleader.ui.presentation.allLeads.AllLeadsScreen
import com.alef.souqleader.ui.presentation.callLog.AddCallLogScreen
import com.alef.souqleader.ui.presentation.cancellationsReport.CancellationsReport
import com.alef.souqleader.ui.presentation.changePassword.ChangePasswordScreen
import com.alef.souqleader.ui.presentation.channelReport.ChannelReport
import com.alef.souqleader.ui.presentation.companyTimeline.CompanyTimelineScreen
import com.alef.souqleader.ui.presentation.crmSystem.CRMScreen
import com.alef.souqleader.ui.presentation.dashboardScreen.DashboardScreen
import com.alef.souqleader.ui.presentation.delaysReports.DelaysReports
import com.alef.souqleader.ui.presentation.filter.FilterScreen
import com.alef.souqleader.ui.presentation.filter2.Filter2Screen
import com.alef.souqleader.ui.presentation.filterResult.FilterResultScreen
import com.alef.souqleader.ui.presentation.inventoryFilter.InventoryFilterScreen
import com.alef.souqleader.ui.presentation.jobApplication.JobApplicationScreen
import com.alef.souqleader.ui.presentation.jobApplicationDetails.JobApplicationDetailsScreen
import com.alef.souqleader.ui.presentation.jobPost.JobPostScreen
import com.alef.souqleader.ui.presentation.leadDetails.LeadDetailsScreen
import com.alef.souqleader.ui.presentation.projects.ProjectsScreen
import com.alef.souqleader.ui.presentation.leadUpdate.LeadUpdateScreen
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.presentation.map.MapScreen
import com.alef.souqleader.ui.presentation.meetingReport.MeetingScreen
import com.alef.souqleader.ui.presentation.paymentPlans.PaymentPlansScreen
import com.alef.souqleader.ui.presentation.profile.ProfileScreen
import com.alef.souqleader.ui.presentation.projectDetails.ProjectDetailsScreen
import com.alef.souqleader.ui.presentation.projectFilterResult.ProductFilterResultScreen
import com.alef.souqleader.ui.presentation.projectReports.ProjectReport
import com.alef.souqleader.ui.presentation.property.PropertyScreen
import com.alef.souqleader.ui.presentation.proprtiesDetails.PropertyDetailsScreen
import com.alef.souqleader.ui.presentation.rolesPremissions.RolesPermissionsScreen
import com.alef.souqleader.ui.presentation.salesProfileReport.SalesProfileReportScreen
import com.alef.souqleader.ui.presentation.timeline.TimelineScreen
import com.alef.souqleader.ui.presentation.userDetails.UserDetailsScreen
import org.json.JSONObject

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier? = null,
    ssd: String, viewModel: SharedViewModel, mainViewModel: MainViewModel
) {
    //  Navigation(navController, Modifier.padding(it), genreList.value)
    NavHost(navController, startDestination = ssd) {


        composable(Screen.DashboardScreen.route) {
            modifier?.let { it1 ->
                DashboardScreen(
                    navController = navController, viewModel, mainViewModel = mainViewModel
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
                        navController, modifier, leadId = leadId, mainViewModel
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
            val obj = backStackEntry.arguments?.getString(Screen.ProjectsScreen.objectName)

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
                        navController, modifier, mainViewModel, viewModel, leadIds = stringArray
                    )
                }

            }
        }

        composable(
            Screen.LeadDetailsScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val obj = backStackEntry.arguments?.getString(Screen.LeadDetailsScreen.objectName)

            if (obj != null) {
                LeadDetailsScreen(
                    navController, leadId = obj, mainViewModel
                )
            }
        }

        composable(
            Screen.JobApplicationScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val obj = backStackEntry.arguments?.getString(Screen.JobApplicationScreen.objectName)
            if (obj != null) {
                JobApplicationScreen(
                    navController = navController, jobId = obj,mainViewModel
                )

            }
        }

        composable(
            Screen.JobApplicationDetailsScreen.route.plus("?" + Screen.JobApplicationDetailsScreen.objectName + "={jobApplicationDetailsScreen}"),
            arguments = listOf(navArgument(Screen.JobApplicationDetailsScreen.objectName) {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString(Screen.JobApplicationDetailsScreen.objectName)
                ?.let { jsonString ->
                    val project = jsonString.fromJson<Jobapps>()
                    modifier?.let { it1 ->
                        JobApplicationDetailsScreen(
                            navController, jobApps = project
                        )

                    }
                }
        }

        composable(Screen.JobPostScreen.route) {
            modifier?.let { it1 ->
                JobPostScreen(
                    navController = navController,mainViewModel=mainViewModel, sharedViewModel = viewModel
                )
            }
        }

        composable(
            Screen.UserDetailsScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val obj = backStackEntry.arguments?.getString(Screen.UserDetailsScreen.objectName)

            if (obj != null) {
                UserDetailsScreen(
                    navController, userId = obj, mainViewModel
                )
            }
        }
//        composable(
//            Screen.InventoryFilterScreen.route, arguments =
//            listOf(navArgument("s") {
//                type = NavType.StringType
//            })
//        ) { backStackEntry ->
//            InventoryFilterScreen(
//                navController, mainViewModel, viewModel
//            )
//        }
        composable(
            Screen.InventoryFilterScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val obj = backStackEntry.arguments?.getString(Screen.InventoryFilterScreen.objectName)

            if (obj != null) {
                InventoryFilterScreen(
                    navController, mainViewModel, viewModel, obj
                )
            }
        }

//            val obj = backStackEntry.arguments?.getString(Screen.InventoryFilterScreen.objectName)
//
//            if (obj != null) {
//                InventoryFilterScreen(
//                    navController, mainViewModel,viewModel
//                )
//            }
//        }


        composable(Screen.AddLeadScreen.route) {
            modifier?.let { it1 ->
                AddLeadScreen(
                    modifier, navController, mainViewModel, sharedViewModel = viewModel
                )
            }
        }

        composable(Screen.ChangePasswordScreen.route) {
            modifier?.let { it1 ->
                ChangePasswordScreen(
                    navController, mainViewModel, sharedViewModel = viewModel
                )
            }
        }

//        composable(Screen.ForgetPasswordScreen.route) {
//            modifier?.let { it1 ->
//                ForgetPasswordScreen(
//
//                )
//            }
//        }

//        composable(Screen.ResetPasswordScreen.route) {
//            modifier?.let { it1 ->
//                ResetPasswordScreen(
//
//                )
//            }
//        }
//        composable(Screen.CheckCodeScreen.route) {
//            modifier?.let { it1 ->
//                CheckCodeScreen(
//
//                )
//            }
//        }


        composable(Screen.Timeline.route) {
            modifier?.let { it1 ->
                TimelineScreen(
                    navController, modifier, mainViewModel
                )
            }
        }
        composable(Screen.CompanyTimelineScreen.route) {
            modifier?.let { it1 ->
                CompanyTimelineScreen(
                    navController, modifier, mainViewModel
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


        composable(
            Screen.SalesProfileReportScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val obj =
                backStackEntry.arguments?.getString(Screen.SalesProfileReportScreen.objectName)
            if (obj != null) {
                SalesProfileReportScreen(
                    obj
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
                    navController,
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





        composable(
            Screen.FilterScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val obj = backStackEntry.arguments?.getString(Screen.FilterScreen.objectName)


            if (obj != null) {
                FilterScreen(
                    navController, mainViewModel, viewModel, obj
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
            Screen.MapScreen.route.plus("?" + Screen.MapScreen.objectName + "={s}"),
            arguments = listOf(navArgument(Screen.MapScreen.objectName) {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString(Screen.MapScreen.objectName)?.let { jsonString ->
                var project: ProjectResponse? = null
                var propertyResponse: PropertyResponse? = null
                if (Screen.MapScreen.title == "pro") {
                    propertyResponse = jsonString.fromJson<PropertyResponse>()
                } else {
                    project = jsonString.fromJson<ProjectResponse>()

                }

                MapScreen(
                    navController,
                    projects = project,
                    propertyResponse = propertyResponse,
                    mainViewModel
                )
            }

        }
        composable(
            Screen.ProductFilterResultScreen.route.plus("?" + Screen.ProductFilterResultScreen.objectName + "={s}"),
            arguments = listOf(navArgument(Screen.ProductFilterResultScreen.objectName) {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString(Screen.ProductFilterResultScreen.objectName)
                ?.let { jsonString ->
                    val projectFilterRequest = jsonString.fromJson<ProjectFilterRequest>()
                    ProductFilterResultScreen(
                        navController,
                        mainViewModel, projectFilterRequest
                    )
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
                val property = jsonString.fromJson<PropertyObject>()
                modifier?.let { it1 ->
                    PropertyDetailsScreen(
                        navController, modifier,mainViewModel, property = property
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
        composable(Screen.AddCallLogScreen.route) {
            modifier?.let { it1 ->
                AddCallLogScreen(
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
                    navController, modifier, mainViewModel
                )
            }
        }
        composable(Screen.MainScreen1.route) {
            modifier?.let { it1 ->
                MainScreen(
                    modifier,
                    navController = navController,
                    viewModel,
                    mainViewModel = mainViewModel
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
                        navController, modifier, mainViewModel
                    )
                }

            }
        }

        composable(
            Screen.ActivityScreen.route.plus("/{s}"), arguments =
            listOf(navArgument("s") {
                type = NavType.StringType
            })
        ) { backStack ->
            val userId = backStack.arguments?.getString(Screen.ActivityScreen.objectName)
            userId?.let {
                if (modifier != null) {
                    ActivityScreen(
                        navController, userId, mainViewModel
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

        Screen.ActivityScreen.route -> {
            Screen.ActivityScreen.title
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
