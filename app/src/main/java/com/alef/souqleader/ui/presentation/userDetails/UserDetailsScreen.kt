package com.alef.souqleader.ui.presentation.userDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.Permission
import com.alef.souqleader.data.remote.dto.UserDetails
import com.alef.souqleader.data.remote.dto.UserDetailsResponse
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import kotlinx.coroutines.launch


@Composable
fun UserDetailsScreen(navController: NavController, userId: String?, mainViewModel: MainViewModel) {
    val userDetailsViewModel: UserDetailsViewModel = hiltViewModel()
    var userDetails by remember { mutableStateOf(UserDetailsResponse()) }


    LaunchedEffect(key1 = true) {
        userId?.let { userDetailsViewModel.userDetails(it) }
        userDetailsViewModel.viewModelScope.launch {
            userDetailsViewModel.userDetailsState.collect {
                when (it) {
                    is Resource.Success -> {
                        userDetails = it.data!!
                        mainViewModel.showLoader = false
                    }

                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        mainViewModel.showLoader = false
                    }
                }
            }
        }
    }
    Column {
        userDetails.data?.let {
            ProfileItem(it, onChangePasswordClick = {

            }, onSignOutClick = {

            })
        }
        Text(
            modifier = Modifier
                .padding(start = 24.dp),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            ),
            text = stringResource(R.string.permissions)
        )
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            userDetails.data?.permissions?.let {
                items(it) { permission ->
                    Permission(permission)
                }
            }
        }
    }
}


@Composable
fun ProfileItem(
    userDetails: UserDetails,
    onChangePasswordClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .padding(vertical = 16.dp, horizontal = 24.dp),
    ) {

        Card(
            Modifier
                .fillMaxWidth()
        ) {
            Column(Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        if (userDetails.photo.isNotEmpty()) {
                            if (userDetails.photo.isNotEmpty()) {
                                AccountData.BASE_URL + userDetails.photo
                            } else {
                                R.drawable.user_profile_placehoder
                            }
                        } else {

                        }
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = userDetails.name,
                    style = TextStyle(
                        fontSize = 18.sp, color = colorResource(id = R.color.blue)
                    ),
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = userDetails.role_name,
                        style = TextStyle(
                            fontSize = 14.sp
                        ),
                    )
                    Text(
                        text = userDetails.email,
                        style = TextStyle(
                            fontSize = 15.sp, color = colorResource(id = R.color.blue)
                        )
                    )
                }

            }
        }



        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)

        ) {
            Card(
                Modifier
                    .weight(1f)
                    .height(100.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = userDetails.activity_count ?: "0",
                        style = TextStyle(
                            fontSize = 20.sp, color = colorResource(id = R.color.blue),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = stringResource(R.string.activity_count),
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                }
            }
            Card(
                Modifier
                    .weight(1f)
                    .height(100.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = userDetails.actions_count ?: "0",
                        style = TextStyle(
                            fontSize = 20.sp, color = colorResource(id = R.color.blue),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = stringResource(R.string.actions_count),
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )

                }
            }
            Card(
                Modifier
                    .weight(1f)
                    .height(100.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = userDetails.sales_report_count ?: "0",
                        style = TextStyle(
                            fontSize = 20.sp, color = colorResource(id = R.color.blue),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = stringResource(R.string.sales_report_count),
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )

                }
            }
        }
    }
}


@Composable
fun Permission(permission: Permission) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.grey100)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                text = permission.module_name, style = TextStyle(
                    fontSize = 13.sp,
                    color = colorResource(id = R.color.black)
                )
            )
//            Image(
//                painterResource(R.drawable.drop_menu_icon_white),
//                contentDescription = "",
//                modifier = Modifier.padding(horizontal = 4.dp),
//            )
        }
    }
}