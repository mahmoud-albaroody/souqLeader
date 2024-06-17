package com.alef.souqleader.ui.presentation.rolesPremissions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissions
import com.alef.souqleader.ui.presentation.dashboardScreen.DashboardViewModel
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.White


@Composable
fun RolesPermissionsScreen(modifier: Modifier) {
    val viewModel: RolesPremissionsViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.getAllRoles()
        viewModel.getAllPermissions()
    }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        items(viewModel.allRoles) {
            RolesPermissionsItem(it)
        }
    }
}

@Composable
fun RolesPermissionsItem(roles:AllRolesAndAllPermissions) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 10f)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(R.drawable.user_profile_placehoder),
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .weight(1f)
                    .size(35.dp)
            )

            Column(
                Modifier
                    .weight(3.5f)
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = "Mahmoud Ali", style = TextStyle(
                        fontSize = 15.sp, color = Blue
                    )
                )
                Text(
                    text = roles.name, style = TextStyle(
                        fontSize = 13.sp,
                    )
                )
            }
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .weight(2.5f),
                colors = CardDefaults.cardColors(containerColor = Blue2),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = "PERMISSION", style = TextStyle(
                            fontSize = 10.sp,
                            color = White
                        )
                    )
                    Image(

                        painterResource(R.drawable.drop_menu_icon_white),
                        contentDescription = "",
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                }
            }
        }
    }
}
