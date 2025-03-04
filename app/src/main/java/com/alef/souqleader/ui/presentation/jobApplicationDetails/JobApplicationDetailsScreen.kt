package com.alef.souqleader.ui.presentation.jobApplicationDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.domain.model.AccountData


@Composable
fun JobApplicationScreen(navController: NavController) {
    val viewModel: JobApplicationDetailsViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        //  viewModel.getPaymentPlan()
//        viewModel.allUsers()
//        viewModel.viewModelScope.launch {
//            viewModel.allUsers.collect {
//                userList.addAll(it)
//            }
//        }
    }
}


@Preview
@Composable
fun PersonDetails() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.grey100)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(Modifier) {
                    Text(
                        text = "AccountData.name",
                        style = TextStyle(
                            fontSize = 18.sp, color = colorResource(id = R.color.grey2)
                        ),
                    )

                    Text(
                        text = "AccountData.email",
                        style = TextStyle(
                            fontSize = 15.sp, color = colorResource(id = R.color.gray)
                        )
                    )


                }

                Image(
//                    painter = rememberAsyncImagePainter(
//                        if (AccountData.photo.isNotEmpty()) {
//                            if (AccountData.photo.isNotEmpty()) {
//                                AccountData.BASE_URL + AccountData.photo
//                            } else {
//                                R.drawable.user_profile_placehoder
//                            }
//                        } else {
//
//                        }
//                    ),
                    painter = painterResource(id = R.drawable.ic_lock_open),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_call_24),
                        contentDescription = ""
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = "01020343274",
                    )
                }
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.call_icon),
                    contentDescription = ""
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = "mahmoud.elbaroody@gmail.com",
                    )
                }
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.mail_icon),
                    contentDescription = ""
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "Egypt cairo")
                Text(text = "Exp Salary")
            }
        }
    }
}

@Preview
@Composable
fun PersonExperience() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.grey100)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {


            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "work experience",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )



            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = "marketing",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )


            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Egypt cairo",
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Exp Salary",
                fontSize = 12.sp
            )

        }
    }
}

@Preview
@Composable
fun PersonSkills() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.grey100)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier,
                text = "Skills",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                Text(
                    modifier = Modifier,
                    text = "work experience",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )



                Text(
                    modifier = Modifier,
                    text = "marketing",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                Text(
                    modifier = Modifier,
                    text = "Egypt cairo",
                    fontSize = 12.sp
                )
                Text(
                    modifier = Modifier,
                    text = "Exp Salary",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun StarRating(rating: Float, maxRating: Int = 5) {
    Row {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Star $i",
                tint = Color.Yellow,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewStarRating() {
    StarRating(rating = 4f)
}

@Composable
fun ProgressBarWithPercentage(progress: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        LinearProgressIndicator(
            progress = progress / 100f,
            modifier = Modifier
                .width(150.dp)
                .height(6.dp),
            color = Color.Blue,
            trackColor = Color.LightGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${progress.toInt()}%",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun PreviewProgressBarWithPercentage() {
    ProgressBarWithPercentage(progress = 50f)
}