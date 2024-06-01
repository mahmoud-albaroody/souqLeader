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
import com.alef.souqleader.ui.presentation.gymDetailsScreen.DetailsGymScreen
import com.alef.souqleader.ui.presentation.gymScreen.GymScreen
import com.alef.souqleader.ui.presentation.rolesPremissions.RolesPermissionsScreen
import com.google.gson.Gson

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier? = null
) {
    NavHost(navController, startDestination = Screen.Gym.route) {
        composable(Screen.Gym.route) {
            modifier?.let { it1 ->
//                GymScreen(it1) {
//                    navController.navigate(Screen.Gym.route)
////                    val gson = Gson()
////                    val json = Uri.encode(gson.toJson(it))
////                    navController.navigate( "${Screen.Gym.route}/$json")
//                }
//                DetailsGymScreen(
//                    modifier
//                )
                RolesPermissionsScreen(
                    modifier
                )
            }
        }

        composable(
            Screen.Gym.route.plus("/{gym_id}"), arguments =
            listOf(navArgument("gym_id") {
                type = NavType.StringType
            })
        ) {
            if (modifier != null) {
                DetailsGymScreen(
                    modifier
                )
            }

        }

    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.Gym.route -> stringResource(id = R.string.app_name)
        Screen.GymDetails.route -> stringResource(id = R.string.app_name)
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
