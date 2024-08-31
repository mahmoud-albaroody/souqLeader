package com.alef.souqleader.ui.presentation.forgetPassword.forgetPassword

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.R
import com.alef.souqleader.ui.presentation.login.isValidText
import com.alef.souqleader.ui.theme.White
import kotlinx.coroutines.launch

@Preview
@Composable
fun ForgetPasswordScreen() {
    val changePasswordViewModel: ForgetPasswordViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        changePasswordViewModel.viewModelScope.launch {
            changePasswordViewModel.forgetPassword.collect {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(White)
            .padding(top = 70.dp)
            .padding(horizontal = 24.dp),

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
                text = stringResource(R.string.forgot_password),
                style = TextStyle(
                    fontSize = 26.sp,
                    color = colorResource(id = R.color.blue2),
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = stringResource(R.string.lorem_ipsum_dolor_sit_amet_cons_ectetur_adipisici_elit),
                style = TextStyle(
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        ChangePass(onChangePasswordClick = { email ->
            changePasswordViewModel
                .forgetPassword(
                    email
                )
        })
    }
}

@Composable
fun ChangePass(onChangePasswordClick: (String) -> Unit) {
    val context = LocalContext.current
    Column(Modifier.verticalScroll(rememberScrollState())) {
        var isEmailNotValid by remember { mutableStateOf(true) }
        var email by remember { mutableStateOf("") }
        ChangePassItem(stringResource(R.string.e_mail),
            onTextChange = { pass, isNotValid ->
                email = pass
                isEmailNotValid = isNotValid
            })


        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue2)),
            onClick = {
                if (isEmailNotValid) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.invalid_data), Toast.LENGTH_LONG
                    ).show()
                } else {
                    onChangePasswordClick(email)
                }
            }) {
            Text(
                text = stringResource(R.string.forgot_password),
                Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePassItem(text: String, onTextChange: (String, Boolean) -> Unit) {
    var isNotValid by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    val keyboardOptions =
        KeyboardOptions(imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text)

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = password,
        placeholder = {
            Text(
                text = text,
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
            isNotValid = it.isEmpty()
            onTextChange(password, isNotValid)

        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        isError = isNotValid,
        keyboardOptions = keyboardOptions,
    )

    if (isNotValid) {
        Text(
            text = stringResource(R.string.please_enter_valid_text),
            fontSize = 12.sp,
            color = colorResource(id = R.color.red)
        )
    }
}