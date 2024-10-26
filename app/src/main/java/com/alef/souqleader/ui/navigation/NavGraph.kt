package com.alef.souqleader.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alef.souqleader.R
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.contactUs.ContactUScreen
import com.alef.souqleader.ui.presentation.forgetPassword.checkCode.CheckCodeScreen
import com.alef.souqleader.ui.presentation.forgetPassword.forgetPassword.ForgetPasswordScreen
import com.alef.souqleader.ui.presentation.forgetPassword.resetPassword.ResetPasswordScreen
import com.alef.souqleader.ui.presentation.login.LoginScreen
import com.alef.souqleader.ui.presentation.mainScreen.CustomModalDrawer
import com.alef.souqleader.ui.presentation.simplifyWorkflow.SimplifyScreen

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
                    modifier,
                    mainViewModel,
                    sharedViewModel = viewModel,
                    navController = navController
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
                    navController
                )
            }
        }

        composable(Screen.ResetPasswordScreen.route) {
            modifier?.let { it1 ->
                ResetPasswordScreen(
                    navController
                )
            }
        }
        composable(Screen.CheckCodeScreen.route) {
            modifier?.let { it1 ->
                CheckCodeScreen(
                    navController
                )
            }
        }
        composable(Screen.ContactUsScreen.route) {
            modifier?.let { it1 ->
                ContactUScreen(
                    navController
                )
            }
        }
        composable(Screen.MainScreen2.route) {
            modifier?.let { it1 ->
                CustomModalDrawer(
                    modifier,
                    navController = navController,
                    viewModel,
                    allLead = SnapshotStateList(),
                    mainViewModel = mainViewModel
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
