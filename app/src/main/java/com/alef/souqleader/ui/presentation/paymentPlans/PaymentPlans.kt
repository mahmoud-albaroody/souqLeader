package com.alef.souqleader.ui.presentation.paymentPlans

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.User
import com.alef.souqleader.data.UsersItem
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun PaymentPlansScreen(modifier: Modifier) {
    val viewModel: PaymentPlanViewModel = hiltViewModel()
    val userList = remember { mutableStateListOf<UsersItem>() }
    LaunchedEffect(key1 = true) {
        //  viewModel.getPaymentPlan()
        viewModel.allUsers()
        viewModel.viewModelScope.launch {
            viewModel.allUsers.collect {
                userList.addAll(it)
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            items(userList) {
                //PaymentPlansItem(it)
                UserItemList(it)
            }
        }
    }
}


//@Composable
//fun PaymentPlansItem(plan: Plan) {
//    val configuration = LocalConfiguration.current
//    val screenHeight = configuration.screenHeightDp.dp
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(screenHeight / 4.8f)
//            .padding(vertical = 8.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(
//            Modifier
//                .fillMaxSize()
//                .weight(2f),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            Row(
//                Modifier
//                    .fillMaxSize()
//                    .weight(2f),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column(
//                    Modifier
//                        .fillMaxWidth()
//                        .weight(1f)
//                        .padding(
//                            vertical = 16.dp,
//                            horizontal = 16.dp
//                        )
//                ) {
//                    Text(
//                        text = plan.currency + plan.price + "/" + plan.trail_days,
//                        style = TextStyle(
//                            fontSize = 18.sp, color = Blue,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                    Text(
//                        modifier = Modifier.padding(top = 4.dp),
//                        text = plan.getPlanName(), style = TextStyle(
//                            fontSize = 13.sp, fontWeight = FontWeight.SemiBold
//                        )
//                    )
//                }
////                Image(
////                    painterResource(R.drawable.select_box),
////                    contentDescription = "",
////                )
//            }
//
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//            ) {
//                Text(
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    text = "You will get unlimit access to every module you want, " + plan.frequency + " user",
//                    style = TextStyle(
//                        fontSize = 12.sp,
//                        color = Grey
//                    )
//
//                )
//
//            }
//        }
//    }
//}

@Composable
fun UserItemList(usersItem: UsersItem) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text(
            modifier = Modifier
                .background(color = colorResource(id = R.color.grey100))
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),

            text = usersItem.getName(),
            fontWeight = FontWeight.Bold
        )
        var h by remember {
            mutableStateOf(0.dp)
        }
        LazyColumn(
            modifier =
            Modifier.height((h * usersItem.users.size)),
            content = {
                items(usersItem.users) {
                    UserItem(it) {
                        h = it
                    }
                }
            }, userScrollEnabled = false
        )
    }

}


@Composable
fun UserItem(user: User, high: (Dp) -> Unit) {
    val localDensity = LocalDensity.current
    var heightIs by remember {
        mutableStateOf(0.dp)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .onGloballyPositioned { coordinates ->
                heightIs = with(localDensity) { coordinates.size.height.toDp() }
                high(heightIs)
            },
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp, bottom = 8.dp)
                .background(colorResource(id = R.color.white)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    if (user.photo.isNotEmpty()) {
                        AccountData.BASE_URL + user.photo
                    } else {
                        R.drawable.user_profile_placehoder
                    }

                ),

                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = user.name, fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = user.email,
                    fontSize = 11.sp,
                )
            }
            Image(
                painter = painterResource(id = R.drawable.next_menu_icon),
                contentDescription = ""
            )
        }
        Divider(
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth() // Set the thickness of the vertical line
                .background(colorResource(id = R.color.lightGray))  // Set the color of the vertical line
        )
    }
}