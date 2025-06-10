package com.alef.souqleader.ui.presentation.login

import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.Start
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    modifier: Modifier, navController: NavHostController,
    sharedViewModel: SharedViewModel, mainViewModel: MainViewModel
) {
    val context = LocalContext.current

    val viewModel: LoginViewModel = hiltViewModel()
    AccountData.auth_token = null
    AccountData.isFirstTime = true
    LaunchedEffect(key1 = true) {
        viewModel.updateBaseUrl(AccountData.BASE_URL)
        viewModel.viewModelScope.launch {
            viewModel.loginState.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data?.status == true) {
                            it.data.data?.let {
                                if (!it.access_token.isNullOrEmpty()) {
                                    AccountData.auth_token = "Bearer " + it.access_token
                                    AccountData.name = it.name.toString()
                                    AccountData.role_name = it.role_name.toString()
                                    AccountData.role_id = it.role_id ?: 0
                                    AccountData.userId = it.id ?: 0
                                    AccountData.photo = it.photo.toString()
                                    AccountData.email = it.email.toString()
                                    it.permissions?.let {
                                        AccountData.permissionList = it
                                    }
                                    AccountData.firebase_token?.let { it1 ->
                                        viewModel.updateFcmToken(
                                            it1
                                        )
                                    }
                                    sharedViewModel.updateSalesNameState(it.role_name.toString())
                                    sharedViewModel.updatePhotoState(it.photo.toString())
                                    sharedViewModel.updateNameState(it.name.toString())
                                }

                                if (AccountData.auth_token != null)
                                    (context as MainActivity).setContent {
                                        Start("login")
                                    }
                            }
                        } else {
                            Toast.makeText(context, it.data?.message.toString(), Toast.LENGTH_LONG)
                                .show()
                        }
                        mainViewModel.showLoader = false
                    }


                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        if (it.errorCode == 401) {
                            AccountData.clear()
                            (context as MainActivity).setContent {
                                AndroidCookiesTheme {
                                    MainScreen(
                                        Modifier,
                                        navController,
                                        sharedViewModel,
                                        mainViewModel
                                    )
                                }
                            }
                        }
                        if (it.errorCode == 500) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.something_error), Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        mainViewModel.showLoader = false
                    }
                }

            }

        }
    }




    LoginItem(modifier, navController, viewModel, onForgetClick = {
        navController.navigate(Screen.ForgetPasswordScreen.route)
    })


}

class SampleNameProvider(override val values: Sequence<NavController>) :
    PreviewParameterProvider<NavController> {

}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun LoginItem(
    modifier: Modifier,
    @PreviewParameter(SampleNameProvider::class) navController: NavController,
    viewModel: LoginViewModel, onForgetClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isNotValid by remember { mutableStateOf(false) }
    var isValidNotPassword by remember { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .verticalScroll(scrollState)
            .padding(vertical = 50.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween

    ) {

        Image(
            painter = painterResource(R.drawable.souq_leader_logo_2__1_),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.let_s_login),
                style = TextStyle(
                    fontSize = 26.sp,
                    color = colorResource(id = R.color.blue2),
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = stringResource(R.string.please_enter_your_credentials_to_access_your_account),
                style = TextStyle(
                    fontSize = 15.sp
                ),
                modifier = modifier.padding(top = 16.dp)
            )
            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = email,

                placeholder = {
                    Text(
                        text = stringResource(R.string.e_mail),
                        style = TextStyle(color = colorResource(id = R.color.gray))
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = colorResource(id = R.color.blue2),
                    disabledLabelColor = colorResource(id = R.color.blue2),
                    focusedIndicatorColor = colorResource(id = R.color.transparent),
                    unfocusedIndicatorColor = colorResource(id = R.color.transparent),
                    errorIndicatorColor = colorResource(id = R.color.transparent),
                    errorCursorColor = colorResource(id = R.color.transparent)
                ),
                onValueChange = {
                    email = it
                    isNotValid = !isValidText(it)
                },
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                isError = isNotValid,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() })
            )

            if (isNotValid) {
                Text(
                    text = stringResource(R.string.please_enter_valid_email),
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.red)
                )
            }

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .padding(vertical = 8.dp)
                    .padding(top = 8.dp),
//                keyboardActions =KeyboardOptions(imeAction = ImeAction.Next) ,
                value = password,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),

                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.password),
                        style = TextStyle(color = colorResource(id = R.color.gray))
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = colorResource(id = R.color.blue2),
                    disabledLabelColor = colorResource(id = R.color.blue2),
                    focusedIndicatorColor = colorResource(id = R.color.transparent),
                    unfocusedIndicatorColor = colorResource(id = R.color.transparent),
                    errorIndicatorColor = colorResource(id = R.color.transparent),
                    errorCursorColor = colorResource(id = R.color.transparent)
                ),
                onValueChange = {
                    password = it
                    isValidNotPassword = it.isEmpty()
                },
                shape = RoundedCornerShape(8.dp),
                isError = isValidNotPassword
            )

            if (isValidNotPassword) {
                Text(
                    text = stringResource(R.string.please_enter_your_password),
                    color = colorResource(id = R.color.red),
                    fontSize = 12.sp
                )
            }
            Text(
                text = stringResource(R.string.forgot_password), style = TextStyle(
                    fontSize = 15.sp, color = colorResource(id = R.color.blue2)
                ), modifier = modifier
                    .align(Alignment.End)
                    .clickable {
                        onForgetClick()
                    }
            )
        }

        Button(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue2)),
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty() && !isNotValid) {
                    viewModel.login(email, password)
                } else {
                    if (email.isEmpty()) {
                        isNotValid = true
                    }
                    if (password.isEmpty()) {
                        isValidNotPassword = true
                    }

                }
            }) {
            Text(
                text = stringResource(R.string.login),
                modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }

    }
}

fun isValidText(text: String): Boolean {
    // Add your custom validation rules here
    return text.isNotEmpty() && EMAIL_ADDRESS.matcher(text).matches()
}