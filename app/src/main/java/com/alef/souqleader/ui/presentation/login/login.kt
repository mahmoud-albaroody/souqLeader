package com.alef.souqleader.ui.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.White


@Composable
fun LoginScreen(navController: NavController, modifier: Modifier) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    LoginItem(navController)
}

class SampleNameProvider(override val values: Sequence<NavController>) :
    PreviewParameterProvider<NavController> {

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginItem(@PreviewParameter(SampleNameProvider::class) navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 50.dp, horizontal = 32.dp),
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
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 26.sp, color = Blue2, fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = stringResource(R.string.lorem_ipsum_dolor_sit_amet_cons_ectetur_adipisici_elit),
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = "",

                placeholder = {
                    Text(text = stringResource(R.string.e_mail))
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    disabledLabelColor = Blue2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {

                },
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .padding(top = 8.dp),
                value = "",

                placeholder = {
                    Text(text = stringResource(R.string.password))
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    disabledLabelColor = Blue2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {

                },
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )
            Text(
                text = stringResource(R.string.forgot_password),
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 15.sp, color = Blue2
                ),
                modifier = Modifier.align(Alignment.End)
            )
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(Blue2),
            onClick = { navController.navigate(Screen.DashboardScreen.route) }) {
            Text(text = "LOGIN", Modifier.padding(vertical = 8.dp))
        }

    }
}