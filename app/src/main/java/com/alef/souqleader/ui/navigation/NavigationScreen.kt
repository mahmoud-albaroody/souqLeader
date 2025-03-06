package com.alef.souqleader.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.alef.souqleader.R

sealed class Screen(
    val route: String,
    var title: String = "souqleader",
    val navIcon: (@Composable () -> Unit) = {
        Icon(
            Icons.Filled.Home, contentDescription = "gym_details_screen"
        )
    },
    val objectName: String = "",
    val objectPath: String = "",
) {
    object SimplifyWorkFlowScreen : Screen("simplifyWorkFlowScreen")
    object LoginScreen : Screen("login")
    object DashboardScreen : Screen("dashboard")
    object JobApplicationScreen : Screen("jobApplicationScreen")
    object JobApplicationDetailsScreen : Screen("jobApplicationDetailsScreen")

    object JobPostScreen : Screen("jobPostScreen")

    object AllLeadsScreen : Screen("allLeadsScreen", objectName = "s", objectPath = "/{s}")

    object FilterResultScreen : Screen("filterResultScreen", objectName = "s", objectPath = "/{s}")


    object LeadUpdateScreen : Screen("leadUpdateScreen", objectName = "s", objectPath = "/{s}")

    object LeadDetailsScreen : Screen("leadDetailsScreen", objectName = "s", objectPath = "/{s}")

    object MapScreen : Screen("mapScreen", objectName = "s", objectPath = "/{s}")
    object ActivityScreen : Screen("activityScreen", objectName = "s", objectPath = "/{s}")

    object ProductFilterResultScreen : Screen("productFilterResultScreen", objectName = "s", objectPath = "/{s}")

    object UserDetailsScreen : Screen("userDetailsScreen", objectName = "s", objectPath = "/{s}")

    object InventoryFilterScreen : Screen("inventoryFilterScreen", objectName = "s", objectPath = "/{s}")



    object Timeline : Screen("timeline")


    object CompanyTimelineScreen : Screen("companyTimelineScreen")

    object SalesProfileReportScreen : Screen("salesProfileReportScreen", objectName = "s", objectPath = "/{s}")
    object AddLeadScreen : Screen("addLeadScreen")

    object ChangePasswordScreen :
        Screen("changePasswordScreen", objectName = "s", objectPath = "/{s}")

    object ForgetPasswordScreen :
        Screen("forgetPasswordScreen", objectName = "s", objectPath = "/{s}")

    object ResetPasswordScreen :
        Screen("resetPasswordScreen", objectName = "s", objectPath = "/{s}")

    object CheckCodeScreen : Screen("checkCodeScreen", objectName = "s", objectPath = "/{s}")

    object MainScreen2 : Screen("MainScreen2", "")

    object MainScreen1 : Screen("MainScreen1", "")

    object InventoryScreen : Screen("inventory")

    object ReportsScreen : Screen("reports")

    object PaymentPlansScreen : Screen("paymentPlans")

    object ContactUsScreen : Screen("contactUsScreen")

    object ProfileScreen : Screen("profile")
    object FilterScreen : Screen("filter", objectName = "s", objectPath = "/{s}")
    object Filter2Screen : Screen("filter2")
    object RoleScreen : Screen("Roles")
    object ProjectsScreen : Screen("projectsScreen", objectName = "s", objectPath = "/{s}")

    object PropertyScreen : Screen("propertyScreen")

    object ProjectsDetailsScreen : Screen(
        "projectDetails",
        objectName = "product", objectPath = "/{product}"
    )

    object PropertyDetailsScreen :
        Screen("propertyDetails", objectName = "property", objectPath = "/{property}")

    object CRMScreen : Screen("CRMScreen", objectName = "CRMS", objectPath = "/{CRMS}")

    object CancellationsReportScreen : Screen("CancellationsReport")

    object ProjectReport : Screen("ProjectReport")

    object DelayReport : Screen("DelayReport")


    object ChannelReport : Screen("ChannelReport")

    object MainScreen : Screen("MainScreen")
}


