package com.alef.souqleader.ui.presentation.jobApplicationDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.JobAppsResponse
import com.alef.souqleader.data.remote.dto.Jobapps
import com.alef.souqleader.data.remote.dto.JopPersion
import com.alef.souqleader.domain.model.AccountData
import kotlinx.coroutines.launch


@Composable
fun JobApplicationDetailsScreen(navController: NavController) {
    val viewModel: JobApplicationDetailsViewModel = hiltViewModel()
    val jobAppList = remember { mutableStateListOf<Jobapps>() }

    LaunchedEffect(key1 = true) {
        viewModel.viewModelScope.launch {
            viewModel.jobAppResponse.collect {
                jobAppList.addAll(it)
            }
        }
    }
}

@Preview
@Composable
fun JobApplication() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PersonDetails()
        PersonExperience()
        PersonSkills()
        PersonRating()
        PersonCV()
    }
}


@Preview
@Composable
fun PersonDetails() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
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
                    Education()

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
                Text(
                    text = "years of Experience",
                    fontWeight = FontWeight.SemiBold
                )
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
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = "Egypt cairo",
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
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
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
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

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier,
                    text = "Sales",
                    fontSize = 12.sp
                )



                ProgressBarWithPercentage(50f)

            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier,
                    text = "Egypt cairo",
                    fontSize = 12.sp
                )
                ProgressBarWithPercentage(50f)
            }
        }
    }
}

@Preview
@Composable
fun PersonRating() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier,
                text = "Language",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            LazyColumn(content = {
                items(5) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            modifier = Modifier,
                            text = "Arabic",
                            fontSize = 12.sp
                        )



                        StarRating(1.2f, 5)

                    }
                }
            })

        }
    }
}

@Preview
@Composable
fun PersonCV() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier,
                text = "CV",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )


            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier,
                    text = "CV is Locked,Unlocked to view ",
                    color = Color.Red,
                    fontSize = 13.sp
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Unlocked Candidate",
                    fontSize = 13.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.SemiBold
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
fun EmptyText() {
    Text(
        modifier = Modifier.padding(vertical = 24.dp),
        text = "No work experience available",
        color = Color.Gray,
        fontSize = 14.sp
    )
}

@Preview
@Composable
fun Education() {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Degree",
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth() .padding(top = 4.dp, start = 4.dp)
               ,
            text = "Egypt cairo",
            fontSize = 12.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 4.dp),
            text = "Exp Salary",
            fontSize = 12.sp
        )
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