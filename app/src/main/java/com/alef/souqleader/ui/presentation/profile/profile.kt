package com.alef.souqleader.ui.presentation.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.UserDate
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.domain.model.Campaign
import com.alef.souqleader.ui.LocaleHelper
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.presentation.addlead.AddLeadViewModel
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.White
import com.alef.souqleader.ui.updateLocale
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun ProfileScreen(modifier: Modifier) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    var userDate by remember { mutableStateOf(UserDate()) }
    LaunchedEffect(key1 = true) {
        profileViewModel.viewModelScope.launch {
            profileViewModel.userDate.collect {
                it.data?.let { userDate = it }
            }
        }
    }

    ProfileItem(userDate)
}

@Composable
fun ProfileItem(userDate: UserDate) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 16.dp, horizontal = 24.dp),
    ) {

        Card(
            Modifier
                .fillMaxWidth()
        ) {
            Column(Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        if (AccountData.photo.isNotEmpty()) {
                            if (AccountData.photo.isNotEmpty()) {
                                AccountData.BASE_URL + AccountData.photo
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
                    text = AccountData.name,
                    style = TextStyle(
                        fontSize = 18.sp, color = Blue
                    ),
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = AccountData.role_name,
                        style = TextStyle(
                            fontSize = 14.sp
                        ),
                    )
                    Text(
                        text = AccountData.email,
                        style = TextStyle(
                            fontSize = 15.sp, color = Blue
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
                        text = userDate.activities_count ?: "0",
                        style = TextStyle(
                            fontSize = 20.sp, color = Blue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = stringResource(R.string.recent_activities),
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
                        text = userDate.actions_count ?: "0",
                        style = TextStyle(
                            fontSize = 20.sp, color = Blue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = stringResource(R.string.my_actions),
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
                        text = "2",
                        style = TextStyle(
                            fontSize = 20.sp, color = Blue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = stringResource(R.string.my_sales_report),
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )

                }
            }
        }


        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)

        ) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (AccountData.lang == "en") {
                            AccountData.lang = "ar"
                        } else {
                            AccountData.lang = "en"
                        }
                        updateLocale(context, Locale(AccountData.lang))
                        (context as? Activity)?.finish()
                        (context as? Activity)?.startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            )
                        )
                    }

            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(R.drawable.language_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                    Text(

                        text = "Language",
                        style = TextStyle(
                            fontSize = 16.sp
                        ),
                    )
                    Image(
                        painter = painterResource(R.drawable.next_menu_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(R.drawable.change_password_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Change Password",
                        style = TextStyle(
                            fontSize = 16.sp
                        ),
                    )
                    Image(
                        painter = painterResource(R.drawable.next_menu_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(R.drawable.sign_out_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Sign Out",
                        style = TextStyle(
                            fontSize = 16.sp
                        ),
                    )
                    Image(
                        painter = painterResource(R.drawable.next_menu_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

