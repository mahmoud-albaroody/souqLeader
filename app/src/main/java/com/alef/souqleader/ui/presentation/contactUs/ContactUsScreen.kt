package com.alef.souqleader.ui.presentation.contactUs

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.theme.White
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@Composable
fun ContactUScreen(navController: NavController) {
    val contactUsViewModel: ContactUsViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        contactUsViewModel.viewModelScope.launch {
            contactUsViewModel.contactUs.collect {
                if (it.data) {
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.LoginScreen.route)
                } else {
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(White)
            .padding(top = 30.dp)
            .padding(horizontal = 24.dp),

        ) {
        Image(
            painter = painterResource(R.drawable.souq_leader_logo_new_logo_same_size),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        ChangePass { name, email, phone, organizationName, message ->
            contactUsViewModel.contactus(
                name, email, phone, organizationName, message
            )
        }
    }
}

@Composable
private fun ChangePass(onChangePasswordClick: (String, String, String, String, String?) -> Unit) {
    val context = LocalContext.current
    Column(Modifier.verticalScroll(rememberScrollState())) {
        var isEmailNotValid by remember { mutableStateOf(true) }
        var isPasswordNotValid by remember { mutableStateOf(true) }
        var isConfirmPasswordNotValid by remember { mutableStateOf(true) }
        var isCodNotValid by remember { mutableStateOf(true) }
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var organizationName by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        ChangePassItem(stringResource(R.string.name), onTextChange = { it, isNotValid ->
            name = it
            isEmailNotValid = isNotValid
        })
        ChangePassItem(stringResource(R.string.e_mail), onTextChange = { e_mail, isNotValid ->
            email = e_mail
            isEmailNotValid = isNotValid
        })

        ChangePassItem(stringResource(R.string.phone), onTextChange = { it, isNotValid ->
            phone = it
            isPasswordNotValid = isNotValid
        })
        ChangePassItem(
            stringResource(R.string.organization_name),
            onTextChange = { it, isNotValid ->
                organizationName = it
                isConfirmPasswordNotValid = isNotValid
            })

        ChangePassItem(stringResource(R.string.message), onTextChange = { it, isNotValid ->
            message = it
            isCodNotValid = isNotValid
        })

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue2)),
            onClick = {
                if (isPasswordNotValid || isEmailNotValid || isCodNotValid || isConfirmPasswordNotValid) {
                    Toast.makeText(
                        context, context.getString(R.string.invalid_data), Toast.LENGTH_LONG
                    ).show()
                } else {
                    onChangePasswordClick(name, email, phone, organizationName, message)
                }
            }) {
            Text(
                text = stringResource(R.string.contact_us),
                Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChangePassItem(text: String, onTextChange: (String, Boolean) -> Unit) {
    var isNotValid by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var keyboardOptions =
        KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text)
    if (text == stringResource(id = R.string.message)) {
        keyboardOptions =
            KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text)
    }
    if (text == stringResource(id = R.string.phone)) {
        keyboardOptions =
            KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number)
    }
    if (text == stringResource(id = R.string.message)) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(top = 16.dp),
            textStyle = TextStyle(textAlign = TextAlign.Start),
            value = password,
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = text,
                    style = TextStyle(
                        color = colorResource(id = R.color.gray),
                        textAlign = TextAlign.Start
                    )
                )
            },

            colors = TextFieldDefaults.colors(
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


    } else {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = password,
            placeholder = {
                Text(
                    text = text, style = TextStyle(color = colorResource(id = R.color.gray))
                )
            },

            colors = TextFieldDefaults.colors(
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
}
