package com.alef.souqleader.ui.presentation.simplifyWorkflow

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.constants.Constants.BASE_URL
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.Grey
import com.alef.souqleader.ui.theme.Grey1
import com.alef.souqleader.ui.theme.Grey2
import com.alef.souqleader.ui.theme.White
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun SimplifyScreen(modifier: Modifier, navController: NavController) {
    val simplifyWorkViewModel: SimplifyWorkViewModel = hiltViewModel()
    val client = simplifyWorkViewModel.getClientState.collectAsState()
    SimplifyItem(navController) { companyName ->
        Log.e("ddd","Sdfdsfds")
        simplifyWorkViewModel.getClient(companyName)
    }

    client.value.let {

        if (!it.domain.isNullOrEmpty()) {
            AccountData.name = it.name ?: ""
            AccountData.domain = it.domain ?: ""
            BASE_URL = it.domain ?: ""
            AccountData.log = it.logo ?: ""
            navController.navigate(Screen.LoginScreen.route)
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimplifyItem(navController: NavController, onclick: (String) -> Unit) {
    var stat by remember { mutableIntStateOf(0) }
    var companyName by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var cs = stringResource(R.string.simplify_your_sales_workflow)
    var skip = stringResource(R.string.skip)
    var image = R.drawable.walkthrow1
    var next = stringResource(id = R.string.next)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),

        ) {
        if (stat == 1) {
            skip = ""
            cs = stringResource(R.string.stay_ahead_of_the_competition_one_click)
            image = R.drawable.walkthrow2
        } else if (stat == 2) {
            skip = ""
            cs = stringResource(R.string.ready_to_explore)
            next = stringResource(R.string.continue_with_e_mail)
            image = R.drawable.walkthrow3
        }
        Row(
            Modifier
                .align(Alignment.End)
                .padding(end = 32.dp, top = 32.dp)
                .clickable {
                    navController.navigate(Screen.LoginScreen.route)
                }
        ) {
            Text(
                text = skip,
                style = TextStyle(
                    fontSize = 15.sp, fontWeight = FontWeight.Bold
                ),
            )
        }
        Text(
            text = cs,
            minLines = 2,
            style = TextStyle(
                fontSize = 25.sp, color = Blue, fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .padding(bottom = 24.dp, top = 16.dp)
        )
        Image(
            painterResource(image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2.9f)
        )

        if (stat != 2)
            Text(
                text = stringResource(R.string.effortlessly_manage_sales_leads_and_streamline_your_sales_process_with_our_powerful_souq_leader),
                style = TextStyle(
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(horizontal = 38.dp, vertical = 32.dp)
            )
        if (stat == 2)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        value = companyName,
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp),
                                text = "Company Name",
                                style = TextStyle(textAlign = TextAlign.End, color = Grey)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            disabledLabelColor = Blue,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        onValueChange = {
                            companyName = it
                        },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 24.dp),
                        text = ".SouqLeader.com",
                        style = TextStyle(textAlign = TextAlign.End)
                    )
                }
            }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp, bottom = 16.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(Blue),
            onClick = {
                when (stat) {
                    0 -> {
                        stat = 1
                    }

                    1 -> {
                        stat = 2
                    }

                    else -> {

                        onclick(companyName)
                    }
                }
            }) {

            Text(
                text = next,
                Modifier.padding(vertical = 8.dp)
            )
        }

        if (stat == 2)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = stringResource(R.string.don_t_have_an_access),
                    color = Grey
                )
                Text(
                    text = stringResource(R.string.contact_us), style = TextStyle(
                        color = Blue2
                    )
                )
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SimplifyItemP() {
    var stat by remember { mutableIntStateOf(2) }
    var companyName by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var cs = stringResource(R.string.simplify_your_sales_workflow)
    var skip = stringResource(R.string.skip)
    var image = R.drawable.walkthrow1
    var next = stringResource(id = R.string.next)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),

        ) {
        if (stat == 1) {
            skip = ""
            cs = stringResource(R.string.stay_ahead_of_the_competition_one_click)
            image = R.drawable.walkthrow2
        } else if (stat == 2) {
            skip = ""
            cs = stringResource(R.string.ready_to_explore)
            next = stringResource(R.string.continue_with_e_mail)
            image = R.drawable.walkthrow3
        }
        Row(
            Modifier
                .align(Alignment.End)
                .padding(end = 32.dp, top = 32.dp)
                .clickable {
                }
        ) {
            Text(
                text = skip,
                style = TextStyle(
                    fontSize = 15.sp, fontWeight = FontWeight.Bold
                ),
            )
        }
        Text(
            text = cs,
            minLines = 2,
            style = TextStyle(
                fontSize = 25.sp, color = Blue, fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .padding(bottom = 24.dp, top = 16.dp)
        )
        Image(
            painterResource(image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2.9f)
        )

        if (stat != 2)
            Text(
                text = stringResource(R.string.effortlessly_manage_sales_leads_and_streamline_your_sales_process_with_our_powerful_souq_leader),
                style = TextStyle(
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(horizontal = 38.dp, vertical = 32.dp)
            )
        if (stat == 2)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        value = companyName,
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp),
                                text = "Company Name",
                                style = TextStyle(textAlign = TextAlign.End, color = Grey)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            disabledLabelColor = Blue,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        onValueChange = {
                            companyName = it
                        },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 24.dp),
                        text = ".SouqLeader.com",
                        style = TextStyle(textAlign = TextAlign.End)
                    )
                }
            }


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp, bottom = 16.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(Blue),
            onClick = {
                when (stat) {
                    0 -> {
                        stat = 1
                    }

                    1 -> {
                        stat = 2
                    }

                    else -> {

                    }
                }
            }) {

            Text(
                text = next,
                Modifier.padding(vertical = 8.dp)
            )
        }

        if (stat == 2)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = stringResource(R.string.don_t_have_an_access),
                    color = Grey
                )
                Text(
                    text = stringResource(R.string.contact_us), style = TextStyle(
                        color = Blue2
                    )
                )
            }
    }
}