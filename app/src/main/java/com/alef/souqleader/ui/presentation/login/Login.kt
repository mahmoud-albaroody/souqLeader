package com.alef.souqleader.ui.presentation.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.meetingReport.MeetingReportViewModel
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.White


@Composable
fun LoginScreen(
    modifier: Modifier, navController: NavController, sharedViewModel: SharedViewModel
) {
    val viewModel: LoginViewModel = hiltViewModel()
    AccountData.auth_token = null
    AccountData.isFirstTime = true
    val loginState by viewModel.loginState.collectAsState()
    viewModel.updateBaseUrl(AccountData.BASE_URL)
    loginState.let {
        if (!it.access_token.isNullOrEmpty()) {
            AccountData.auth_token = "Bearer " + it.access_token
            AccountData.name = it.name.toString()
            AccountData.role_name = it.role_name.toString()
            AccountData.role_id = it.role_id ?: 0
            AccountData.photo = it.photo.toString()
            sharedViewModel.updateSalesNameState(it.role_name.toString())
            sharedViewModel.updatePhotoState(it.photo.toString())
            sharedViewModel.updateNameState(it.name.toString())
        }

        if (AccountData.auth_token != null)
            navController.navigate(Screen.DashboardScreen.route)
    }
    LoginItem(modifier, navController, viewModel)


}

class SampleNameProvider(override val values: Sequence<NavController>) :
    PreviewParameterProvider<NavController> {

}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun LoginItem(
    modifier: Modifier,
    @PreviewParameter(SampleNameProvider::class) navController: NavController,
    viewModel: LoginViewModel
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
            .background(White)
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
                    fontSize = 26.sp, color = Blue2, fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = stringResource(R.string.lorem_ipsum_dolor_sit_amet_cons_ectetur_adipisici_elit),
                style = TextStyle(
                    fontSize = 15.sp
                ),
                modifier = modifier.padding(top = 16.dp)
            )
            TextField(modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
                value = email,

                placeholder = {
                    Text(
                        text = stringResource(R.string.e_mail),
                        style = TextStyle(color = Color.Gray)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Blue2,
                    disabledLabelColor = Blue2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    errorCursorColor = Color.Transparent
                ),
                onValueChange = {
                    email = it
                    isNotValid = !isValidText(it)
                },
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                isError = isNotValid,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }))

            if (isNotValid) {
                Text(text = stringResource(R.string.please_enter_valid_text), color = Color.Red)
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
                        style = TextStyle(color = Color.Gray)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Blue2,
                    disabledLabelColor = Blue2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    errorCursorColor = Color.Transparent
                ),
                onValueChange = {
                    password = it
                    isValidNotPassword = it.isEmpty()
                },
                shape = RoundedCornerShape(8.dp),
                isError = isValidNotPassword
            )

            if (isValidNotPassword) {
                Text(text = stringResource(R.string.please_enter_valid_text), color = Color.Red)
            }
            Text(
                text = stringResource(R.string.forgot_password), style = TextStyle(
                    fontSize = 15.sp, color = Blue2
                ), modifier = modifier.align(Alignment.End)
            )
        }

        Button(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(Blue2),
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