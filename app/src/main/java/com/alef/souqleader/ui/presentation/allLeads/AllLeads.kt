package com.alef.souqleader.ui.presentation.allLeads

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.dashboardScreen.DashboardViewModel
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.Grey


@Composable
fun AllLeadsScreen(navController: NavController, modifier: Modifier) {
    val viewModel: DashboardViewModel = viewModel()

//    LaunchedEffect(key1 = true) {
//        // viewModel.getGym()
//    }
    screen(navController)

}


@Composable
private fun screen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Box(
        Modifier
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxSize()
    ) {
        Column(Modifier.height(screenHeight * 0.85f)) {
            Search(stringResource(R.string.search)){
              navController.navigate(Screen.FilterScreen.route)
            }
            LazyColumn(
                Modifier
                    .fillMaxWidth()
            ) {
                items(6) {
                    AllLeadsItem()
                }
            }
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(Blue2),
            onClick = {
                navController.navigate(Screen.LeadUpdateScreen.route)
            })
        {
            Text(text = stringResource(R.string.add_action), modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun AllLeadsItem() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3f)
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    painterResource(R.drawable.client_placeholder),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 8.dp)
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .padding(top = 6.dp)
                ) {
                    Text(
                        text = "Mahmoud Ali", style = TextStyle(
                            fontSize = 16.sp, color = Blue
                        )
                    )
                    Text(
                        text = "Admin", style = TextStyle(
                            fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                        )
                    )
                }

            }

            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.sales_name_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = stringResource(R.string.sales_name),
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painterResource(R.drawable.project_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = stringResource(R.string.project_name),
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painterResource(R.drawable.coin),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Text(
                        text = "\$248,67",
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }


            Row(verticalAlignment = Alignment.Top) {
                Image(
                    painterResource(R.drawable.notes_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consec tetur ad ipisici elit, sed do eiusmod tempor",
                    style = TextStyle(
                        fontSize = 11.sp, color = Grey
                    ),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painterResource(R.drawable.call_icon),
                    contentDescription = "",
                    Modifier.size(60.dp, 40.dp)
                )
                Image(
                    painterResource(R.drawable.sms_icon),
                    contentDescription = "",
                    Modifier.size(60.dp, 40.dp)
                )
                Image(
                    painterResource(R.drawable.mail_icon),
                    contentDescription = "",
                    Modifier.size(60.dp, 40.dp)
                )
                Image(
                    painterResource(R.drawable.whats_icon),
                    contentDescription = "",
                    Modifier.size(60.dp, 40.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(text: String, onFilterClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painterResource(R.drawable.search_icon),
                modifier = Modifier
                    .size(30.dp)
                    .weight(0.5f),
                contentDescription = "",
            )
            TextField(
                modifier = Modifier.weight(3f),
                value = "",
                placeholder = {
                    Text(text = text)
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    disabledLabelColor = Blue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {

                },
//                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )
            Image(
                painterResource(R.drawable.filter_icon),
                contentDescription = "",
                Modifier
                    .size(30.dp)
                    .weight(0.5f)
                    .clickable {
                        onFilterClick.invoke()
                    }
            )
        }
    }

}
