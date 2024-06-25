package com.alef.souqleader.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.alef.souqleader.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_name,
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
    object AllLeadsScreen : Screen("allLeadsScreen")
    object LeadUpdateScreen : Screen("leadUpdateScreen")
    object Timeline : Screen("timeline")
    object SalesProfileReportScreen : Screen("salesProfileReportScreen")
    object AddLeadScreen : Screen("addLeadScreen")

    object InventoryScreen : Screen("inventory")

    object ReportsScreen : Screen("reports")

    object PaymentPlansScreen : Screen("paymentPlans")

    object ProfileScreen : Screen("profile")
    object FilterScreen : Screen("filter")
    object Filter2Screen : Screen("filter2")
    object RoleScreen : Screen("Roles")
    object ProjectsScreen : Screen("projectsScreen", objectName = "s", objectPath = "/{s}")

    object PropertyScreen : Screen("propertyScreen")

    object ProjectsDetailsScreen : Screen("projectDetails", objectName = "product", objectPath = "/{product}")

    object PropertyDetailsScreen : Screen("propertyDetails", objectName = "property", objectPath = "/{property}")

    object CRMScreen : Screen("CRMScreen", objectName = "CRMS", objectPath = "/{CRMS}")

    object CancellationsReportScreen : Screen("CancellationsReport")

    object MainScreen : Screen("MainScreen")
}


