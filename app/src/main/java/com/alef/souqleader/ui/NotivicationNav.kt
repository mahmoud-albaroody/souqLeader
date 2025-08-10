package com.alef.souqleader.ui

import androidx.navigation.NavController
import com.alef.souqleader.ui.navigation.Screen

fun navNotification(navController: NavController, value1:String, value2:String) {
    when (value1) {
        "lead-view" -> {
            navController.navigate(
                Screen.LeadDetailsScreen.route.plus("/$value2")
            )
        }

        "post" -> {
            Screen.CRMScreen.title = "Timeline"
            navController.navigate(
                Screen.CRMScreen.route
                    .plus("?" + Screen.CRMScreen.objectName + "=${value2}")
            )
        }

        else -> {
            Screen.CRMScreen.title = "companyTimeline"
            navController.navigate(
                Screen.CRMScreen.route
                    .plus("?" + Screen.CRMScreen.objectName + "=${value2}")
            )
        }
    }
}
