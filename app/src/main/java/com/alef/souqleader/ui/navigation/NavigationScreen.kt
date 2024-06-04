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
) {
    object SimplifyWorkFlowScreen : Screen("simplifyWorkFlowScreen")
    object LoginScreen : Screen("login")
    object DashboardScreen : Screen("dashboard")
    object AllLeadsScreen : Screen("allLeadsScreen")
    object LeadUpdateScreen : Screen("leadUpdateScreen")
    object AddLeadScreen : Screen("addLeadScreen")

    object CancellationsReportScreen : Screen("cancellationsReportScreen")

}


